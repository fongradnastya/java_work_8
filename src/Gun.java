public class Gun {
    private final int number;
    private boolean isCharging;

    Gun(int number){
        this.number = number;
        isCharging = true;
    }

    public void charge(){
        isCharging = true;
    }

    public void shoot(){
        isCharging = false;
    }

    public boolean checkIsCharging(){
        return isCharging;
    }

    @Override
    public String toString() {
        return "The gun number " + number;
    }
}
