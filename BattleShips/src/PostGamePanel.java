import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class PostGamePanel extends JLabel{
    PostGamePanel(String txt){
        this.setText(txt);
        this.setBounds(0,0,1280,720);
        this.setBackground(Color.white);
        this.setFont(new Font("Ink Free", Font.BOLD, 111));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }
}
