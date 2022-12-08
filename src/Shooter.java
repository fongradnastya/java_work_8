import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Поток, реализующий стрелка для обороны крепости
 */
public class Shooter extends Thread {
    /**
     * Время в милисукундах, затрачиваемое на один выстрел
     */
    private final int shotTime;

    /**
     * Флаг, обозначающий, нужно ли остановить выполнение стрельбы
     */
    private boolean isStopped;

    /**
     * Разделяемый ресурс, содержащий все доступные ружья
     */
    private final CopyOnWriteArrayList<Gun> guns;

    /**
     * Конструктор стрелка
     * @param time размер единицы времени в милисекундах
     * @param guns список всех доступных ружий
     */
    Shooter(int time, CopyOnWriteArrayList<Gun> guns){
        shotTime = 5 * time;
        this.guns = guns;
        this.isStopped = false;
    }

    /**
     * Метод для запуска исполнения потока
     */
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

    /**
     * Получение первого доступного для стрельбы ружья
     * @return заряженное ружьё, либо null
     */
    private synchronized Gun getAvailableGun(){
        for(Gun gun: guns){
            if (gun.checkIsCharging()){
                return gun;
            }
        }
        return null;
    }

    /**
     * Получение времени ожидания прихода следующего врага
     * @return время
     */
    private int getEnemyWaitingTime(){
        int min_time = 100;
        int max_time = 10000;
        return (int) (Math.random() * max_time + min_time + 1) - min_time;
    }

    /**
     * Завершение работы стрелка
     */
    public void disable(){
        isStopped = true;
    }

}
