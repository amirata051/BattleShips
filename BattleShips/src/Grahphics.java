import javax.swing.JFrame;

public class Grahphics extends JFrame{
    PreGamePanel preGamePanel;
    GamePanel gamePanel;

    public void StartGame(){
        preGamePanel.setVisible(false);
        gamePanel = new GamePanel(preGamePanel.board, GameHandeler.serverClient.syncBoards(preGamePanel.board));
        this.add(gamePanel);
    }

    public void GAMEOVER(String message){
        gamePanel.setVisible(false);
        this.add(new PostGamePanel(message));
    }

    Grahphics(){
        preGamePanel = new PreGamePanel();
        this.add(preGamePanel);
        this.setSize(1280,720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        // StartGame(); // FIXME
    }
}
