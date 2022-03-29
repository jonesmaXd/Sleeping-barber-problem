import java.time.LocalTime;

public class Barber implements Runnable {

    private int barberId;
    private BarberShop barberShop;

    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    @Override
    public void run() {

    }
}
