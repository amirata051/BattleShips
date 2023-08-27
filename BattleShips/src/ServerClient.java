import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClient {
    private int port = 1111;
    private String address = "127.0.0.1";

    public Socket socket = null;
    ServerSocket server = null;
    DataInputStream getter = null;
    DataOutputStream sender = null;

    void Connect(){
        try{
            try{
                socket = new Socket(address, port);
            }catch(Exception e){
                server = new ServerSocket(port);
                socket = server.accept();
            }
            getter = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            sender = new DataOutputStream(socket.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        GameHandeler.grahphics.StartGame();
    }

    Board syncBoards(Board myBoard){
        Board res = null;
        try{
            if(server == null){
                sender.writeUTF(encryptBoard(myBoard));
                res = decrypt(getter.readUTF());
            }else{
                res = decrypt(getter.readUTF());
                sender.writeUTF(encryptBoard(myBoard));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    void Dissconet(){
        try{
            getter = null;
            sender = null;
            socket.close();
            server.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String encryptBoard(Board b){
        String res = "";
        for(Tile[] tileRow: b.tiles){
            for(Tile tile: tileRow){
                if(tile.GetSituation() == TileSituation.water)
                    res += "0";
                else
                    res += "1";
            }
        }
        for(Ship ship: b.placedShips){
            switch(ship.name){
                case "Cruiser":
                    res += 'r';
                    break;
                case "Battleship":
                    res += 'b';
                    break;
                case "Carrier":
                    res += 'c';
                    break;
                case "Submarine":
                    res += 's';
                    break;
                case "Destroyer":
                    res += 'd';
                    break;
            }
            for(Point p: ship.tilePoints){
                res += "" + p.row + p.col;
            }
        }
        return res;
    }

    public Board decrypt(String code){
        int charIndex = 0;
        Board board = new Board();
        for(Tile[] tileRow: board.tiles){
            for(Tile tile: tileRow){
                tile.hidden = true;
                char c = code.charAt(charIndex);
                if(c == '1')
                    tile.setSituation(TileSituation.ship);
                charIndex++;
            }
        }
        for(int i = 0; i < 5; i++){
            String name = "";
            int shipLength = 0;
            switch(code.charAt(charIndex)){
                case 'r':
                    name = "Cruiser";
                    shipLength = 5;
                    break;
                case 'b':
                    name = "Battleship";
                    shipLength = 4;
                    break;
                case 'c':
                    name = "Carrier";
                    shipLength = 3;
                    break;
                case 's':
                    name = "Submarine";
                    shipLength = 3;
                    break;
                case 'd':
                    name = "Destroyer";
                    shipLength = 2;
                    break;
            }
            charIndex++;
            Point[] tilePoints = new Point[shipLength];
            for(int j = 0; j < shipLength; j++){
                int row = Character.getNumericValue(code.charAt(charIndex));
                int col = Character.getNumericValue(code.charAt(charIndex+1));
                tilePoints[j] = new Point(row, col);
                charIndex += 2;
            }
            board.placedShips[i] = new Ship(name, tilePoints);
        }
        return board;
    }
}
