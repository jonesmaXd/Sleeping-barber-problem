import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberShop {

    private AtomicBoolean shopOpen;
    private AtomicInteger customersCut;
    private AtomicInteger customersLost;
    private int numberOfBarbers;
    private int numberOfCustomers;
    private int availableBarbers;
    private int numberOfChairs;
    private Date currentTime;
    private LinkedList<Customer> waitingList;


    public BarberShop(AtomicBoolean shopOpen,
                      AtomicInteger customersCut,
                      AtomicInteger customersLost,
                      int numberOfChairs,
                      int numberOfBarbers,
                      int availableBarbers,
                      int numberOfCustomers,
                      Date currentTime) {
        this.shopOpen = shopOpen;
        this.customersCut = customersCut;
        this.customersLost = customersLost;
        this.availableBarbers = availableBarbers;
        this.numberOfBarbers = numberOfBarbers;
        this.numberOfChairs = numberOfChairs;
        this.numberOfCustomers = numberOfCustomers;
        this.currentTime = currentTime;
        this.waitingList = new LinkedList<>();
    }


    private void closeShop() {
        shopOpen.set(false);
    }

    private void openShop() {
        shopOpen.set(true);
    }

    public AtomicInteger getCustomersCut() {
        return customersCut;
    }

    public void setCustomersCut(AtomicInteger customersCut) {
        this.customersCut = customersCut;
    }

    public AtomicInteger getCustomersLost() {
        return customersLost;
    }

    public void setCustomersLost(AtomicInteger customersLost) {
        this.customersLost = customersLost;
    }

    public String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(currentTime);
    }

    /**
     * 'Creates' a new customer to the barbershop.
     * The customer will go through three alternatives;
     * 1. If the barber is available - Sleeping, the barber will cut the customer's hair
     * 2. If the barber is busy cutting some other customer's hair, the customer will sit down and sleep.
     * 3. However, if there is no empty chair to sit in, the customer will leave.
     * @param customer
     */
    private void addNewCustomer(Customer customer) {
        System.out.println("\n Customer " + customer.getCustomerId()
                + " entered the barber shop" + getCurrentTime());

        synchronized (waitingList) {

            //Check if there are any available chairs
            if (waitingList.size() == numberOfChairs) {
                System.out.println("\n No chair is available " + customer.getCustomerId() +
                        " Leaves the barber shop");
                customersLost.incrementAndGet();
                return;

                //Check if there are any available barbers
            } else if (availableBarbers > 0) {
                waitingList.addLast(customer);
                waitingList.notify();

            } else {

                System.out.println("There are no available barbers, " + customer.getCustomerId() +
                        " Sits down on an empty chair");
                    waitingList.addLast(customer);

                    if (waitingList.size() == 1) {
                    waitingList.notify();
                }
            }
        }
    }

    private void cutHair() {

    }
}
