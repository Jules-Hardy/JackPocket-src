import java.util.ArrayList;
import java.util.List;


public class Detective extends Tile {
    private String name;
    private int row;
    private int column;

    public Detective(String name,int row, int column){
        this.name=name;
        this.row=row;
        this.column=column;
    }

    public Detective(){
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
    public void setRow(int row){this.row = row; }
    public void setColumn(int column){this.column = column; }


    public void move(int steps){
        if(getRow() == 1) {
            setRow(1);
        }
    }

    public List<Alibi> seeAlibis(Tile[][] board){
        List<Alibi> visibleAlibis=new ArrayList<>();
        int nextAlibi=1;
        if (row==0){
            if (((District) board[row+nextAlibi][column]).isNorth()){
                District district = (District) board[row + nextAlibi][column];
                visibleAlibis.add(district.getAlibi());
                while (district.isSouth()&&nextAlibi<3){
                    nextAlibi++;
                    District nextDistrict=(District) board[row+nextAlibi][column];
                    if (nextDistrict.isNorth()){
                        visibleAlibis.add(nextDistrict.getAlibi());
                        district=nextDistrict;
                    }
                }
            }
        }else if (row==4){
            if (((District)board[row-nextAlibi][column]).isSouth()){
                District district=(District)board[row-nextAlibi][column];
                visibleAlibis.add(district.getAlibi());
                while(district.isNorth()&&nextAlibi<3){
                    nextAlibi++;
                    District nextDistrict = (District) board[row-nextAlibi][column];
                    if (nextDistrict.isSouth()){
                        visibleAlibis.add(nextDistrict.getAlibi());
                        district=nextDistrict;
                    }
                }
            }
        }
        if(column==0){
            if (((District)board[row][column+nextAlibi]).isWest()){
                District district = (District)board[row][column+nextAlibi];
                visibleAlibis.add(district.getAlibi());
                while (district.isEast()&&nextAlibi<3){
                    nextAlibi++;
                    District nextDistrict=(District)board[row][column+nextAlibi];
                    if (nextDistrict.isWest()){
                        visibleAlibis.add(nextDistrict.getAlibi());
                        district=nextDistrict;
                    }
                }
            }
        }else if(column==4){
            if (((District)board[row][column-nextAlibi]).isEast()){
                District district=(District)board[row][column-nextAlibi];
                visibleAlibis.add(district.getAlibi());
                while (district.isWest()&&nextAlibi<3){
                    nextAlibi++;
                    District nextDistrict=(District)board[row][column-nextAlibi];
                    if(nextDistrict.isEast()){
                        visibleAlibis.add(nextDistrict.getAlibi());
                        district=nextDistrict;
                    }
                }
            }
        }return visibleAlibis;
    }

    @Override
    public String toString(){
        return this.name;
    }


}