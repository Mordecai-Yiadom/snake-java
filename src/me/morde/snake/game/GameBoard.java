package me.morde.snake.game;

import me.morde.snake.app.AppDataManager;
import me.morde.snake.app.AppManager;
import me.morde.snake.app.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Responsible for Rendering and Managing Snake Gameplay
public class GameBoard extends JPanel {

    private static boolean debugEnabled = false; //DEFAULT VALUE
    private static Difficulty difficulty = Difficulty.EASY;

    //Instance Variables
    private int UNIT_SIZE;
    private int UNIT_COUNT = difficulty.getUnitSize();
    private final KeyActionFactory keyActionFactory = new KeyActionFactory();


    //GamePlay Variables
    private int appleX, appleY;
    private int snakeHeadX, snakeHeadY;
    private Timer timer;
    private int[] snakeBodies_X, snakeBodies_Y;
    private int snakeLength;
    private Direction direction;
    private int score;
    private boolean isDirectionChangeable;

    //Constants
        private static final Color SNAKE_HEAD_COLOR = new Color(6, 93, 25);
        private static final Color SNAKE_BODY_COLOR = new Color(64, 204, 25);
        private static final int STARTING_LENGTH = 2;

        private static final int SCORE_FONT_SIZE = 40;
        private static final Font SCORE_FONT = new Font(AppManager.APP_FONT, Font.BOLD + Font.ITALIC, SCORE_FONT_SIZE);

        private static final Color BOARD_COLOR = new Color(0, 0, 0, 255);

    protected GameBoard()
    {
        super();
        initComponents();
        init();
    }

    private void init()
    {
        setFocusable(true);
        setPreferredSize(new Dimension(AppManager.SCREEN_WIDTH, AppManager.SCREEN_HEIGHT));
        setBackground(BOARD_COLOR);
        setLayout(null);

        addKeyListener(keyActionFactory.createKeyAction(KeyActionFactory.KeyActionType.ESC_TO_PAUSE));
        addKeyListener(keyActionFactory.createKeyAction(KeyActionFactory.KeyActionType.F3_TO_DEBUG));

        //Add Movement Keys Listeners
        addKeyListener(UP);
        addKeyListener(DOWN);
        addKeyListener(LEFT);
        addKeyListener(RIGHT);

        //Debug Feature
        addKeyListener(RESET_LEVEL);

    }

    private void initComponents()
    {}

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //If DebugEnabled is true, debug features will be rendered on board
        if (debugEnabled)
        {
            //Vertical Lines
            for (int x = 0; x <= AppManager.SCREEN_WIDTH / UNIT_SIZE; x++)
            {
                graphics.drawLine(UNIT_SIZE * x, AppManager.SCREEN_HEIGHT, UNIT_SIZE * x, 0);
            }
            //Horizontal Lines
            for (int y = 0; y <= AppManager.SCREEN_HEIGHT / UNIT_SIZE; y++)
            {
                graphics.drawLine(0, UNIT_SIZE * y, AppManager.SCREEN_WIDTH, UNIT_SIZE * y);
            }
        }
        //Draw Apple
        graphics.setColor(Color.RED);
        graphics.drawRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        graphics.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        //Draw SnakeBodies
        graphics.setColor(SNAKE_BODY_COLOR);
        for(int i = 0; i <= snakeLength - 1; i++)
        {
            graphics.drawRect(snakeBodies_X[i], snakeBodies_Y[i], UNIT_SIZE, UNIT_SIZE);
            graphics.fillRect(snakeBodies_X[i], snakeBodies_Y[i], UNIT_SIZE, UNIT_SIZE);
        }

        //Draw SnakeHead
        graphics.setColor(SNAKE_HEAD_COLOR);
        graphics.drawRect(snakeHeadX, snakeHeadY, UNIT_SIZE, UNIT_SIZE);
        graphics.fillRect(snakeHeadX, snakeHeadY, UNIT_SIZE, UNIT_SIZE);

        //Draw Score
        graphics.setColor(Color.WHITE);
        graphics.setFont(SCORE_FONT);
        graphics.drawString(Integer.toString(score), AppManager.SCREEN_WIDTH - 50, 30); //BANDAGE FIX

    }

    //Setter and Getter Methods for reading and writing static variables "difficulty" and "debugEnabled"
    public static void setDebugEnabled(boolean db){debugEnabled = db;}
    public static boolean getDebugEnabled(){return debugEnabled;}
    public static void setDifficulty(Difficulty d){difficulty = d;}
    public static Difficulty getDifficulty(){return difficulty;}

    private synchronized void setDirection(Direction direction){this.direction = direction;}
    private synchronized Direction getDirection(){return this.direction;}

    //Initializes game variables, then starts Timer
    public void startGame()
    {
        //Initialize Game Values
        this.UNIT_SIZE = difficulty.getUnitSize();
        this.UNIT_COUNT = (AppManager.SCREEN_WIDTH / UNIT_SIZE) * (AppManager.SCREEN_HEIGHT / UNIT_SIZE);

        //Set SnakeHead X and Y to random Location on GameBoard (in line with the unseen unit grid (use setDebugEnabled(true) to see unit grid))
        this.snakeHeadX = UNIT_SIZE * (int)  (Math.random() * (AppManager.SCREEN_WIDTH/UNIT_SIZE));
        this.snakeHeadY = UNIT_SIZE * (int)  (Math.random() * (AppManager.SCREEN_HEIGHT/UNIT_SIZE));

        snakeBodies_X = new int[UNIT_COUNT];
        snakeBodies_Y = new int[UNIT_COUNT];

        generateStartingBodies();
        generateApple();

        direction = Direction.NORTH;

        isDirectionChangeable = true;

        this.timer = new Timer(difficulty.getTickSpeed(), new GameCycle());

        timer.start();
    }
    public void terminateGame()
    {
        timer.stop();

        //Determine High Score
        switch(this.difficulty)
        {
            case EASY:
                if(this.score > AppDataManager.getInstance().getEasyScore())
                    AppDataManager.getInstance().setEasyScore(this.score);
                break;
            case MEDIUM:
                if(this.score > AppDataManager.getInstance().getMediumScore())
                    AppDataManager.getInstance().setMediumScore(this.score);
                break;
            case HARD:
                if(this.score > AppDataManager.getInstance().getHardScore())
                    AppDataManager.getInstance().setHardScore(this.score);
                break;
        }



        /* TODO || make a GAME OVER screen that also shows score as well as previous high score */

        if(!debugEnabled){AppManager.getInstance().displayTitleScreen();}
    }

    /*
     *   To be called upon initial start up as well as when apple is eaten
     */
    private void generateApple()
    {
        appleX = UNIT_SIZE * (int) (Math.random() * (AppManager.SCREEN_WIDTH/UNIT_SIZE));
        appleY = UNIT_SIZE * (int) (Math.random() * (AppManager.SCREEN_HEIGHT/UNIT_SIZE));

        //Checks if snake head is already in generated position
        if(appleX == snakeHeadX && appleY == snakeHeadY)
            generateApple();

        //Checks if ANY snake body is already in generated position
        for(int i = 0; i < snakeBodies_X.length; i++)
            if(appleX == snakeBodies_X[i] && appleY == snakeBodies_Y[i])
                generateApple();
    }

    //Encapsulating Method
    protected synchronized void move()
    {
        moveSnakeBody();
        moveSnakeHead();
        if(debugEnabled){System.out.printf("X: %d || Y: %d\n", snakeHeadX, snakeHeadY);}

    }

    //To be called ONLY by move()
    private void moveSnakeHead()
    {
        switch(this.direction)
        {
            case NORTH:
                snakeHeadY -= UNIT_SIZE;
                return;
            case SOUTH:
                snakeHeadY += UNIT_SIZE;
                return;
            case EAST:
                snakeHeadX += UNIT_SIZE;

                return;
            case WEST:
                snakeHeadX -= UNIT_SIZE;

        }
    }

    //To be called ONLY by move()
    private void moveSnakeBody()
    {
        //Update Body X + Y Values (Last to First)
        for(int i = snakeLength - 1; i >= 0; i--)
        {
            if(i == 0) {snakeBodies_X[i] = snakeHeadX;}
            else{snakeBodies_X[i] = snakeBodies_X[i-1];}

            if(i == 0) {snakeBodies_Y[i] = snakeHeadY;}
            else{snakeBodies_Y[i] = snakeBodies_Y[i-1];}

        }
    }

    private boolean appleCollision()
    {
        if(snakeHeadX == appleX && snakeHeadY == appleY)
        {
            /*
             * TODO add "Blitz" mode
             *   difficulty where body increases randomly
             */
            addBody(1);
            generateApple();
            if((float) score % 10 == 0 && score != 0)
            {score+= 2;}
            else{score++;}

            SoundManager.getInstance().playSound(SoundManager.Sound.APPLE_COLLISION);
            return true;
        }
        return false;
    }

    private boolean bodyCollision()
    {
        for(int i = 0; i <= snakeLength; i++)
        {
            if(snakeHeadX == snakeBodies_X[i] && snakeHeadY == snakeBodies_Y[i])
            {
                SoundManager.getInstance().playSound(SoundManager.Sound.BODY_COLLISION);
                terminateGame();
                return true;
            }
        }
        return false;
    }
    private boolean boundaryCollision()
    {
        if((snakeHeadX >= AppManager.SCREEN_WIDTH || snakeHeadX < 0 )
                || (snakeHeadY >= AppManager.SCREEN_HEIGHT || snakeHeadY < 0))
        {
            SoundManager.getInstance().playSound(SoundManager.Sound.BOUNDARY_COLLISION);
            terminateGame();
            return true;
        }
        return false;
    }

    private void addBody(int num)
    {
        snakeLength += num;
    }

    //BANDAGE IMPLEMENTATION
    private void generateStartingBodies()
    {
        for(int i = 0; i <= STARTING_LENGTH; i++)
        {
            snakeBodies_Y[i] = snakeHeadY + UNIT_SIZE;
            snakeBodies_X[i] = snakeHeadX;
        }
        addBody(STARTING_LENGTH);
    }

    public void setScore(int score){this.score = score;}
    public int getScore(){return this.score;}



    //Used to determine snake head direction
    private enum Direction {NORTH,SOUTH,EAST,WEST}


    /***************************************************
                    Nested Utility Classes
     *****************************************************/


    //To be used to instantiate Timer for GameBoard
    private class GameCycle implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            boundaryCollision();
            bodyCollision();
            appleCollision();
            AppManager.getInstance().requestRepaint();

            move();
            AppManager.getInstance().requestRepaint();

            isDirectionChangeable = true;
        }
    }


    //Defining Key Listeners for Movement Keys
    private final KeyListener UP = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(!isDirectionChangeable){return;}
            if(!((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_W))){return;}
            if(!(getDirection() == Direction.SOUTH)){setDirection(Direction.NORTH);}

            isDirectionChangeable = false;


            if(debugEnabled)
            {
                System.out.println(getDirection());

            }
        }
    };

    private final KeyListener DOWN = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(!isDirectionChangeable){return;}
            if(!((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_S))){return;}
            if(!(getDirection() == Direction.NORTH)){setDirection(Direction.SOUTH);}

            isDirectionChangeable = false;

            if(debugEnabled)
            {
                System.out.println(getDirection());

            }
        }
    };

    private final KeyListener LEFT = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(!isDirectionChangeable){return;}
            if(!((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_A))){return;}
            if(!(getDirection() == Direction.EAST)) {setDirection(Direction.WEST);}

            isDirectionChangeable = false;

            if(debugEnabled)
            {
                System.out.println(getDirection());

            }
        }
    };

    private final KeyListener RIGHT = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(!isDirectionChangeable){return;}
            if(!((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_D))){return;}
            if(!(getDirection() == Direction.WEST)){setDirection(Direction.EAST);}

            isDirectionChangeable = false;

            if(debugEnabled)
            {
                System.out.println(getDirection());

            }
        }
    };

    private final KeyListener RESET_LEVEL = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(e.getKeyCode() == KeyEvent.VK_F1)
            {
                AppManager.getInstance().resetGameBoard();
            }
        }
    };

}
