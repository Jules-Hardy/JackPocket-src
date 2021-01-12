import java.util.*;

public class Game {
    private Tile[][] board ;
    private Detective[] detectives;
    private HashSet<Alibi> visibleAlibis;

    // On initialise le plateau
    public Player play() {

        List<Player> listJoueur = Player.definePlayer();
        Player mrJack = listJoueur.get(0);
        Player enqueteur = listJoueur.get(1);

        Tile board[][] = initialiseBoard();

        if (visibleAlibis == null) visibleAlibis = new HashSet<>();

        int nbTour = 1;
        Player winner = null;
        for(int i=0;i<4 && i>1;i++){
            for(int j=0;j<4 && j>1;j++){
                board[i][j].
            }
        }


            while (Math.max(mrJack.getNbSablier(), enqueteur.getNbSablier()) < 6 && nbTour < 8) {

            this.visibleAlibis = updateVisibleAlibis();
            displayVisibleAlibis();
            printBoard();

            // DEBUT DE TOUR ET CHOIX DES ACTIONS
            System.out.println("-------- Début du tour : "+nbTour+" --------\n"+"\tRésumé des scores : \n" + "- "+mrJack.getName()+" (mrJack) : "+mrJack.getNbSablier()+ "\n- "+enqueteur.getName()+" (Enqueteur) : "+enqueteur.getNbSablier()+"\n");
            System.out.println("On lance les jetons...");
            List<String> jetons = Jeton.tourJeton();
            System.out.println("Voici les jetons tirés : "+jetons);

            if(nbTour %2 == 1){ // Tour impaire : Enqueteur commence
                while(true){
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
                    break;
                }

            }
            else if(nbTour %2 != 1) { // Tour paire : MrJack commence

            }

            // APPEL A TEMOIN
            for(Alibi alibi: visibleAlibis) {
                if(alibi.getMrJack()) {
                    System.out.println("Mr Jack est visible !");
                    enqueteur.addSablier(1);
                    break;
                }

                else{
                    System.out.println("Mr Jack n'est pas visible !");
                    mrJack.addSablier(1);
                }
            }
            // FIN APPEL A TEMOIN
            nbTour = nbTour+1;

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
        //if (detectives==null)detectives=new ArrayList<>();
        List<District> districts = createDistrict();

        for (int i=0;i<5;i++) {
            for (int j = 0; j < 5; j++) {
                this.board[i][j] = new EmptyTile();
            }
        }

        Detective sherlock = new Detective("Sherlock", 12);
        placeDetective(sherlock,12);
        Detective tobby = new Detective("Tobby", 8);
        placeDetective(tobby,8);
        Detective watson = new Detective("Watson", 4);
        placeDetective(watson,4);

        this.detectives = new Detective[]{sherlock, tobby, watson};

        for (int i=1;i<4;i++) {
            for (int j = 1; j < 4; j++) {
                this.board[i][j] = districts.remove(0);
            }
        }
        return board;
    }


    private List<District> createDistrict(){
        List<District> districts = new ArrayList<>();
        String[] names = {"Inspecteur Lestrade", "Miss Stealthy", "Jeremy Bert", "John Pizer","Joseph Lane","Madame", "Sergent Goodley", "William Gull", "Dernir perso"};
        Random rand = new Random();
        int randomNum = rand.nextInt(names.length);
        for(int i=0; i<names.length;i++){
            districts.add(new District(new Alibi(names[i],0)));
            if( i == randomNum){
                districts.get(i).getAlibi().setMrJack();
            }
        }
        Collections.shuffle(districts);
        return districts;

    }


    private void printBoard(){
        for (int i=0;i<5;i++){

            for (int j=0;j<5;j++){
                String carac = board[i][j].toString();
                int n = (22-carac.length())/2;
                String espace = "";
                for(int y=0; y <n;y++){
                    espace+=' ';
                }
                System.out.print(espace+carac+espace);
                if((22-carac.length())%2 == 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n\n");
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

    private String choix(List<String> jetons, Tile board[][], Player currentPlayer, Detective[] detectives){
        Scanner scanner = new Scanner( System.in );
        String choice = scanner.nextLine();
        if(jetons.contains(choice)){
            Detective sherlock = (Detective) detectives[0];
            Detective watson = (Detective) detectives[2];
            Detective tobby = (Detective) detectives[1];
            switch(choice){
                case "Sherlock":
                    System.out.println("De combien de case voulez vous bouger Sherlock ? 1 ou 2 ?");
                    int input = scanner.nextInt();
                    move(sherlock, input);
                    break;

                case "Tobby":
                    System.out.println("De combien de case voulez vous bouger Tobby ? 1 ou 2 ?");
                    input = scanner.nextInt();
                    move(tobby, input);
                    break;

                case "Watson":
                    System.out.println("De combien de case voulez vous bouger Watson ? 1 ou 2 ?");
                    input = scanner.nextInt();
                    move(watson, input);
                    break;

                case "Tourner  district 1":
                    System.out.println("Quel district voulez vous tourner ?");
                    Alibi alibiChoiced = new Alibi("ss", 2);
                    //districtChoiced.move();
                    currentPlayer.addSablier(alibiChoiced.getHourglassCount());
                    break;

                case "Echanger district":
                    System.out.println("Quels districts voulez vous échanger ?");
                    Alibi alibiChoiced1 = new Alibi("Alibi1", 2);
                    District districtChoiced1 = new District(alibiChoiced1);
                    Alibi alibiChoiced2 = new Alibi("Alibi2", 2);
                    District districtChoiced2 = new District(alibiChoiced2);

                    District.exchange(districtChoiced1, districtChoiced2);
                    break;

                case "Tourner  district 2":
                    //
                    break;

                case "Avancer un des détectives":
                    String ins;
                    do{
                        System.out.println("Quel joueur voulez vous avancer de 1 case ?");
                        ins = scanner.nextLine();
                        System.out.println(ins);
                    }while(false);
                    if(ins=="Sherlock"){ this.move(sherlock,1);}
                    if(ins=="Tobby"){ this.move(tobby,1);}
                    if(ins=="Watson"){ this.move(watson,1);}
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

    private void move(Detective d, int step){
        board[d.getColumn()][d.getRow()] = new EmptyTile();
        placeDetective(d, d.getPos()+step);
    }

    public void avancer(Detective d, int step){
        board[d.getColumn()][d.getRow()] = new EmptyTile();
        placeDetective(d, d.getPos()+step);
    }

    private void placeDetective(Detective d, int n){
        n = n % 13;
        if(n<=3){
            d.setPos(n);
            this.board[0][n]=d;

        }
        else if(n<=6){
            d.setPos(n);
            this.board[n-3][4]=d;
        }
        else if(n<=9){
            d.setPos(n);
            this.board[4][10-n]=d;
        }
        else if(n<=12){
            d.setPos(n);
            this.board[13-n][0]=d;
        }
    }
}