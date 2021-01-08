import java.util.*;

public class Alibi {
    private String name;
    private int hourglassCount;

    public Alibi(String name, int hourglassCount){
        this.name=name;
        this.hourglassCount=hourglassCount;
    }

    @Override
    public String toString(){
        return name;
    }

}