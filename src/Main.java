import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CopyOnWriteArrayList<Gun> guns = new CopyOnWriteArrayList<>();
        guns.add(new Gun(1));
        guns.add(new Gun(2));
        int time = getUnitOfTime();
        Shooter shooter = new Shooter(time, guns);
        Charger charger = new Charger(time, guns);
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