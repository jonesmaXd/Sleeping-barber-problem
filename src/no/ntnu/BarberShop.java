package no.ntnu;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a barbershop in the sleeping barber problem. The sleeping barber problem
 * is a classic thread synchronisation problem. It is as follows;
 *
 * In a barber shop there are barbers, barber chairs which the barbers sleep in,
 * and chair where customers wait. If there is no customers, the barbers will sleep.
 * When a customer arrives, he has to wake up the barber.
 * When the barber is cutting the hair of a customer, the customer sleeps.
 * If there are no available barbers when a customer arrives,
 * the customer sits down in a chair to wait.
 * If there are no available chairs to wait in, the customer leaves.
 */
public class BarberShop {

    private Boolean shopOpen;
    private AtomicInteger customersCut = new AtomicInteger(0);
    private AtomicInteger customersLost = new AtomicInteger(0);
    private int availableBarbers;
    private int numberOfChairs;
    private LinkedList<Customer> waitingList;

    public BarberShop(Boolean shopOpen,
                      int numberOfChairs,
                      int numberOfBarbers) {
        this.shopOpen = shopOpen;
        if (numberOfBarbers < 0) {
            System.out.println("numberOfCustomers cannot be less than 0");
        } else {
            this.availableBarbers = numberOfBarbers;
        }
        if (numberOfChairs < 0) {
            System.out.println("numberOfChairs cannot be less than 0");
        } else {
            this.numberOfChairs = numberOfChairs;
        }
        this.waitingList = new LinkedList<>();
    }

    /**
     * Returns true if the shop is open, false if the shop is closed.
     *
     * @return the shop's open/closed status.
     */
    public Boolean getShopOpen() {
        return shopOpen;
    }

    /**
     * Simulates closing the barber shop.
     */
    public void closeShop() {
        shopOpen = false;
    }

    /**
     * Simulates opening the barber shop.
     */
    public void openShop() {
        shopOpen = true;
    }

    /**
     * Returns the amount of customers which have received a haircut.
     * @return the amount of customers.
     */
    public AtomicInteger getCustomersCut() {
        return customersCut;
    }

    /**
     * Returns the amount of customers which have left the barber shop
     * due to there not being enough chairs to wait in.
     * @return the amount of customers.
     */
    public AtomicInteger getCustomersLost() {
        return customersLost;
    }

    /**
     * Simulates a barber in the barber shop. If there are no customers,
     * the barber will go to sleep and wait.
     * When a customer arrives and wakes up the barber, the barber
     * will cut the customer's hair. While the barber is busy
     * cutting hair the barber will not do any other tasks.
     *
     * @param barberId a created barber's id to
     */
    public void cutHair(int barberId) {

        int millisDelay = 0;
        Customer customer;

        synchronized (waitingList) {

            //If there are no customers, go to sleep
            while (waitingList.size() == 0) {

                System.out.println("\nBarber " + barberId +
                        " goes to sleep, waiting for next customer");

                //Sleep until a customer arrives
                try {
                    waitingList.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            //Gets the first customer in the waiting list and removes it
            customer = waitingList.pollFirst();

            System.out.println("\nCustomer " + customer.getCustomerId() +
                    " wakes up the barber and proceeds to get a haircut");
        }

        try {

            //Barber is unavailable as it cuts the hair of a customer
            availableBarbers--;

            System.out.println("\nBarber " + barberId +
                    " is cutting the hair of customer " +
                    customer.getCustomerId() + " so the customer sleeps ");

            //A random millisecond number between 4000 and 500
            millisDelay = utility.Utility.getRandomNumberInRange(4000, 500);
            Thread.sleep(millisDelay);

            System.out.println("\nCustomer " + customer.getCustomerId() +
                    " has received a haircut by barber " + barberId
                    + " in " + millisDelay + " milliseconds, and leaves the shop");

            customersCut.incrementAndGet();

            //Checks if new customers have entered the shop
            if (waitingList.size() > 0) {
                System.out.println("\nBarber " + barberId +
                        " wakes up a waiting customer in line");
            }

            //Since the barber is done cutting hair it becomes available again
            availableBarbers++;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 'Creates' a new customer to the barbershop.
     * The customer will go through three alternatives;
     * 1. If the barber is available - Sleeping, the barber will cut the customer's hair
     * 2. If the barber is busy cutting some other customer's hair, the customer will sit down and sleep.
     * 3. However, if there is no empty chair to sit in, the customer will leave.
     * This method is synchronized so that multiple threads don't access it at the same time,
     * because it could lead to problems when a customer is added to the waiting list.
     *
     * @param customer the customer to be added
     */
    public void addNewCustomer(Customer customer) {
        System.out.println("\nCustomer " + customer.getCustomerId()
                + " entered the barber shop at " + utility.Utility.getCurrentTime());

        synchronized (waitingList) {

            //Check if there are any available chairs
            if (waitingList.size() == numberOfChairs && availableBarbers == 0) {
                System.out.println("\nNo chair is available, customer " + customer.getCustomerId() +
                        " leaves the barber shop");
                customersLost.incrementAndGet();
                return;

                //Check if there are any available barbers
            } else if (availableBarbers > 0) {
                waitingList.addLast(customer);
                waitingList.notify();

            } else {
                waitingList.addLast(customer);
                System.out.println("\nThere are no available barbers, customer " + customer.getCustomerId() +
                        " sits down on an empty chair");
                if (waitingList.size() == 1) {
                    waitingList.notify();
                }
            }
        }
    }
}
