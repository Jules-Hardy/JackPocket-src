import java.util.*;

public class Main {
    static Deque<Alibi> alibis;
    public static void main(String[] args){
        Game game = new Game();
        Player winner = game.play();
    }
}