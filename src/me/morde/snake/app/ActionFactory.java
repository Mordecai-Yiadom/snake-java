package me.morde.snake.app;

import java.awt.event.*;
import javax.swing.*;

//Generic UI controlling actions
public class ActionFactory
{
    public ActionListener createActionListener(ActionType type)
    {
        switch(type)
        {
            case DISPLAY_TITLE_SCREEN:
                ActionListener titleScreenAction = (event)-> {AppManager.getInstance().displayTitleScreen();};
                return titleScreenAction;
            case DISPLAY_GAME_BOARD:
                ActionListener gameBoardAction = (event)-> {AppManager.getInstance().displayGameBoard();};
                return gameBoardAction;
            case QUIT_GAME:
                ActionListener quitGameAction = (event)-> {AppManager.getInstance().closeApp();};
                return quitGameAction;
            case DISPLAY_SETTINGS_SCREEN:
                ActionListener settingsScreenAction = (event)-> {AppManager.getInstance().displaySettingsMenu();};
                return settingsScreenAction;
        }

        return null;
    }

    //Used to specify Action desired.
    public enum ActionType
    {
        DISPLAY_GAME_BOARD,
        DISPLAY_TITLE_SCREEN,
        QUIT_GAME,
        DISPLAY_SETTINGS_SCREEN;
    }
}
