public class Gun {
    private final int number;
    private boolean isCharging;

    Gun(int number){
        this.number = number;
        isCharging = true;
    }

    public void charge(){
        isCharging = true;
        System.out.printf("The gun %d is charging\n", number);
    }

    public void shoot(){
        System.out.printf("The gun %d has fired\n", number);
        isCharging = false;
    }

    public boolean checkIsCharging(){
        return isCharging;
    }
}
