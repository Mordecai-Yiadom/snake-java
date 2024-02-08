package me.morde.snake.app;

import me.morde.snake.game.Difficulty;
import me.morde.snake.game.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsMenu extends JPanel
{
    //Private Constants
    private static final int TITLE_FONT_SIZE = 80;
    private static final int BUTTON_FONT_SIZE = 40;

    private static final int EXIT_FONT_SIZE = 15;

    private static final String TITLE = "<settings>";

    private static final int BUTTON_WIDTH = 350;
    private static final int BUTTON_HEIGHT = 75;
    private static final int BUTTON_ORIGIN_X = (AppManager.SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
    private static final int BUTTON_ORIGIN_Y = (AppManager.SCREEN_WIDTH/4);

    private static final int TITLE_X_OFFSET = 45;

    private static final int TITLE_X = BUTTON_ORIGIN_X - TITLE_X_OFFSET;
    private static final int TITLE_Y = 70;

    private static final Font BUTTON_FONT = new Font(AppManager.APP_FONT, Font.BOLD, BUTTON_FONT_SIZE);
    private static final Font EXIT_FONT = new Font(AppManager.APP_FONT, Font.BOLD, EXIT_FONT_SIZE);
    private static final Font TITLE_FONT = new Font(AppManager.APP_FONT, Font.BOLD, TITLE_FONT_SIZE);


    private static final Color EASY_COLOR = new Color(76, 185, 21);
    private static final Color MEDIUM_COLOR = new Color(227, 179, 11);
    private static final Color HARD_COLOR = new Color(173, 22, 22);

    private static final String HOME_ICON_PATH = "/assets/textures/gui/home_icon.png";

    protected SettingsMenu()
    {
        super();
        init();
        initComponents();
    }

    private void init()
    {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setLayout(null);
        setPreferredSize(new Dimension(AppManager.SCREEN_WIDTH, AppManager.SCREEN_HEIGHT));
        setFocusable(true);

        add(createJButton(ButtonType.SELECT_DIFFICULTY));
        add(createJButton(ButtonType.BACK_TO_MAIN_MENU));

        //TEMPORARY IMPLEMENTATION  ADD BUTTON TO EXIT
        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e) {if(e.getKeyCode() == KeyEvent.VK_ESCAPE){AppManager.getInstance().displayTitleScreen();}}
        });

    }
    private void initComponents()
    {}

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        //Draw Title
        graphics.setColor(Color.WHITE);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setFont(TITLE_FONT);
        graphics.drawString(TITLE, TITLE_X, TITLE_Y);

    }

    //Button Creation

    private JButton createJButton(ButtonType type)
    {
        switch (type)
        {
            case SELECT_DIFFICULTY:
                JButton selectDifficulty = new JButton();
                selectDifficulty.setFocusable(false);

                selectDifficulty.setBorder(BorderFactory.createRaisedBevelBorder());
                selectDifficulty.setFont(BUTTON_FONT);
                selectDifficulty.setBounds(BUTTON_ORIGIN_X, BUTTON_ORIGIN_Y,BUTTON_WIDTH, BUTTON_HEIGHT);
                selectDifficulty.setForeground(Color.WHITE);

                selectDifficulty.addActionListener(new DifficultyChangeAction());
                //Determine initial background color of Button by reading static difficulty from GameBoard class
                switch(GameBoard.getDifficulty())
                {
                    case EASY:
                        selectDifficulty.setBackground(EASY_COLOR);
                        selectDifficulty.setText("Easy");
                        return selectDifficulty;
                    case MEDIUM:
                        selectDifficulty.setBackground(MEDIUM_COLOR);
                        selectDifficulty.setText("Medium");
                        return selectDifficulty;
                    case HARD:
                        selectDifficulty.setBackground(HARD_COLOR);
                        selectDifficulty.setText("Hard");
                        return selectDifficulty;
                }

            case BACK_TO_MAIN_MENU:
                JButton mainMenu = new JButton("Main Menu");

                mainMenu.setLayout(null);

                mainMenu.setBackground(Color.BLACK);
                mainMenu.setForeground(Color.WHITE);

                mainMenu.setBounds(10, 10, 100, 50);
                mainMenu.setFocusable(false);

                mainMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                mainMenu.setFont(EXIT_FONT);
                mainMenu.addActionListener((event)->AppManager.getInstance().displayTitleScreen());
                return mainMenu;
        }
        return null;
    }

    private JSlider createJSlider(SliderType type)
    {
        JSlider slider = new JSlider(0, 100);
        slider.setBackground(Color.WHITE);

        switch(type)
        {
            case SFX_VOLUME:

            case MUSIC_VOLUME:

        }
        return null;
    }

    private enum ButtonType
    {
        SELECT_DIFFICULTY,
        ENABLE_DEBUG_MODE,
        BACK_TO_MAIN_MENU;
    }

    private enum SliderType
    {
        SFX_VOLUME,
        MUSIC_VOLUME;
    }


    //Action Listener for Difficulty Change Button
    private class DifficultyChangeAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            try {
                JButton modeButton = (JButton) getComponents()[0];

                switch (GameBoard.getDifficulty()) {
                    case EASY:
                        EventQueue.invokeLater(()->modeButton.setBackground(MEDIUM_COLOR));
                        EventQueue.invokeLater(()->modeButton.setText("Medium"));
                        GameBoard.setDifficulty(Difficulty.MEDIUM);

                        if(GameBoard.getDebugEnabled())System.out.println(GameBoard.getDifficulty());//FOR TESTING

                        return;

                    case MEDIUM:
                        EventQueue.invokeLater(()->modeButton.setBackground(HARD_COLOR));
                        EventQueue.invokeLater(()->modeButton.setText("Hard"));
                        GameBoard.setDifficulty(Difficulty.HARD);

                        if(GameBoard.getDebugEnabled())System.out.println(GameBoard.getDifficulty());//FOR TESTING

                        return;

                    case HARD:
                        modeButton.setBackground(EASY_COLOR);
                        modeButton.setText("Easy");
                        GameBoard.setDifficulty(Difficulty.EASY);

                        if(GameBoard.getDebugEnabled())System.out.println(GameBoard.getDifficulty());//FOR TESTING

                        return;
                }

            }
            catch(Exception ex){ex.printStackTrace();}
        }
    }
}
