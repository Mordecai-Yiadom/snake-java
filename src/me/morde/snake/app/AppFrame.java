package me.morde.snake.app;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame
{
    protected AppFrame()
    {
        super("Snake!");
        init();
    }

    private void init()
    {
        setSize(AppManager.SCREEN_WIDTH, AppManager.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /* Called by AppManager to display given container to screen
     * throws NullPointerException if container is null
     */
    protected void displayScreen(Container container)
    {
        if(container == null)
            {throw new NullPointerException("Passed Container <" + container.getClass().getName()+ "> cannot be null!");}
        else
        {
            setContentPane(container);
            pack();
            repaint();

            if(container.isFocusable()){container.requestFocusInWindow();}

            if(!isVisible()){setVisible(true);}
        }
    }
}
