import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner( System.in );
        int in = 1;
        do{
            Game game = new Game();
            Player winner = game.play();
            System.out.println("Bravo "+winner+" tu as gagné !\nVoulez vous refaire une partie ?\n 1 : oui, 2 : non\n-->");
            in = scanner.nextInt();
        }while(in==1);
    }
}