package me.morde.snake.game;

//Constants used to determine GameBoard difficulty level
public enum Difficulty
{
    EASY(64, 150),
    MEDIUM(32, 80),
    HARD(32, 45);

    private int unitSize;
    private int tickSpeed;
    protected int getUnitSize() {return unitSize;}
    protected int getTickSpeed(){return  tickSpeed;}
    Difficulty(int unitSize, int tickSpeed)
    {
        this.unitSize = unitSize;
        this.tickSpeed = tickSpeed;
    }
}
