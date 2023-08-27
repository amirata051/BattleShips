import javax.swing.JPanel;

public class GamePanel extends JPanel{
    Board myBoard, enemyBoard;
    ShipNamesPanel myNamesPanel, enemyNamesPanel;
    ServerClient serverClient;

    void updateShipNames(Board board, ShipNamesPanel nameLable){
        boolean isItLost = true;
        for(Ship ship: board.placedShips){
            boolean isHitWholeShip = true;
            for(Point p: ship.tilePoints){
                if(!board.tiles[p.row][p.col].hit){
                    isHitWholeShip = false;
                    isItLost = false;
                    break;
                }
            }
            if(isHitWholeShip){
                nameLable.strikeName(ship.name);
            }
        }
        if(isItLost){
            if(board == myBoard)
                GameHandeler.grahphics.GAMEOVER("YOU LOST");
            else
                GameHandeler.grahphics.GAMEOVER("YOU WON");
        }
    }

    void EnemyBoardEnabled(boolean t){
        for(Tile[] tilerow: enemyBoard.tiles)
            for(Tile tile: tilerow)
                if(!tile.hit)
                    tile.setEnabled(t);
    }

    void sendToOtherPlayer(int row, int col){
        try{
            serverClient.sender.writeUTF("" + row + col);
        }catch(Exception e){
            e.printStackTrace();
        }
        Tile tile = enemyBoard.tiles[row][col];
        tile.GetHit();
        if(tile.GetSituation() == TileSituation.water){
            waitForOtherPlayer();
        }else{
            updateShipNames(enemyBoard, enemyNamesPanel);
        }
    }

    void waitForOtherPlayer(){
        EnemyBoardEnabled(false);
        Thread t = new Thread(){
            public void run(){
                String request = "";
                try{
                    request = serverClient.getter.readUTF();
                }catch(Exception e){
                    e.printStackTrace();
                }
                int row = Character.getNumericValue(request.charAt(0));
                int col = Character.getNumericValue(request.charAt(1));
                Tile tile = myBoard.tiles[row][col];
                tile.GetHit();
                if(tile.GetSituation() == TileSituation.ship){
                    waitForOtherPlayer();
                    updateShipNames(myBoard, myNamesPanel);
                }else{
                    EnemyBoardEnabled(true);
                }
            }
        };
        t.start();
    }


    GamePanel(Board mine, Board enemy){
        this.serverClient = GameHandeler.serverClient;

        this.myBoard = mine;
        myBoard.setBounds(187, 139, 440, 440);
        for(Tile[] tilerow: myBoard.tiles)
            for(Tile tile: tilerow)
                tile.setEnabled(false);

        this.enemyBoard = enemy;
        enemyBoard.setBounds(653, 139, 440, 440);
        for(Tile[] tilerow: enemyBoard.tiles){
            for(Tile tile: tilerow){
                tile.addActionListener(e -> {
                    sendToOtherPlayer(tile.row, tile.col);
                });
            }
        }

        myNamesPanel = new ShipNamesPanel();
        myNamesPanel.setBounds(19, 139, 168, 440);

        enemyNamesPanel = new ShipNamesPanel();
        enemyNamesPanel.setBounds(1093, 139, 168, 440);

        if(serverClient.server == null){
            waitForOtherPlayer();
        }

        this.setBounds(0,0,1280,720);
        this.setLayout(null);
        this.add(myBoard);
        this.add(enemyBoard);
        this.add(myNamesPanel);
        this.add(enemyNamesPanel);
    }
}
