import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class snakeGame extends JPanel implements ActionListener,KeyListener
{
    private class Tile
    {
        int x,y;
        Tile(int x, int y)
        {
            this.x=x;
            this.y=y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize=25;
    //snake
    Tile snakeHead;
    ArrayList<Tile>snakeBody;
    //food
    Tile food;
    Random random;
    //logic
    Timer gameLoop; 
    int velocityX;
    int velocityY;
    boolean started=false;
    boolean gameOver=false;
    snakeGame(int boardWidth,int boardHeight)
    {
        this.boardWidth=boardWidth;
        this.boardHeight=boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snakeHead=new Tile(5,5);
        snakeBody=new ArrayList<Tile>();
        food=new Tile(10,10);
        random= new Random();
        placeFood();
        
        velocityX=0;
        velocityY=0;
        
        
        gameLoop=new Timer(100,this);
        gameLoop.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        //grid
//     for (int i=0;i<boardWidth/tileSize;i++)
//        {
//            g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
//            g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
//            
//        }
        //color_
        g.setColor(new Color(255,102, 102));//g.setColor(new Color(128, 203, 196));
        //g.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);
        g.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);
        g.setColor(new Color(128, 203, 196));// g.setColor(new Color(255,102, 102)); // Dark green shade
        g.fillRect(snakeHead.x*tileSize,snakeHead.y*tileSize,tileSize,tileSize);
        //   g.fill3DRect(snakeHead.x*tileSize,snakeHead.y*tileSize,tileSize,tileSize,true);
        
        //snakeBody
        for(int i=0;i<snakeBody.size();i++)
        {
            Tile snakePart=snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize,snakePart.y * tileSize,tileSize,tileSize);
            g.fill3DRect(snakePart.x * tileSize,snakePart.y * tileSize,tileSize,tileSize,true);
        }
        
        //score
        g.setFont(new Font("Arial",Font.PLAIN,17));
        if(gameOver)
        {
            g.setColor(Color.red);
            g.drawString("GAME OVER hehehe: "+ String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
        else
        {
            g.drawString("Score:" + String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
        if( ! started)
        {
            
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Controls: SPACE : Stop & R : Restart", 10, 40);
            g.drawString("arrow keys : Move", 10, 60);
            
             g.drawString("W & E : Diagonally left & right " ,10,80);
             g.drawString("S & D : Diagonally left & right " ,10,100);
        }
    }
    
    public void placeFood()
    {
        food.x=random.nextInt(boardWidth/tileSize);
        food.y=random.nextInt(boardHeight/tileSize);
    }
    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }
    public void move()
    {
        if(collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
            
        }
        
        //snake body
        for(int i=snakeBody.size()-1;i>=0;i--)
        {
            Tile snakePart=snakeBody.get(i);
            if(i==0)
            {
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else
            {
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }
        
        
        //snake head
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;
        
        //game over conditions
        for(int i=0;i<snakeBody.size();i++)
        {
            Tile snakePart=snakeBody.get(i);
            if(collision(snakeHead, snakePart))
            {
               gameOver=true;
                
            }
        }
        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize> boardWidth || snakeHead.y*tileSize<0 || snakeHead.y*tileSize> boardHeight)
        {
           // gameOver=true;
        }
        
    }
    //rstrt
    public void restartGame() 
    {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        placeFood();
        velocityX = 0;
        velocityY = 0;
        gameOver = false;
        started=true;
        gameLoop.start();
        repaint();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        move();
        repaint();
        if(gameOver)
        {
            gameLoop.stop();
        }
    }
    public void keyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();
        if (! started && (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT))
        {
           started = true;
        }
        else if(key==KeyEvent.VK_SPACE)
        {
            started=false;
        }
        if(key==KeyEvent.VK_UP  && velocityY != 1)
        {
            velocityX=0;
            velocityY=-1;
        }
        else if(key==KeyEvent.VK_DOWN && velocityY != 1)
        {
            velocityX=0;
            velocityY=1;
        }
        else if(key==KeyEvent.VK_LEFT && velocityX != 1)
        {
            velocityX=-1;
            velocityY=0;
        }
        else if(key==KeyEvent.VK_RIGHT && velocityX != 1)
        {
            velocityX=1;
            velocityY=0;
        }
        else if(key==KeyEvent.VK_SPACE)
        {
            velocityX=0;
            velocityY=0;
        }
        else if(key==KeyEvent.VK_D)
        {
            velocityX=2;
            velocityY=1;
        }
        else if(key==KeyEvent.VK_E)
        {
            velocityX=2;
            velocityY=-1;
        }
        else if(key==KeyEvent.VK_W)
        {
            velocityX=-2;
            velocityY=-1;
        }
        else if(key==KeyEvent.VK_S)
        {
            velocityX=-2;
            velocityY=1;
        }
        else if (key == KeyEvent.VK_R && gameOver) 
        {
            restartGame();
        }
        
    }
    
    
    public void keyTyped(KeyEvent e)
    {}
    public void keyReleased(KeyEvent e)
    {}
    
    
    
    
}


