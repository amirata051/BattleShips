import javax.swing.JPanel;

import java.awt.GridLayout;

public class Board extends JPanel{
    public Tile[][] tiles;
    Ship[] placedShips;
    int placedShipsCount;
    Board(){
        placedShips = new Ship[5];
        placedShipsCount = 0;
        this.setLayout(new GridLayout(10,10));
        tiles = new Tile[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Tile newTile = new Tile(i, j);
                newTile.setSituation(TileSituation.water);
                tiles[i][j] = newTile;
                this.add(newTile);
            }
        }
    }
}
