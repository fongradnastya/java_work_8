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
        while (true){
            int waitingTime = getEnemyWaitingTime();
            System.out.println("There are no enemies");
            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException ignored) {}
            System.out.println("Shooter noticed an enemy");
            Gun gun = getAvailableGun();
            if(gun != null){
                gun.shoot();
                guns.notify();
                try {
                    Thread.sleep(shotTime);
                } catch (InterruptedException ignored) {}
            }
            else{
                try {
                    guns.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        int min_time = 200;
        int max_time = 2000;
        return (int) (Math.random() * max_time + min_time + 1) - min_time;
    }

}
