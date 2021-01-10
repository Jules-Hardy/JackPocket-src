import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class Game {
    private Tile[][] board ;
    private List<Detective> detectives;
    private HashSet<Alibi> visibleAlibis;

    // On initialise le plateau
    public Player play() {

        List<Player> listJoueur = Player.definePlayer();
        Player mrJack = listJoueur.get(0);
        Player enqueteur = listJoueur.get(1);

        Tile board[][] = initialiseBoard();

        if (visibleAlibis == null) visibleAlibis = new HashSet<>();

        Alibi mrJackAlibi = new Alibi("MrJack", 1);

        int nbTour = 1;
        Player winner = null;
        while (Math.max(mrJack.getNbSablier(), enqueteur.getNbSablier()) < 6 && nbTour < 8) {
            if (visibleAlibis == null) visibleAlibis = new HashSet<>();
            visibleAlibis = updateVisibleAlibis();
            displayVisibleAlibis();
            printBoard();

            System.out.println("-------- Début du tour : "+nbTour+" --------\n"+"\tRésumé des scores : \n" + "- "+mrJack.getName()+" (mrJack) : "+mrJack.getNbSablier()+ "\n- "+enqueteur.getName()+" (Enqueteur) : "+enqueteur.getNbSablier()+"\n");
            System.out.println("On lance les jetons...");
            List<String> jetons = Jeton.tourJeton();
            System.out.println("Voici les jetons tirés : "+jetons);

            if(nbTour %2 == 1){
                System.out.println("L'enqueteur commence. Que voulez vous faire ?\n"+enqueteur.getName()+" -->");
                Player currentPlayer = enqueteur;
                String choice = choix(jetons, board, currentPlayer, detectives);
                jetons.remove(choice);
                printBoard();
                System.out.println("Voici les jetons restants : "+jetons);
                System.out.println("C'est au tour de Mr Jack. Que voulez vous faire ?\n"+mrJack.getName()+" -->");
                currentPlayer = mrJack;
                choice = choix(jetons, board, currentPlayer, detectives);
                jetons.remove(choice);
                System.out.println("Voici les jetons restants : "+jetons);
                System.out.println("C'est au tour de Mr Jack. Que voulez vous faire ?\n"+mrJack.getName()+" -->");
                currentPlayer = mrJack;
                choice = choix(jetons, board, currentPlayer, detectives);
                jetons.remove(choice);
                System.out.println("Voici les jetons restants : "+jetons);
                System.out.println("C'est au tour de l'inspecteur. Que voulez vous faire ?\n"+enqueteur.getName()+" -->");
                currentPlayer = mrJack;
                choice = choix(jetons, board, currentPlayer, detectives);
            }
            else if(nbTour %2 != 1) {

            }

            // APPEL A TEMOIN
            if(visibleAlibis.contains(mrJackAlibi)){
                System.out.println("Mr est visible !");
            }


            nbTour++;
            mrJack.addSablier(1);
            enqueteur.addSablier(1);

            if(mrJack.getNbSablier()>=6){
                winner = (Player) mrJack;
                break;
            }
            // Si il n'y a plus d'alibis, alors xx est le gagnant !
            else{
                winner = null;
            }
        }

        if(winner==null)
            System.out.println("La partie est terminée. MrJack est le gagnant car 8 tours ont été écoulés. Bravo "+mrJack.getName()+" !");
        else
            System.out.println("Bravo, vous avez terminé la partie ! "+winner.getName()+" est le gagnant !");
        return winner;
    }


    private Tile[][] initialiseBoard(){
        if (board==null)board=new Tile[5][5];
        if (detectives==null)detectives=new ArrayList<>();

        List<District> districts = createDistrict();
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                // On ajoute les detectives autour du plateau
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
                else
                    {
                    if (j>0&&j<4){
                        board[i][j]=districts.remove(0);
                    }
                    else{
                        board[i][j]=new Detective();
                    }
                }
            }
        }
        return board;
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

    private static List<Boolean> generateRandomOrientation(){
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
                System.out.print(board[i][j].toString()+"\t\t\t\t");
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
        System.out.println("Voici les personnes visibles par l'enqueteur :");
        for (Alibi alibi : visibleAlibis){
            System.out.println("- "+alibi.toString()+"\n");
        }
    }

    public static String choix(List<String> jetons, Tile board[][], Player currentPlayer, List detectives){
        Scanner scanner = new Scanner( System.in );
        String choice = scanner.nextLine();
        if(jetons.contains(choice)){
            Detective sherlock = (Detective) detectives.get(0);
            Detective watson = (Detective) detectives.get(1);
            Detective tobby = (Detective) detectives.get(2);
            switch(choice){
                case "Sherlock":
                    System.out.println("De combien de case voulez vous bouger Sherlock ? 1 ou 2 ?");
                    int input = scanner.nextInt();
                    sherlock.move(input);
                    if(sherlock.isCorrectPosition())
                        board[sherlock.getColumn()][sherlock.getRow()] = sherlock;
                    break;

                case "Tobby":
                    System.out.println("De combien de case voulez vous bouger Tobby ? 1 ou 2 ?");
                    input = scanner.nextInt();
                    break;

                case "Watson":
                    System.out.println("De combien de case voulez vous bouger Sherlock ? 1 ou 2 ?");
                    input = scanner.nextInt();
                    watson.move(input);
                    break;

                case "Tourner  district 1":
                    System.out.println("Quel district voulez vous tourner ?");
                    Alibi alibiChoiced = new Alibi("ss", 2);
                    District districtChoiced = new District(alibiChoiced, generateRandomOrientation());
                    //districtChoiced.move();
                    currentPlayer.addSablier(alibiChoiced.getHourglassCount());
                    break;

                case "Echanger district":
                    System.out.println("Quels districts voulez vous échanger ?");
                    Alibi alibiChoiced1 = new Alibi("Alibi1", 2);
                    District districtChoiced1 = new District(alibiChoiced1, generateRandomOrientation());
                    Alibi alibiChoiced2 = new Alibi("Alibi2", 2);
                    District districtChoiced2 = new District(alibiChoiced2, generateRandomOrientation());

                    District.exchange(districtChoiced1, districtChoiced2);
                    break;

                case "Tourner  district 2":
                    //
                    break;

                case "Avancer un des détectives":
                    System.out.println("Quel joueur voulez vous avancer de 1 case ?");
                    String ins = scanner.nextLine();
                    if(ins=="Sherlock"){ sherlock.move(1);}
                    if(ins=="Tobby"){ tobby.move(1);}
                    if(ins=="Watson"){ watson.move(1);}
                    break;

                case "Tirer carte alibi":

                    break;

            }
        }
        else {
            System.out.println("Votre choix n'est pas valable, voici les choix proposés : "+jetons);
            choix(jetons, board, currentPlayer, detectives);

        }
        return choice;
    }
}