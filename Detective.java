import java.util.ArrayList;
import java.util.List;


public class Detective extends Tile {
    private String name;
    private int row;
    private int column;
    private int pos;

    public Detective(String name, int pos){
        this.name=name;
        setPos(pos);
    }

    public Detective() {

    }

    public void setPos(int n) {
        this.pos=n;
        if(n<=3){
            this.setColumn(n);
            this.setRow(0);
        }
        else if(n<=6){
            this.setColumn(4);
            this.setRow(n-3);
        }
        else if(n<=9){
            this.setColumn(10-n);
            this.setRow(4);
        }
        else if(n<=12){
            this.setColumn(0);
            this.setRow(13-n);
        }
    }

    public String getName(){
        return name;
    }
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
    public int getPos(){ return pos;}
    public void setRow(int row){this.row = row; }
    public void setColumn(int column){this.column = column; }




    @Override
    public String toString(){
        return this.name;
    }


}