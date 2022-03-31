package no.ntnu;

/**
 *
 */
public class Customer implements Runnable {

    private int customerId;
    private BarberShop barberShop;

    public Customer(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    /**
     *
     */
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

    private synchronized void getHairCut() {
        barberShop.addNewCustomer(this);
    }
}
