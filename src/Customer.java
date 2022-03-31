import java.time.LocalTime;
import java.util.Date;

public class Customer implements Runnable {

    private int customerId;
    private BarberShop barberShop;

    @Override
    public void run() {
        getHairCut();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BarberShop getBarberShop() {
        return barberShop;
    }

    public void setBarberShop(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    private synchronized void getHairCut() {
        barberShop.addNewCustomer(this);
    }
}
