import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;


public class Player {

    private String name;
    private String role;
    private int nbSablier;

    public Player(String name, String role, int nbSablier) {
        this.name=name;
        this.role =role;
        this.nbSablier = nbSablier;
    }

    public static List<Player> definePlayer(){
        List<Player> listJoueur = new ArrayList<>();
        Scanner scanner = new Scanner( System.in );
        System.out.println("Entrez 1 pour que joueur 1 soit MrJack, ou 2 pour l'enquêteur --> ");
        int input = scanner.nextInt();
        if(input==1){
            System.out.println("Joueur1 est MrJack. Entrez votre pseudo --> ");
            scanner.nextLine();
            Player joueur1 = new Player(scanner.nextLine(), "MrJack", 0);
            System.out.println("Joueur2 est l'inspecteur. Entrez votre pseudo --> ");
            Player joueur2 = new Player(scanner.nextLine(), "Enqueteur", 0);
            listJoueur.add(joueur1);
            listJoueur.add(joueur2);
        }
        else {
            System.out.println("Joueur1 est l'enquêteur.\nEntrez votre pseudo --> ");
            scanner.nextLine();
            Player joueur1 = new Player(scanner.nextLine(), "Enqueteur", 0);
            System.out.println("Joueur2 est MrJack.\nEntrez votre pseudo --> ");
            Player joueur2 = new Player(scanner.nextLine(), "MrJack", 0);
            listJoueur.add(joueur1);
            listJoueur.add(joueur2);
        }
        return listJoueur;
    }


    public String getName(){
        return name;
    }

    public String getRole(){
        return role;
    }

    public int getNbSablier(){
        return nbSablier;
    }

    public void setSablier(int nbSablier) {
        this.nbSablier = nbSablier;
    }

    public void setRole(String role){
        this.role = role;
    }
}
