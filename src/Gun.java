/**
 * Класс ружья
 */
public class Gun {
    /**
     * Порядковый номер ружья
     */
    private final int number;

    /**
     * Является ли ружьё заряженным
     */
    private boolean isCharging;

    /**
     * Создаёт новое ружьё с номером
     * @param number номер создаваемого ружья
     */
    Gun(int number){
        this.number = number;
        isCharging = true;
    }

    /**
     * Метод зарядки ружья
     */
    public void charge(){
        isCharging = true;
    }

    /**
     * Метод для совершения выстрела из ружья
     */
    public void shoot(){
        isCharging = false;
    }

    /**
     * Метод для проверки заряжено ли ружьё
     * @return является ли заряженным
     */
    public boolean checkIsCharging(){
        return isCharging;
    }

    /**
     * Возвращает строковое представление объекта
     * @return данные об оружии
     */
    @Override
    public String toString() {
        return "The gun number " + number;
    }
}
