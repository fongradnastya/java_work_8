import java.util.concurrent.CopyOnWriteArrayList;

public class Shooter extends Thread {
    private final int shotTime;

    private CopyOnWriteArrayList<Gun> guns;

    Shooter(int time, CopyOnWriteArrayList<Gun> guns){
        shotTime = 5 * time;
        this.guns = guns;
    }

    @Override
    public void run() {
        super.run();
    }
}
