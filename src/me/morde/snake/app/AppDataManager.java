package me.morde.snake.app;
import java.util.Scanner;
import java.io.*;
import me.morde.snake.game.*;

public class AppDataManager {
    private int easyScore, mediumScore, hardScore;
    private int SFXVolume, MusicVolume;
    private Difficulty difficulty;
    private static final AppDataManager INSTANCE = new AppDataManager();

    protected void readScore() throws Exception {
        File file = new File(getClass().getResource("/assets/appdata/.score.txt").toURI());
        Scanner scnr = new Scanner(file);

        scnr.next();
        this.easyScore = scnr.nextInt();
        scnr.nextLine();

        scnr.next();
        this.mediumScore = scnr.nextInt();
        scnr.nextLine();

        scnr.next();
        this.hardScore = scnr.nextInt();
        scnr.nextLine();
    }

    protected void readSettings() throws Exception
    {
        File file = new File(getClass().getResource("/assets/appdata/.settings.txt").toURI());
        Scanner scnr = new Scanner(file);
        scnr.next();

        //Read Difficulty
        String difficulty = scnr.next();

        switch(difficulty)
        {
            case "EASY":
                this.difficulty = Difficulty.EASY;
                scnr.nextLine();
                break;
            case "MEDIUM":
                this.difficulty = Difficulty.MEDIUM;
                scnr.nextLine();
                break;
            case "HARD":
                this.difficulty = Difficulty.HARD;
                scnr.nextLine();
                break;
        }

        GameBoard.setDifficulty(this.getDifficulty());

        //Read SFX and Music Volume
        scnr.next();
        this.SFXVolume = scnr.nextInt();
        scnr.nextLine();

        scnr.next();
        this.MusicVolume = scnr.nextInt();
        scnr.nextLine();
    }

    public void writeScore() throws Exception
    {
        File file = new File(getClass().getResource("/assets/appdata/.score.txt").toURI());

        PrintWriter writer = new PrintWriter(file);

        //Write Scores
        writer.printf("easy: %d\n", this.easyScore);
        writer.printf("medium: %d\n", this.mediumScore);
        writer.printf("hard: %d\n", this.hardScore);

        writer.flush();
    }

    public void writeSettings() throws Exception
    {
        File file = new File(getClass().getResource("/assets/appdata/.settings.txt").toURI());

        PrintWriter writer = new PrintWriter(file);

        //Write Difficulty
        writer.printf("Difficulty: %s\n", GameBoard.getDifficulty());

        //Write SFX and Music Volumes
        writer.printf("SFXVolume: %d\n", this.SFXVolume);
        writer.printf("MusicVolume: %d\n", this.MusicVolume);

        writer.flush();
    }


    //Score getters nad setters
    public int getEasyScore(){return this.easyScore;}
    public int getMediumScore(){return this.mediumScore;}
    public int getHardScore(){return this.hardScore;}

    public void setEasyScore(int score){this.easyScore = score;}
    public void setMediumScore(int score){this.mediumScore = score;}
    public void setHardScore(int score){this.hardScore = score;}

    //Settings getters and setters
    public Difficulty getDifficulty(){return this.difficulty;}
    public int getMusicVolume(){return this.MusicVolume;}
    public int getSFXVolume(){return this.SFXVolume;}

    public void setDifficulty(Difficulty difficulty){this.difficulty = difficulty;}
    public void setMusicVolume(int volume){this.MusicVolume = volume;}
    public void setSFXVolume(int volume){this.SFXVolume = volume;}

    //Singleton Constructor
    private AppDataManager(){}
    public static AppDataManager getInstance() {return INSTANCE;}
}
