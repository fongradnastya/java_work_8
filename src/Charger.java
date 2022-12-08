import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Поток, реализующий заряжающего ружья для обороны крепости
 */
public class Charger extends Thread{
    /**
     * Время в милисекундах для перезарядки одного ружья
     */
    private final int chargingTime;

    /**
     * Флаг, обозначающий, нужно ли остановить оборону
     */
    private boolean isStopped;

    /**
     * Разделяемый ресурс, содержащий все доступные ружья
     */
    private final CopyOnWriteArrayList<Gun> guns;

    /**
     * Конструктор для добавления нового заряжающего
     * @param time размер единицы времени в милисекундах
     * @param guns список всех доступных ружий
     */
    Charger(int time, CopyOnWriteArrayList<Gun> guns){
        chargingTime = 15 * time;
        this.guns = guns;
        isStopped = false;
    }

    /**
     * Метод для запуска потока
     */
    @Override
    public void run() {
        while (!isStopped){
            PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
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

    /**
     * Метод получения первого незаряженного ружья
     * @return незаряженное ружьё или null
     */
    private synchronized Gun getUnchargedGun(){
        for(Gun gun: guns){
            if (! gun.checkIsCharging()){
                return gun;
            }
        }
        return null;
    }

    /**
     * Метод для завершения работы заряжающего
     */
    public void disable(){
        isStopped = true;
    }

}
