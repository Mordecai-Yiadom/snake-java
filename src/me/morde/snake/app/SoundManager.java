package me.morde.snake.app;

import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

//Singleton Class responsible for playing
public class SoundManager
{
    private static final SoundManager SOUND_MANAGER = new SoundManager();

    private int sfxVolume, musicVolume;

    public void playSound(Sound sound)
    {
        try
        {
            URL path = getClass().getResource(sound.getFilePath());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            clip.start();
        }
        catch(Exception ex)
        {ex.printStackTrace();}
    }

    protected void setSFXVolume()
    {}


    //Singleton implementation
    private SoundManager()
    {
        sfxVolume = 100;
        musicVolume = 100;
    }
    public static SoundManager getInstance()
    {return SOUND_MANAGER;}


    //Contains Game (and GUI) Sounds
    public enum Sound
    {
        APPLE_COLLISION("/assets/sound/game/apple_collision.wav"),
        BODY_COLLISION("/assets/sound/game/body_collision.wav"),
        BOUNDARY_COLLISION("/assets/sound/game/boundary_collision.wav"),
        SET_EASY("/assets/sound/game/set_easy.wav"),
        SET_MEDIUM("/assets/sound/game/set_medium.wav"),
        SET_HARD(("/assets/sound/game/set_hard.wav"));

        private String filePath;
        protected String getFilePath()
        {return filePath;}

        Sound(String path)
        {
            this.filePath = path;
        }
    }

}
