import java.util.*;

public class Alibi {
    private String name;
    private int hourglassCount;
    private boolean isMrJack;

    public Alibi(String name, int hourglassCount){
        this.name=name;
        this.hourglassCount=hourglassCount;
        this.isMrJack = false;
    }

    public void setMrJack() {
        this.isMrJack = true;
    }

    public boolean getMrJack() {
        return this.isMrJack;
    }

    public int getHourglassCount(){ return hourglassCount;}

    public String getName() {return this.name;}

    @Override
    public String toString(){
        return name;
    }

}