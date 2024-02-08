package me.morde.snake.game;

import me.morde.snake.app.AppManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyActionFactory
{
    public KeyListener createKeyAction(KeyActionType type)
    {
        switch(type)
        {

            case ESC_TO_PAUSE:
                KeyListener escToPause = new KeyAdapter()
                {
                    @Override
                    public void keyPressed(KeyEvent e)
                    {
                        if(e.getKeyCode() == KeyEvent.VK_UP)
                        {

                            AppManager.getInstance().displayPauseMenu();
                        }
                    }
                };
                return escToPause;

            case F3_TO_DEBUG:
                KeyListener f3ToPause = new KeyAdapter()
                {
                    @Override
                    public void keyPressed(KeyEvent e)
                    {
                        if(e.getKeyCode() == KeyEvent.VK_F3)
                        {
                            if(!GameBoard.getDebugEnabled()){GameBoard.setDebugEnabled(true);}
                            else if (GameBoard.getDebugEnabled()){GameBoard.setDebugEnabled(false);}
                            AppManager.getInstance().requestRepaint();
                        }
                    }
                };
                return f3ToPause;
        }
        return null;
    }

    public enum KeyActionType
    {
        ESC_TO_PAUSE,
        ESC_TO_RETURN,
        F3_TO_DEBUG,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
