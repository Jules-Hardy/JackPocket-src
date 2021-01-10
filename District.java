import java.util.List;
public class District extends Tile {
    private Alibi alibi;
    private boolean north, east, south, west;


    public District(Alibi alibi, List<Boolean> orientation) {
        this.alibi = alibi;
        this.north = orientation.get(0);
        this.east = orientation.get(1);
        this.south = orientation.get(2);
        this.west = orientation.get(3);
    }

    public Alibi getAlibi(){
        return alibi;
    }

    public boolean isNorth(){
        return north;
    }

    public boolean isEast(){
        return east;
    }

    public boolean isSouth(){
        return south;
    }

    public boolean isWest(){
        return west;
    }

    public void move(int value){
        if(north == false){

        }

    }

    public static void exchange(District districtChoiced1, District districtChoiced2){

    }

    @Override
    public String toString(){
        String s;
        s = this.alibi.toString();
        if(this.isWest()) s+="<";
        if(this.isNorth()) s+="^";
        if(this.isSouth()) s+="v";
        if(this.isEast()) s+=">";
        return s;
    }

}