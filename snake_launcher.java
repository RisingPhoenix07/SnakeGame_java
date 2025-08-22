import javax.swing.*;
public class snake_launcher
{
    public static void main(String[]args)
    {
        int boardWidth=700;
        int boardHeight=boardWidth;
        JFrame frame=new JFrame("First game trial by Rez... !! ");
        frame.setVisible(true);
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
         snakeGame snakeGame=new snakeGame(boardWidth, boardHeight);
         frame.add(snakeGame);
         frame.pack();
         snakeGame.requestFocus();
    }
}