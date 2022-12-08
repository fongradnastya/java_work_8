import java.util.concurrent.CopyOnWriteArrayList;

public class Charger extends Thread{
    private final int chargingTime;

    private CopyOnWriteArrayList<Gun> guns;

    Charger(int time, CopyOnWriteArrayList<Gun> guns){
        chargingTime = 15 * time;
        this.guns = guns;
    }

    @Override
    public void run() {
        super.run();
    }
}
