import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    private static Shooter shooter;
    private static Charger charger;

    private static CopyOnWriteArrayList<Gun> guns;

    private final static PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

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
    public static void printMenu(){
        System.out.println("___________________COMMANDS______________________");
        System.out.println("Type 1 to restart shooting");
        System.out.println("Type 2 to exit");
        System.out.println("_________________________________________________");
    }

    public static void startNewShooting(){
        int time = getUnitOfTime();
        shooter = new Shooter(time, guns);
        charger = new Charger(time, guns);
        shooter.start();
        charger.start();
    }

    public static int getIntString() {
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
    public static int getUnitOfTime(){
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