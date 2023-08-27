import java.awt.Color;

import javax.swing.JButton;

public class Tile extends JButton{
    private TileSituation situation;
    int row, col;
    boolean hidden;
    boolean hit;

    Tile(int r, int c){
        row = r;
        col = c;
        hidden = false;
        hit = false;
    }

    void GetHit(){
        hit = true;
        this.setEnabled(false);
        if(situation == TileSituation.water){
            this.setBackground(Color.black);
        }else{
            this.setBackground(Color.red);
        }
    }

    public TileSituation GetSituation(){
        return situation;
    }

    public void setSituation(TileSituation s){
        if(s == TileSituation.water || hidden){
            this.setBackground(Color.blue);
        }else if(s == TileSituation.ship){
            this.setBackground(Color.LIGHT_GRAY);
        }
        situation = s;
    }
}
