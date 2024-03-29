package sweeper;

public class Game
{
    private Bomb bomb;
    private Flag flag;
    private GameStat state;

    public GameStat getState()
    {
        return state;
    }

    public Game (int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start ()
    {
        bomb.start();
        flag.start();
        state = GameStat.PLAYED;
    }

    public Box getBox (Coord coord)
    {
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton (Coord coord)
    {
        if (gameOver ()) return;
        openBox (coord);
        checkWinner();
    }

    private void checkWinner ()
    {
        if (state == GameStat.PLAYED)
            if (flag.getCountClosedBoxes() == bomb.getTotalBombs())
                state = GameStat.WINNER;
    }

    private void openBox(Coord coord)
    {
        switch (flag.get(coord))
        {
            case OPENED : setOpenedToClosedBoxesAroundNumber(coord);return;
            case FLAGED : return;
            case CLOSED :
                switch (bomb.get(coord))
                {
                    case ZERO: openBoxesAround (coord); return;
                    case BOMB: openBombs (coord); return;
                    default: flag.setOpenedToBox(coord);  return;
                }

        }
    }

    private void openBombs(Coord bombed)
    {
        state = GameStat.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox (coord);
            else
                flag.setNobombToFlagedSafeBox (coord);
    }

    private void openBoxesAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsArround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord)
    {
        if (gameOver ()) return;
        flag.toggleFlagedToBox (coord);
    }

    private boolean gameOver()
    {
        if (state == GameStat.PLAYED)
            return false;
        //start();
        return true;
    }


    private void setOpenedToClosedBoxesAroundNumber (Coord coord)
    {
        if (bomb.get(coord) != Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsArround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

}