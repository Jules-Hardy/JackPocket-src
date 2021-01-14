import java.util.Random;

public class District extends Tile {
    private Alibi alibi;
    private boolean north, east, south, west;
    private static char[] orientations = {'N', 'E', 'S', 'W'};
    private char orientation;
    private int orientationN;


    public District(Alibi alibi) {
        this.alibi = alibi;
        Random r = new Random();
        this.orientationN = r.nextInt(4) + 1;
        this.orientation = orientations[this.orientationN - 1];
        getOrientation(orientation);
    }

    public void rotate(int n){
        for(int i = 0; i < n-1; i++) {
            this.orientationN = (this.orientationN + 1) % 4;
        }
        this.orientation = orientations[this.orientationN - 1];
        getOrientation(orientation);
    }

    public Alibi getAlibi() {
        return alibi;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    public char getOrientations(){return orientation;}

    private void getOrientation(char orientation) {
        switch (orientation) {
            case 'N':
                this.north = false;
                this.south = true;
                this.east = true;
                this.west = true;
                break;
            case 'S':
                this.north = true;
                this.south = false;
                this.east = true;
                this.west = true;
                break;
            case 'E':
                this.north = true;
                this.south = true;
                this.east = false;
                this.west = true;
            case 'W':
                this.north = true;
                this.south = true;
                this.east = true;
                this.west = false;
        }
    }

    @Override
    public String toString() {
        String s;
        s = this.alibi.toString();
        if (!this.isWest()) s += "╠";
        if (!this.isNorth()) s += "╦";
        if (!this.isSouth()) s += "╩";
        if (!this.isEast()) s += "╣";
        return s;
    }

}