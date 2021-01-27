import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner( System.in );
        int in = 1;
        List<Player> listJoueur = Player.definePlayer();
        do{
            Game game = new Game();
            Player winner = game.play(listJoueur);
            System.out.println("Bravo "+winner.getName()+" tu as gagné !\nVoulez vous refaire une partie ?\n 1 : oui, 2 : non\n-->");
            in = scanner.nextInt();
        }while(in==1);
    }
}