import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class Charger extends Thread{
    private final int chargingTime;

    private boolean isStopped;
    private final CopyOnWriteArrayList<Gun> guns;

    private final static PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    Charger(int time, CopyOnWriteArrayList<Gun> guns){
        chargingTime = 15 * time;
        this.guns = guns;
        isStopped = false;
    }

    @Override
    public void run() {
        while (!isStopped){
            String ANSI_RESET = "\u001B[0m";
            String ANSI_RED = "\u001B[31m";
            Gun gun = getUnchargedGun();
            if(gun == null){
                System.out.println(ANSI_RED + "Charger is waiting for a gun" + ANSI_RESET);
                synchronized (guns){
                    try {
                        guns.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else{
                synchronized (guns){
                    gun.charge();
                    out.println(ANSI_RED + "\uD83D\uDD2B " + gun + " is charging" + ANSI_RESET);
                    guns.notify();
                }
                try {
                    Thread.sleep(chargingTime);
                } catch (InterruptedException ignored) {}
            }
        }
        System.out.println("Charger finished their work!");
    }

    private synchronized Gun getUnchargedGun(){
        for(Gun gun: guns){
            if (! gun.checkIsCharging()){
                return gun;
            }
        }
        return null;
    }

    public void disable(){
        isStopped = true;
    }

}
