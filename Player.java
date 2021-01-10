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
        System.out.println("\t Bienvenue,\nAvant de commencer une partie, vueillez choisir quel personne vous voulez jouer...");
        List<Player> listJoueur = new ArrayList<>();
        Scanner scanner = new Scanner( System.in );
        System.out.println("Joueur1 est MrJack. Entrez votre pseudo --> ");
        Player mrJack = new Player(scanner.nextLine(), "MrJack", 0);
        System.out.println("Joueur2 est l'inspecteur. Entrez votre pseudo --> ");
        Player enqueteur = new Player(scanner.nextLine(), "Enqueteur", 0);
        listJoueur.add(mrJack);
        listJoueur.add(enqueteur);
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

    public void addSablier(int number){
        this.nbSablier = nbSablier + number;
    }

    public void setSablier(int nbSablier) {
        this.nbSablier = nbSablier;
    }

    public void setRole(String role){
        this.role = role;
    }
}
