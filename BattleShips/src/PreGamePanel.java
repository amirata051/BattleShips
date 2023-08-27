import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PreGamePanel extends JPanel{
    Board board;
    JPanel rightJPanel;
    JButton chosenShipButton;
    int length = 3;
    boolean isHorizontal;

    void locateShip(int row, int col){
        if(chosenShipButton == null)
            return;

        Ship newShip = new Ship(row, col, length, isHorizontal);
        for(Point p: newShip.tilePoints){
            if(p.row > 9 || p.col > 9)
                return;
            if(board.tiles[p.row][p.col].GetSituation() == TileSituation.ship)
                return;
        }

        for(Point p: newShip.tilePoints)
            board.tiles[p.row][p.col].setSituation(TileSituation.ship);

        newShip.name = chosenShipButton.getText();
        board.placedShips[board.placedShipsCount] = newShip;
        board.placedShipsCount++;
        rightJPanel.setVisible(board.placedShipsCount == 5);
        chosenShipButton.setVisible(false);
        chosenShipButton = null;
    }


    PreGamePanel(){
        // Board
        board = new Board();
        board.setBounds(400, 140, 525,525);
        for(Tile[] tileRow : board.tiles){
            for(Tile tile : tileRow){
                tile.addActionListener(e -> {
                    locateShip(tile.row, tile.col);
                });
            }
        }

        // Ship select
        Font myFont = new Font("Ink Free", Font.BOLD, 20);
        chosenShipButton = null;
        JPanel topButtonsPanel = new JPanel();
        topButtonsPanel.setBounds(0,0, 1280, 125);
        topButtonsPanel.setBackground(Color.yellow);
        topButtonsPanel.setLayout(new GridLayout(1,5, 30, 0));
        JButton[] topShipButtons = new JButton[5];
        for(int i = 0; i < 5; i++){
            JButton newButton = new JButton();
            topShipButtons[i] = newButton;
            newButton.setFont(myFont);
            topButtonsPanel.add(newButton);
        }
        topShipButtons[0].setText("Cruiser");
        topShipButtons[1].setText("Battleship");
        topShipButtons[2].setText("Carrier");
        topShipButtons[3].setText("Submarine");
        topShipButtons[4].setText("Destroyer");
        topShipButtons[0].addActionListener(e -> {length = 5; chosenShipButton = topShipButtons[0];});
        topShipButtons[1].addActionListener(e -> {length = 4; chosenShipButton = topShipButtons[1];});
        topShipButtons[2].addActionListener(e -> {length = 3; chosenShipButton = topShipButtons[2];});
        topShipButtons[3].addActionListener(e -> {length = 3; chosenShipButton = topShipButtons[3];});
        topShipButtons[4].addActionListener(e -> {length = 2; chosenShipButton = topShipButtons[4];});

        isHorizontal = true;
        JButton vectorButton = new JButton("Horizontal");
        vectorButton.setFont(myFont);
        vectorButton.addActionListener(e -> {
            if(isHorizontal){
                isHorizontal = false;
                vectorButton.setText("Vertical");
            }else{
                isHorizontal = true;
                vectorButton.setText("Horizontal");
            }
        });
        topButtonsPanel.add(vectorButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(myFont);
        resetButton.addActionListener(e -> {
            for(Tile[] tileRow : board.tiles)
                for(Tile tile : tileRow)
                    tile.setSituation(TileSituation.water);

            for(JButton b: topShipButtons)
                b.setVisible(true);

            board.placedShips = new Ship[5];
            board.placedShipsCount = 0;
            chosenShipButton = null;
            rightJPanel.setVisible(false);
        });
        topButtonsPanel.add(resetButton);
        JLabel explanationLable = new JLabel("Place all your ships.");
        explanationLable.setFont(myFont);
        explanationLable.setBounds(25,140, 353, 525);

        rightJPanel = new JPanel();
        rightJPanel.setBounds(948, 488, 307, 277);
        JLabel doneLable = new JLabel("Click here to connect");
        JButton doneButton = new JButton("Connect");
        doneButton.addActionListener(e -> {
            GameHandeler.serverClient.Connect();
        });
        rightJPanel.add(doneLable);
        rightJPanel.add(doneButton);
        rightJPanel.setVisible(false);


        this.add(board);
        this.add(topButtonsPanel);
        this.add(explanationLable);
        this.add(rightJPanel);
        this.setBounds(0,0,1280,720);
        this.setLayout(null);
    }
}
