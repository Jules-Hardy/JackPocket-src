import java.util.Random;

public class District extends Tile {
    private Alibi alibi;
    private static char[] orientations = {'N', 'E', 'S', 'W'};
    private char orientation;
    private int orientationN;
    private boolean isFliped;


    public District(Alibi alibi) {
        this.alibi = alibi;
        Random r = new Random();
        this.orientationN = r.nextInt(4) + 1;
        this.orientation = orientations[this.orientationN - 1];
        this.isFliped = false;
    }



    public void rotate(int n){
        if(n==0) {
            return;
        }
        this.orientationN = this.orientationN == 4 ? 1 : this.orientationN + 1;
        this.orientation = orientations[this.orientationN - 1];
        this.rotate(n-1);
    }

    public void flip() {
        this.isFliped = !this.isFliped;
    }

    public Alibi getAlibi() {
        return this.alibi;
    }

    public void setOrientation(char n){this.orientation = n;}

    public char getOrientation(){return this.orientation;}

    public boolean getFlip(){return this.isFliped;}


    @Override
    public String toString() {
        String s = "";
        if(!this.isFliped) {
            s = this.alibi.toString();
        }
        if (this.orientation=='W') s += "╣";
        if (this.orientation=='N') s += "╩";
        if (this.orientation=='S') s += "╦";
        if (this.orientation=='E') s += "╠";
        return s;
    }
}
