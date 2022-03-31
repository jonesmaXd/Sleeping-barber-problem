package no.ntnu;

/**
 *
 */
public class Barber implements Runnable {

    private int barberId;
    private BarberShop barberShop;

    public Barber(BarberShop barberShop, int barberId) {
        this.barberShop = barberShop;
        this.barberId = barberId;
    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            barberShop.cutHair(barberId);
        }
    }
}
