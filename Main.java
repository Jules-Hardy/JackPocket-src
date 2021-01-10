import java.util.*;

public class Main {
    static Deque<Alibi> alibis;
    public static void main(String[] args){
        Game game = new Game();
        Player winner = game.play();
    }

    private static void initialiseAlibis(){
        List<Alibi> tmp = new ArrayList<>();
        tmp.add(new Alibi("Inspecteur Lestrade",0));
        tmp.add(new Alibi("Miss Stealthy",1));
        tmp.add(new Alibi("Jeremy Bert",1));
        tmp.add(new Alibi("John Pizer",1));
        tmp.add(new Alibi("John Smith",1));
        tmp.add(new Alibi("Joseph Lane",1));
        tmp.add(new Alibi("Madame",2));
        tmp.add(new Alibi("Sgt Goodley",0));
        tmp.add(new Alibi("William Gull",1));
        Collections.shuffle(tmp);

        if (alibis==null){
            alibis=new ArrayDeque<>(tmp);
        }else{
            alibis.clear();
            alibis.addAll(tmp);
        }
    }

    private static Alibi drawAlibi(){
        if(alibis.size()>0) return alibis.pop();
        return null;
    }

}