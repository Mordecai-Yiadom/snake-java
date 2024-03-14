package me.morde.snake.app;

import me.morde.snake.game.*;

import java.awt.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;


/* IMPORTANT CLASS DESCRIPTION
 *-----------------------------------------------------------------------------------
 *  Singleton class (use AppManager.getInstance() to access instance)
 *  This class manages general application logic
 * -----------------------------------------------------------------------------------
 */
public class AppManager implements WindowListener
{
    //Singleton variable
    private static final AppManager APP_MANAGER = new AppManager();

    //GLOBAL APP CONSTANTS
        public static final int SCREEN_WIDTH = 1024;
        public static final int SCREEN_HEIGHT = 768;
        public static final String APP_FONT = "Consolas";
        public static final String VERSION = "BETA 1.0";
        public static final String CREDIT = "©MJ";

        public static final String FRAME_ICON_PATH = "/assets/textures/gui/frame_icon.png";

    //Instance variables
    private AppFrame appFrame;
    private TitleScreen titleScreen;
    private GameBoard gameBoard;
    private GameBoardFactory gameBoardFactory;
    private SettingsMenu settingsMenu;
    private AppDataManager appDataManager;

    private AppManager()
    {
        initComponents();
    }
    private void initComponents()
    {
        appFrame = new AppFrame();

        titleScreen = new TitleScreen();
        appDataManager = AppDataManager.getInstance();

        try
        {
            appDataManager.readScore();
            appDataManager.readSettings();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        gameBoardFactory = new GameBoardFactory();

        settingsMenu = new SettingsMenu();
    }

    //Launches App from TitleScreen || to be used by client methods (main() in this application)
    public static void launchApp()
    {
        getInstance().loadGUITextures();

        APP_MANAGER.displayTitleScreen();
    }


    //Display Methods
    public void displayTitleScreen(){appFrame.displayScreen(titleScreen);}
    public void displayGameBoard()
    {
        this.gameBoard = gameBoardFactory.createGameBoard();
        gameBoard.startGame();
        displayScreen(gameBoard);
    }
    public void displayPauseMenu() {appFrame.getGlassPane().setVisible(true);}
    public void hidePauseMenu(){appFrame.getGlassPane().setVisible(false);}

    public void displaySettingsMenu() {appFrame.displayScreen(settingsMenu);}
    public void displayScreen(Container container){appFrame.displayScreen(container);}

    //Requests repaint on AppManager instance's AppFrame (USE WISELY)
    public void requestRepaint() {appFrame.repaint();}

    public void resetGameBoard()
    {
        gameBoard.terminateGame();
        displayGameBoard();
    }

    private void loadGUITextures()
    {
        URL image = getClass().getResource(FRAME_ICON_PATH);
        appFrame.setIconImage(new ImageIcon(image).getImage());
    }

    public void closeApp()
    {
        try
        {
           appDataManager.writeScore();
           appDataManager.writeSettings();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }

    //Singleton access method
    public static AppManager getInstance() {return APP_MANAGER;}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e)
    {
        closeApp();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
