import java.util.List;
public class District extends Tile {
    private Alibi alibi;
    private boolean north, east, south, west;

    public District(Alibi william_gull, List<Boolean> generateRandomOrientation) {
        super();
    }

    public void Disctrict(Alibi alibi, List<Boolean> orientation){
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

    @Override
    public String toString(){
        String s = alibi.toString();
        if(isWest()) s+="<";
        if(isNorth()) s+="^";
        if(isSouth()) s+="v";
        if(isEast()) s+=">";
        return s;
    }

}