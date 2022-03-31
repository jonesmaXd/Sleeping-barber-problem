package no.ntnu;

/**
 * represents a customer in a barbershop
 */
public class Customer implements Runnable {

    private int customerId;
    private BarberShop barberShop;

    public Customer(BarberShop barberShop) {
        if (barberShop == null) {
            System.out.println("barberShop cannot be null");
        } else {
            this.barberShop = barberShop;
        }
    }

    /**
     * When a customer enters the barbershop, the customer's only
     * objective is to get a haircut.
     */
    @Override
    public void run() {
        getHairCut();
    }

    public int getCustomerId() {
        return customerId;
    }

    /**
     * sets a new customer id
     * @param customerId the new customer id
     */
    public void setCustomerId(int customerId) {
        if (customerId < 0) {
            System.out.println("customerId cannot be less than 0");
        } else {
            this.customerId = customerId;
        }
    }

    /**
     *
     */
    private synchronized void getHairCut() {
        barberShop.addNewCustomer(this);
    }
}
