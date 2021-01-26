import java.util.*;
import java.util.Arrays.*;

class Game {
    private Tile[][] board;
    private Detective[] detectives;
    private ArrayList<Alibi> visibleAlibis;

    // On initialise le plateau
    public Player play() {

        List<Player> listJoueur = Player.definePlayer();
        Player mrJack = listJoueur.get(0);
        Player enqueteur = listJoueur.get(1);

        List<District> districts = createDistrict();
        List<District> districtsAlibi = new ArrayList<>(districts);
        this.initialiseBoard(districts);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nous allons picher l'alibi de MrJack... L'enqueteur ne doit pas regarder !!!\nEntrez un retour à la ligne pour voir l'identité de MrJack");
        scanner.nextLine();
        for (District district : districtsAlibi) {
            if (district.getAlibi().getMrJack())
                System.out.println("MrJack a pioché l'alibi suivant : " + district.getAlibi().getName());
        }
        System.out.println("Entrez un retour à la ligne pour voir le tableau de jeu et commencer à jouer...");
        scanner.nextLine();
        if (this.visibleAlibis == null) this.visibleAlibis = new ArrayList<Alibi>();
        int nbTour = 1;
        Player winner = null;

        while (mrJack.getNbSablier() < 6) {

            this.visibleAlibis = this.getVisible();

            printBoard();
            System.out.print("Entrez pour continuer.");
            scanner.nextLine();
            Player currentPlayer = null;

            // DEBUT DE TOUR ET CHOIX DES ACTIONS
            System.out.println("-------- Début du tour : " + nbTour + " --------\n" + "\tRésumé des scores : \n" + "- " + mrJack.getName() + " (mrJack) : " + mrJack.getNbSablier() + "\n- " + enqueteur.getName() + " (Enqueteur) : " + enqueteur.getNbSablier() + "\n");
            System.out.println("On lance les jetons...");
            List<String> jetons = Jeton.tourJeton();

            Jeton.toString(jetons);

            if (nbTour % 2 == 1) { // Tour impaire : Enqueteur commence
                System.out.println("L'enqueteur commence. Que voulez vous faire ?\n" + enqueteur.getName() + " -->");
                currentPlayer = enqueteur;
                String choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
                Jeton.toString(jetons);
                System.out.println("C'est au tour de Mr Jack. Que voulez vous faire ?\n" + mrJack.getName() + " -->");
                currentPlayer = mrJack;
                choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
                Jeton.toString(jetons);
                System.out.println("C'est au tour de Mr Jack. Que voulez vous faire ?\n" + mrJack.getName() + " -->");
                currentPlayer = mrJack;
                choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
                Jeton.toString(jetons);
                System.out.println("C'est au tour de l'inspecteur. Que voulez vous faire ?\n" + enqueteur.getName() + " -->");
                currentPlayer = enqueteur;
                choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
            } else if (nbTour % 2 != 1) { // Tour paire : MrJack commence
                System.out.println("MrJack commence. Que voulez vous faire ?\n" + mrJack.getName() + " -->");
                currentPlayer = mrJack;
                String choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
                Jeton.toString(jetons);
                System.out.println("C'est au tour de l'enquêteur. Que voulez vous faire ?\n" + enqueteur.getName() + " -->");
                currentPlayer = enqueteur;
                choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
                Jeton.toString(jetons);
                System.out.println("C'est au tour de l'enquêteur. Que voulez vous faire ?\n" + enqueteur.getName() + " -->");
                currentPlayer = enqueteur;
                choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
                Jeton.toString(jetons);
                System.out.println("C'est au tour de MrJack. Que voulez vous faire ?\n" + mrJack.getName() + " -->");
                currentPlayer = mrJack;
                choice = choix(jetons, currentPlayer, districtsAlibi);
                jetons.remove(choice);
            }

            displayVisibleAlibis();
            System.out.print("Entrez pour continuer.");
            scanner.nextLine();

            boolean isPresent = false;
            for (Alibi alibi : visibleAlibis) {
                if (alibi.getMrJack()) {
                    isPresent = true;
                    break;
                }
            }
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    District d = this.board[i][j].getDistrict();
                    if (d.getFlip()) {
                        continue;
                    }
                    if ((!isPresent && this.visibleAlibis.contains(d.getAlibi())) || (isPresent && !this.visibleAlibis.contains(d.getAlibi()))) {
                        d.flip();
                    }
                }
            }

            if (isPresent) {
                System.out.println("Mr Jack est visible !");
                enqueteur.addSablier(1);

            }
            if (!isPresent) {
                System.out.println("Mr Jack n'est pas visible !");
                mrJack.addSablier(1);
            }


            int n = 0;
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    District d = this.board[i][j].getDistrict();
                    if (d.getFlip()) {
                        n += 1;
                    }
                }
            }

            if (n == 8) {
                winner = enqueteur;
                break;
            }

            nbTour = nbTour + 1;

            if (mrJack.getNbSablier() >= 6 || nbTour >= 8) {
                winner = mrJack;
                break;
            }

        }
        return winner;

    }


    private void initialiseBoard(List<District> districts) {
        if (board == null) board = new Tile[5][5];
        //if (detectives==null)detectives=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.board[i][j] = new EmptyTile();
            }
        }

        Detective sherlock = new Detective("Sherlock", 12);
        placeDetective(sherlock, 12);
        Detective tobby = new Detective("Tobby", 8);
        placeDetective(tobby, 8);
        Detective watson = new Detective("Watson", 4);
        placeDetective(watson, 4);
        this.detectives = new Detective[]{sherlock, tobby, watson};

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                this.board[i][j] = districts.remove(0);
                if (i == 1 && j == 1) {
                    this.board[i][j].getDistrict().setOrientation('E');
                }
                if (i == 1 && j == 3) {
                    this.board[i][j].getDistrict().setOrientation('W');
                }
                if (i == 3 && j == 2) {
                    this.board[i][j].getDistrict().setOrientation('N');

                }
            }
        }
    }

    private ArrayList<Alibi> getVisible() {
        ArrayList<Alibi> res = new ArrayList<Alibi>();
        for (Detective d : this.detectives) {
            if (d.getColumn() == 0) {
                for (int i = 1; i < 4; i++) {
                    District p = this.board[d.getRow()][i].getDistrict();
                    if (p.getOrientation() == 'E') {
                        break;
                    } else {
                        res.add(this.board[d.getRow()][i].getDistrict().getAlibi());
                        if (p.getOrientation() == 'W') {
                            break;
                        }
                    }
                }
            } else if (d.getColumn() == 4) {
                for (int j = 3; j > 0; j--) {
                    District p = this.board[d.getRow()][j].getDistrict();
                    if (p.getOrientation() == 'W') {
                        break;
                    } else {
                        res.add(this.board[d.getRow()][j].getDistrict().getAlibi());
                        if (p.getOrientation() == 'E') {
                            break;
                        }
                    }
                }
            } else if (d.getRow() == 4) {
                for (int i = 3; i > 0; i--) {
                    District p = this.board[i][d.getColumn()].getDistrict();
                    if (p.getOrientation() == 'N') {
                        break;
                    } else {
                        res.add(this.board[i][d.getColumn()].getDistrict().getAlibi());
                        if (p.getOrientation() == 'S') {
                            break;
                        }
                    }
                }
            } else if (d.getRow() == 0) {
                for (int j = 1; j < 4; j++) {
                    District p = (District) this.board[j][d.getColumn()];
                    if (p.getOrientation() == 'S') {
                        break;
                    } else {
                        res.add(this.board[j][d.getColumn()].getDistrict().getAlibi());
                        if (p.getOrientation() == 'N') {
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }


    private List<District> createDistrict() {
        List<District> districts = new ArrayList<>();
        String[] names = {"Inspecteur Lestrade", "Miss Stealthy", "Jeremy Bert", "John Pizer", "Joseph Lane", "Madame", "Sergent Goodley", "William Gull", "John Smith"};
        Random rand = new Random();
        int randomNum = rand.nextInt(names.length);
        for (int i = 0; i < names.length; i++) {
            districts.add(new District(new Alibi(names[i], 0)));
            if (i == randomNum) {
                districts.get(i).getAlibi().setMrJack();
            }
        }
        Collections.shuffle(districts);
        return districts;
    }


    private void printBoard() {
        System.out.println("Tableau de jeu actuel :");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String carac = board[i][j].toString();
                int n = (22 - carac.length()) / 2;
                StringBuilder espace = new StringBuilder();
                espace.append(" ".repeat(Math.max(0, n)));
                System.out.print(espace + carac + espace);
                if ((22 - carac.length()) % 2 == 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n\n");
        }
    }


    private void displayVisibleAlibis() {
        System.out.println("Voici les alibis visibles par l'enqueteur :");
        for (Alibi alibi : this.getVisible()) {
            System.out.println("- " + alibi.toString() + "\n");
        }
    }

    private String choix(List<String> jetons, Player currentPlayer, List<District> districtsAlibi) {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        String c = jetons.get(choice);

        Detective sherlock = detectives[0];
        Detective watson = detectives[2];
        Detective tobby = detectives[1];
        switch (c) {
            case "Sherlock":
                System.out.println("De combien de case voulez vous bouger Sherlock ? 1 ou 2 ?");
                int input = scanner.nextInt();
                this.board[sherlock.getColumn()][sherlock.getRow()] = new EmptyTile();
                this.move(sherlock, input);
                break;

            case "Tobby":
                System.out.println("De combien de case voulez vous bouger Tobby ? 1 ou 2 ?");
                input = scanner.nextInt();
                this.board[tobby.getColumn()][tobby.getRow()] = new EmptyTile();
                this.move(tobby, input);
                break;

            case "Watson":
                System.out.println("De combien de case voulez vous bouger Watson ? 1 ou 2 ?");
                input = scanner.nextInt();
                this.board[watson.getColumn()][watson.getRow()] = new EmptyTile();
                this.move(watson, input);
                break;

            case "Echanger district":
                int i1, i2, y1, y2;
                System.out.println("Entrez l'abscisse du 1 er district à échanger :\n-->");
                i1 = scanner.nextInt();
                System.out.println("Entrez l'ordonnée du 1 er district à échanger : \n-->");
                y1 = scanner.nextInt();
                System.out.println("Entrez l'abscisse du 2 ème district à échanger :\n-->");
                i2 = scanner.nextInt();
                System.out.println("Entrez l'ordonnée du 2 ème district à échanger : \n-->");
                y2 = scanner.nextInt();
                System.out.println("Echange de " + board[i1][y1].toString() + " avec " + board[i2][y2].toString());
                Tile tmp = null;
                tmp = this.board[i1][y1];
                this.board[i1][y1] = this.board[i2][y2];
                this.board[i2][y2] = tmp;
                break;

            case "Tourner  district 1":
            case "Tourner  district 2":
                System.out.println("Quel district voulez vous tourner ?");
                System.out.println("Entrez la colonne du district à tourner :\n-->");
                int y = scanner.nextInt();
                System.out.println("Entrez la ligne du district à tourner : \n-->");
                int i = scanner.nextInt();
                System.out.println("Souhaitez vous tourner le district 1 fois ou 2 fois ? (entrez 1 ou 2) \n-->");
                int step = scanner.nextInt();
                System.out.println("Rotation de " + this.board[i][y].getDistrict().toString());

                this.board[i][y].getDistrict().rotate(step);
                break;

            case "Avancer un des détectives":
                String ins;
                System.out.println("Quel joueur voulez vous avancer de 1 case ?");
                ins = scanner.nextLine();
                System.out.println(ins);
                if (ins.equals("Sherlock")) {
                    this.move(sherlock, 1);
                }
                if (ins.equals("Tobby")) {
                    this.move(tobby, 1);
                }
                if (ins.equals("Watson")) {
                    this.move(watson, 1);
                } else {
                    System.out.println("erreur");
                }
                break;


            case "Tirer carte alibi":
                Random rand = new Random();
                Alibi pickedAlibi;
                Alibi al;
                do {
                    int randomNum = rand.nextInt(districtsAlibi.size());
                    al = districtsAlibi.get(randomNum).getAlibi();
                    District d = districtsAlibi.get(randomNum);
                    if (al.getMrJack()) {
                        continue;
                    }
                    if (currentPlayer.getRole().equals("Enqueteur")) {
                        districtsAlibi.remove(randomNum);
                        currentPlayer.addSablier(al.getHourglassCount());
                        System.out.println("Alibi pioché : " + al.toString());
                        d.flip();
                        break;
                    } else if (currentPlayer.getRole().equals("MrJack")) {
                        pickedAlibi = districtsAlibi.get(randomNum).getAlibi();
                        districtsAlibi.remove(randomNum);
                        System.out.println("Alibi pioché : " + al.toString());
                        currentPlayer.addSablier(pickedAlibi.getHourglassCount());
                        d.flip();
                    } else {
                        System.out.println("erreur");
                    }
                } while (al.getMrJack());
                break;
        }
        this.visibleAlibis = this.getVisible();
        printBoard();

        return c;
    }

    private void move(Detective d, int step) {
        int pos = d.getPos() + step;
        if (pos == 13) {
            pos = 1;
        }
        if (pos == 14) {
            pos = 2;
        }
        //this.board[d.getRow()][d.getColumn()] = new EmptyTile();
        // On vérifie que le détective qu'on souhaite bouger ne possède pas un autre détective comme attribut
        for (Detective otherDetective : this.detectives) {
            if (otherDetective.getPos() == pos && d != otherDetective) {
                otherDetective.setDeuxiemeDetective(d);
                this.board[d.getRow()][d.getColumn()] = new EmptyTile();
                d.setPos(pos);
                this.placeDetective(otherDetective, pos);
            }

            else if (d.getDeuxiemeDetective() != null) {
                d.setPos(pos);
                this.placeDetective(d, pos);
                this.placeDetective(d.getDeuxiemeDetective(), d.getDeuxiemeDetective().getPos());
                d.setDeuxiemeDetective(null);
            }

            else if(otherDetective.getDeuxiemeDetective() == d){
                d.setPos(pos);
                this.placeDetective(d, pos);
                otherDetective.setDeuxiemeDetective(null);
                this.placeDetective(otherDetective,otherDetective.getPos());
            }

            else {
                this.board[d.getRow()][d.getColumn()] = new EmptyTile();
                d.setPos(pos);
                this.placeDetective(d, pos);
            }
        }
    }


    private void placeDetective(Detective d, int n) {
        if (n <= 3) {
            d.setPos(n);
            this.board[0][n] = d;
        } else if (n <= 6) {
            d.setPos(n);
            this.board[n - 3][4] = d;
        } else if (n <= 9) {
            d.setPos(n);
            this.board[4][10 - n] = d;
        } else if (n <= 12) {
            d.setPos(n);
            this.board[13 - n][0] = d;
        }
    }
}
