package sweeper;

class Flag
{
    private Matrix flagMap;
    private int countOfclosedBoxes;

    void start ()
    {
        flagMap = new Matrix(Box.CLOSED);
        countOfclosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord)
    {
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord)
    {
        flagMap.set(coord, Box.OPENED);
        countOfclosedBoxes--;
    }

    void toggleFlagedToBox (Coord coord)
    {
        switch (flagMap.get(coord))
        {
            case FLAGED: setClosedToBox (coord); break;
            case CLOSED: setFlagedToBomb(coord); break;
        }
    }

    private void setClosedToBox(Coord coord)
    {
        flagMap.set(coord, Box.CLOSED);
    }

    public void setFlagedToBomb(Coord coord)
    {
        flagMap.set(coord, Box.FLAGED);
    }

    int getCountClosedBoxes()
    {
        return countOfclosedBoxes;
    }

    void setBombedToBox(Coord coord)
    {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord)
    {
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedSafeBox(Coord coord)
    {
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NOBOMB);
    }

    int getCountOfFlagedBoxesAround (Coord coord)
    {
        int count = 0;
        for (Coord around : Ranges.getCoordsArround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }
}