package no.ntnu;

/**
 * A runnable object which represents a customer in a barbershop
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

    /**
     * Gets the customer's id.
     * @return the customer's id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * sets a new customer id.
     * @param customerId the new customer id.
     */
    public void setCustomerId(int customerId) {
        if (customerId < 0) {
            System.out.println("customerId cannot be less than 0");
        } else {
            this.customerId = customerId;
        }
    }

    /**
     * Simulates a customer going for a haircut.
     * This will add a new customer to the barbershop.
     */
    private void getHairCut() {
        barberShop.addNewCustomer(this);
    }
}
