package me.morde.snake.app;

import me.morde.snake.game.KeyActionFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitleScreen extends JPanel {
    //Instance Variables
    private String title;
    private ActionFactory actionFactory = new ActionFactory();

    //Private Constants
        private static final int TITLE_FONT_SIZE = 150;
        private static final int VERSION_FONT_SIZE = 30;
        private static final int BUTTON_FONT_SIZE = 25;

        private static final Font TITLE_FONT = new Font(AppManager.APP_FONT, Font.BOLD, TITLE_FONT_SIZE);
        private static final Font VERSION_FONT = new Font(AppManager.APP_FONT, Font.BOLD, VERSION_FONT_SIZE);
        private static final Font BUTTON_FONT = new Font(AppManager.APP_FONT, Font.BOLD, BUTTON_FONT_SIZE);

        private static final int VERSION_OFFSET = 10;
        private static final int VERSION_X = 0 + VERSION_OFFSET;
        private static final int VERSION_Y = AppManager.SCREEN_HEIGHT - VERSION_OFFSET;

        private static final int BUTTON_WIDTH = 350;
        private static final int BUTTON_HEIGHT = 75;
        private static final int BUTTON_ORIGIN_X = (AppManager.SCREEN_WIDTH/2) - (BUTTON_WIDTH/2);
        private static final int BUTTON_ORIGIN_Y = 300;

        private static final int TITLE_X_OFFSET = 30;
        private static final int TITLE_Y_OFFSET = 50;

        private static final int TITLE_X = BUTTON_ORIGIN_X - TITLE_X_OFFSET;
        private static final int TITLE_Y = BUTTON_ORIGIN_Y - TITLE_Y_OFFSET;


        private static final Dimension PREFERRED_SIZE = new Dimension(AppManager.SCREEN_WIDTH, AppManager.SCREEN_HEIGHT);

        private static final Color BACKGROUND_COLOR = Color.BLACK;


    protected TitleScreen()
    {
        super();
        initComponents();
        init();
    }

    private void init()
    {
        this.title = "SNAKE";

        setBackground(BACKGROUND_COLOR);
        setForeground(Color.WHITE);
        setPreferredSize(PREFERRED_SIZE);
        setLayout(null);
        setFocusable(true);

    }

    private void initComponents()
    {
        //Initialize and Add JButtons to TitleScreen
        JButton playButton = createButton("Play", actionFactory.createActionListener(ActionFactory.ActionType.DISPLAY_GAME_BOARD));
        playButton.setBounds(BUTTON_ORIGIN_X, BUTTON_ORIGIN_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(playButton);

        JButton settingsButton = createButton("Settings",actionFactory.createActionListener(ActionFactory.ActionType.DISPLAY_SETTINGS_SCREEN));
        settingsButton.setBounds(BUTTON_ORIGIN_X, BUTTON_ORIGIN_Y + 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(settingsButton);

        JButton quitGameButton = createButton("Quit Game", actionFactory.createActionListener(ActionFactory.ActionType.QUIT_GAME));
        quitGameButton.setBounds(BUTTON_ORIGIN_X, settingsButton.getY() + 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(quitGameButton);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        //Set graphics attributes
        graphics.setColor(Color.WHITE);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //Draw Title
        graphics.setFont(TITLE_FONT);
        graphics.drawString(this.title, TITLE_X, TITLE_Y);

        //Draw Version Number
        graphics.setFont(VERSION_FONT);
        graphics.drawString(AppManager.VERSION, VERSION_X, VERSION_Y);

        //Draw Credit
        graphics.drawString(AppManager.CREDIT, AppManager.SCREEN_WIDTH - 60, VERSION_Y);
    }

    //Private Factory Method for creating JButtons to be added to title screen
    private static JButton createButton(String label, ActionListener actionListener)
    {
        JButton button = new JButton(label);

        button.setBackground(BACKGROUND_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.addActionListener(actionListener);
        button.setFont(BUTTON_FONT);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));

        /* IMPORTANT NOTE
         * ---------------------
         *
         * NEED to add Button "Roll Over Animation"
         *  -> Mouse listener that triggers when cursor is over Button in question
         *      -> Sets Background Color of Button to an accent color (gray) || over time?
         *          -> If Mouse leaves Button after changing color, listener sets color back to original (black)
         */

        return button;
    }
}
