package no.ntnu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberShop {

    private Boolean shopOpen;
    private AtomicInteger customersCut = new AtomicInteger(0);
    private AtomicInteger customersLost= new AtomicInteger(0);
    private int numberOfBarbers;
    private int numberOfCustomers;
    private int availableBarbers;
    private int numberOfChairs;
    private LinkedList<Customer> waitingList;

    public BarberShop(Boolean shopOpen,
                      int numberOfChairs,
                      int numberOfBarbers,
                      int numberOfCustomers) {
        this.shopOpen = shopOpen;
        this.availableBarbers = numberOfBarbers;
        this.numberOfChairs = numberOfChairs;
        this.numberOfCustomers = numberOfCustomers;
        this.waitingList = new LinkedList<>();
    }

    public Boolean getShopOpen() {
        return shopOpen;
    }

    public void closeShop() {
        shopOpen = false;
    }

    public void openShop() {
        shopOpen = true;
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


    /**
     *  Simulates cutting the hair of a customer
     */
    public void cutHair(int barberId) {

        int millisDelay=0;
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

            System.out.println("Customer " + customer.getCustomerId() +
                    " wakes up the barber and proceeds to get a haircut \n");
        }

                try {

                    //Barber is unavailable as it cuts the hair of a customer
                    availableBarbers--;

                    System.out.println("Barber " + barberId +
                            " is cutting the hair of customer " +
                            customer.getCustomerId() + " so the customer sleeps \n");

                    //A random millisecond number between 4000 and 500
                    millisDelay = utility.Utility.getRandomNumberInRange(4000, 500);
                    Thread.sleep(millisDelay);

                    System.out.println("Customer " + customer.getCustomerId() +
                            " has received a haircut by barber " + barberId
                              + " in " + millisDelay + " milliseconds, and leaves the shop \n");

                    customersCut.incrementAndGet();

                    //Checks if new customers have entered the shop
                    if (waitingList.size() > 0) {
                        System.out.println("Barber " + barberId +
                                " wakes up a waiting customer in line \n");
                    }

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
     * @param customer the customer to be added
     */
    public void addNewCustomer(Customer customer) {
        System.out.println("\nCustomer " + customer.getCustomerId()
                + " entered the barber shop at " + utility.Utility.getCurrentTime());

        synchronized (waitingList) {

            //Check if there are any available chairs
            if (waitingList.size() == numberOfChairs) {
                System.out.println("\n No chair is available, customer " + customer.getCustomerId() +
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
