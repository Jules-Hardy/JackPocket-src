import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Game {
    private Tile[][] board ;
    private List<Detective> detectives;
    private HashSet<Alibi> visibleAlibis;

    // On initialise le plateau
    public void play() {
        System.out.println("Bienvenue");
        initialiseBoard();
        printBoard();
        createDistrict();
        int tour = 0;
/*
        for(int i = 0; i<=8; i++){
            if(tour %2 == 1){
                joueur1 = "Enqueteur";
                joueur2 = "MrJack";
            }
            else if(tour %2 != 1) {
                joueur1 = "MrJack";
                joueur2 = "Enqueteur";
            }
            else
                System.out.println("erreur");
            tour ++;
        }
*/
        if (visibleAlibis == null) visibleAlibis = new HashSet<>();
        visibleAlibis = updateVisibleAlibis();
        displayVisibleAlibis();
    }


    private void initialiseBoard(){
        if (board==null)board=new Tile[5][5];
        if (detectives==null)detectives=new ArrayList<>();

        List<District> districts = createDistrict();
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                if (i==0){
                    board[i][j]=new Detective();
                }
                else if(i==1){
                    if (j==0){
                        Detective sherlock= new Detective("Sherlock",i,j);
                        detectives.add(sherlock);
                        board[i][j]=sherlock;
                    }
                    else if (j==4){
                        Detective watson = new Detective("Watson",i,j);
                        detectives.add(watson);
                        board[i][j]=watson;
                    }
                    else board[i][j]=districts.remove(0);
                }
                else if(i==4){
                    if(j==2){
                        Detective toby = new Detective("Toby",i,j);
                        detectives.add(toby);
                        board[i][j]=toby;
                    }
                    else board[i][j]=new Detective();
                }
                else{
                    if (j>0&&j<4){
                        board[i][j]=districts.remove(0);
                    }
                    else{
                        board[i][j]=new Detective();
                    }
                }
            }
        }
    }


    private List<District> createDistrict(){
        List<District> districts = new ArrayList<>();
        districts.add(new District(new Alibi("Inspecteur Lestrade",0),generateRandomOrientation()));
        districts.add(new District(new Alibi("Miss Stealthy",1),generateRandomOrientation()));
        districts.add(new District(new Alibi("Jeremy Bert",1),generateRandomOrientation()));
        districts.add(new District(new Alibi("John Pizer",1),generateRandomOrientation()));
        districts.add(new District(new Alibi("Joseph Lane",1),generateRandomOrientation()));
        districts.add(new District(new Alibi("Madame",2),generateRandomOrientation()));
        districts.add(new District(new Alibi("Sgt Goodley",0),generateRandomOrientation()));
        districts.add(new District(new Alibi("William Gull",1),generateRandomOrientation()));
        districts.add(new District(new Alibi("DERNIER PERSO",1),generateRandomOrientation()));
        Collections.shuffle(districts);
        return districts;
    }

    private List<Boolean> generateRandomOrientation(){
        List<Boolean> orientation = new ArrayList<>();
        orientation.add(true);
        orientation.add(true);
        orientation.add(true);
        orientation.add(false);
        Collections.shuffle(orientation);
        return orientation;
    }

    private void printBoard(){
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                System.out.print(board[i][j].toString()+"\t");
            }
            System.out.println("");
        }
    }

    private HashSet<Alibi> updateVisibleAlibis(){
        HashSet<Alibi> visibleAlibis = new HashSet<>();
        for (Detective detective : detectives){
            visibleAlibis.addAll(detective.seeAlibis(board));
        }
        return visibleAlibis;
    }

    private void displayVisibleAlibis(){
        for (Alibi alibi : visibleAlibis){
            System.out.println(alibi.toString());
        }
    }
}