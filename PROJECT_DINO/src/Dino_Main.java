public class Dino_Main {

    public static void main(String[] args) {
        boolean waitingRegistr = true;
        DinoWindowGame dwg = new DinoWindowGame();
        MenuFrame mf = new MenuFrame();
        while(waitingRegistr){
            System.out.println("WAITING");
            if (mf.getMv().isDone()){
                dwg.viewStart();
                dwg.setVisible(true);
                waitingRegistr = false;
            }
        }
    }
}
