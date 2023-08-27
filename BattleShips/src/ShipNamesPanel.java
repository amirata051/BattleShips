import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class ShipNamesPanel extends JPanel{
    JLabel[] lables;
    public void strikeName(String s){
        for(JLabel l: lables){
            if(l.getText().equals(s)){
                l.setFont(new Font("Ink Free", Font.ITALIC, 20));
                l.setForeground(Color.GRAY);
            }
        }
    }

    ShipNamesPanel(){
        String[] shipNames = {"Cruiser", "Battleship", "Carrier", "Submarine", "Destroyer"};
        lables = new JLabel[5];
        for(int i = 0; i < 5; i++){
            JLabel l = new JLabel(shipNames[i]);
            l.setFont(new Font("Ink Free", Font.PLAIN, 22));
            l.setHorizontalAlignment(JLabel.CENTER);
            lables[i] = l;
            this.add(l);
        }
        this.setBackground(Color.orange);
        this.setLayout(new GridLayout(5, 1, 0, 10));
    }
}