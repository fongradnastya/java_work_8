import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class Shooter extends Thread {
    private final int shotTime;

    private boolean isStopped;

    private final CopyOnWriteArrayList<Gun> guns;

    Shooter(int time, CopyOnWriteArrayList<Gun> guns){
        shotTime = 5 * time;
        this.guns = guns;
        this.isStopped = false;
    }

    @Override
    public void run() {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        while (!isStopped){
            int waitingTime = getEnemyWaitingTime();
            Gun gun = getAvailableGun();
            if(gun != null){
                System.out.println(ANSI_GREEN + "There are no enemies" + ANSI_RESET);
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException ignored) {}
                out.println(ANSI_GREEN + "\uD83D\uDC40 Shooter noticed an enemy" + ANSI_RESET);
                synchronized (guns){
                    gun.shoot();
                    out.println(ANSI_GREEN + "\uD83D\uDCA5" + gun + " has fired" + ANSI_RESET);
                    guns.notify();
                }
                try {
                    Thread.sleep(shotTime);
                } catch (InterruptedException ignored) {}
            }
            else{
                synchronized (guns){
                    try {
                        guns.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.println("Shooter finished their work!");
    }

    private synchronized Gun getAvailableGun(){
        for(Gun gun: guns){
            if (gun.checkIsCharging()){
                return gun;
            }
        }
        return null;
    }

    private int getEnemyWaitingTime(){
        int min_time = 100;
        int max_time = 10000;
        return (int) (Math.random() * max_time + min_time + 1) - min_time;
    }

    public void disable(){
        isStopped = true;
    }

}
