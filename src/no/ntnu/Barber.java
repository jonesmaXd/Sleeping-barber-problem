package no.ntnu;

/**
 * A runnable object which represents a barber in a barbershop
 */
public class Barber implements Runnable {

    private int barberId;
    private BarberShop barberShop;

    public Barber(BarberShop barberShop, int barberId) {
        if (barberShop == null) {
            System.out.println("barberShop cannot be null");
        } else {
            this.barberShop = barberShop;
        }
        if (barberId < 0) {
            System.out.println("BarberId cannot be less than 0");
        } else {
            this.barberId = barberId;
        }
    }

    /**
     * Barber loops through the cutHair method infinitely. This is fine because the
     * thread will sleep if it has nothing to do, therefore, if you have many barbers
     * it will not take an extreme amount of resources.
     */
    @Override
    public void run() {
        while (true) {
            barberShop.cutCustomerHair(barberId);
        }
    }
}
