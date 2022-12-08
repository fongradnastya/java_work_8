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
        while (true){
            Gun gun = getUnchargedGun();
            if(gun == null){
                System.out.println("Charger is waiting for a gun");
                try {
                    guns.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                gun.charge();
                guns.notify();
                try {
                    Thread.sleep(chargingTime);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    private synchronized Gun getUnchargedGun(){
        for(Gun gun: guns){
            if (! gun.checkIsCharging()){
                return gun;
            }
        }
        return null;
    }
}
