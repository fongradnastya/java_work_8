import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс для запуска обороны крепости
 */
public class Main {
    /**
     * Экземпляр потока стреляющего из ружий
     */
    private static Shooter shooter;

    /**
     * Экземпляр потока заряжающего ружья
     */
    private static Charger charger;

    /**
     * Потокобезопасный список ружий
     */
    private static CopyOnWriteArrayList<Gun> guns;

    /**
     * Точка входа в приложение
     * @param args аргуменнты коммандной строки
     */
    public static void main(String[] args) {
        guns = new CopyOnWriteArrayList<>();
        guns.add(new Gun(1));
        guns.add(new Gun(2));
        startNewShooting();
        printMenu();
        boolean end = false;
        while(! end){
            int command = getIntString();
            switch (command){
                case 1 -> {
                    shooter.disable();
                    charger.disable();
                    while (shooter.isAlive() || charger.isAlive()){}
                    startNewShooting();
                }
                case 2 -> {
                    shooter.disable();
                    charger.disable();
                    end = true;
                }
            }
        }
    }

    /**
     * Вывод в консоль списка доступных комманд
     */
    public static void printMenu(){
        System.out.println("___________________COMMANDS______________________");
        System.out.println("Type 1 to restart shooting");
        System.out.println("Type 2 to exit");
        System.out.println("_________________________________________________");
    }

    /**
     * Метод для запуска стрельбы в нескольких потоках
     */
    public static void startNewShooting(){
        int time = getUnitOfTime();
        shooter = new Shooter(time, guns);
        charger = new Charger(time, guns);
        shooter.start();
        charger.start();
    }

    /**
     * Считывание строки с целым числом
     * @return считанное целое число
     */
    public static int getIntString() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        while (number == 0)
        {
            if(scanner.hasNext()){
                String line = scanner.nextLine();
                try
                {
                    number = Integer.parseInt(line);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Wrong number format!");
                    return 0;
                }
            }
        }
        return number;
    }

    /**
     * Получение от пользователя длины единицы времени
     * @return считанное значение в миллисекундых
     */
    public static int getUnitOfTime(){
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.println("️⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐");
        System.out.println("New shooting");
        out.println("️⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐⭐");
        System.out.print("Please, enter the length of a unit time interval: ");
        int unitOfTime = 0;
        while(unitOfTime < 1){
            unitOfTime = getIntString();
            if(unitOfTime < 1){
                System.out.println("This value should be positive!");
            }
        }
        return unitOfTime;
    }
}