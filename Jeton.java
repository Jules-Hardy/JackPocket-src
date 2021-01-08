import java.util.*;

public class Jeton {

    private String recto;
    private String verso;
    private int numero;

    public static void main(String[] args) {}

    public static List<String> tourJeton(){
        List<Jeton> listJeton = new ArrayList<>();
        Jeton jeton1 = new Jeton(1,"Holmes","Tirer carte alibi");
        Jeton jeton2 = new Jeton(2,"Tobby","Sherlock");
        Jeton jeton3 = new Jeton(3,"Tourner  district 1","Echanger disctrit");
        Jeton jeton4 = new Jeton(4,"Tourner  district 2","Avancer un des détectives");
        List aa = new ArrayList<>();
        aa = Jeton.tirerJetons(jeton1, jeton2,jeton3,jeton4);
        return aa;
    }

    public Jeton(int numero, String recto, String verso) {
        this.numero=numero;
        this.recto =recto;
        this.verso =verso;
    }


    private static List tirerJetons(Jeton jeton1, Jeton jeton2, Jeton jeton3, Jeton jeton4){
        List<String> facesFinal = new ArrayList<>();
        facesFinal.add(jeton1.tirerFace());
        facesFinal.add(jeton2.tirerFace());
        facesFinal.add(jeton3.tirerFace());
        facesFinal.add(jeton4.tirerFace());
        return facesFinal;
    }


    private String tirerFace(){
        Random random = new Random();
        int[] tmpList = new int[2];
        int finalValue = random.nextInt(tmpList.length);
        String output = null;
        if(finalValue == 0){
            output = getRecto();
        }
        else if(finalValue == 1) {
            output = getVerso();
        }
        else
            System.out.println("Erreur");
        return output;
    }

    public int getNumero() {
        return numero;
    }

    public String getRecto(){
        return recto;
    }

    public String getVerso() {
        return verso;
    }
}