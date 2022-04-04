package no.ntnu;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * The main class of the sleeping barber problem. Its main purpose is to simulate the problem.
 */
public class SleepingBarber {

    /**
     * Simulates the sleeping barber problem.
     *
     * @throws InterruptedException if executor service encounters a problem shutting down
     */
    public void runSimulation() throws InterruptedException {

        int numberOfBarbers = getUserIntResponse("barber");
        int numberOfChairs = getUserIntResponse("chair");
        int numberOfCustomers = getUserIntResponse("customer");
        BarberShop barberShop = new BarberShop(true, numberOfChairs, numberOfBarbers);

        if(barberShop.getShopOpen()) {
            //Tracks the start time of the simulation
            long startTime = System.currentTimeMillis();

            System.out.println("\nThe barbershop is now open, " +
                    "it currently has " + numberOfBarbers +
                    " available barber(s) and " + numberOfChairs +
                    " chairs for customers");

            ExecutorService executorService = Executors.newFixedThreadPool(numberOfCustomers);

            initializeBarbers(numberOfBarbers, executorService, barberShop);
            initializeCustomers(numberOfCustomers, executorService, barberShop);

            //Shuts down the executor service
            executorService.shutdown();

            //Waits 5 seconds for all the threads to finish
            executorService.awaitTermination(5, SECONDS);
            barberShop.closeShop();

            double elapsedTime = (System.currentTimeMillis() - startTime)*0.001;
            if(!barberShop.getShopOpen()){
                System.out.println("\nBarbershop closes for the day\n" +
                        "Total time used cutting " + numberOfCustomers + " customers by " +
                        numberOfBarbers + " barber(s) with " + numberOfChairs +
                        " chair(s) is: " + elapsedTime + " seconds");
                System.out.println("In total there were:\n" +
                        numberOfCustomers + " customers entered the shop\n" +
                        barberShop.getCustomersLost() + " customers left the shop\n" +
                        barberShop.getCustomersCut() + " customers which received a haircut");
            }
        }
    }

    /**
     *
     * @param numberOfBarbers
     * @param executorService
     * @param barberShop
     */
    public void initializeBarbers (int numberOfBarbers, ExecutorService executorService, BarberShop barberShop) {
        for (int i=0; i<numberOfBarbers; i++) {
            Barber barber = new Barber(barberShop, i);
            Thread threadBarber = new Thread(barber);
            executorService.execute(threadBarber);
        }
    }

    /**
     *
     * @param numberOfCustomers
     * @param executorService
     * @param barberShop
     */
    private void initializeCustomers (int numberOfCustomers, ExecutorService executorService, BarberShop barberShop) {
        for (int i=0; i<numberOfCustomers; i++) {
            Customer customer = new Customer(barberShop);
            Thread threadCustomer = new Thread(customer);
            customer.setCustomerId(i);
            executorService.execute(threadCustomer);

            try {
                //Makes each customer thread sleep a number of milliseconds between 4000 and 500
                //This is to simulate the delay between each customer entering the barber shop
                Thread.sleep(utility.Utility.getRandomNumberInRange(4000, 500));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }



    /**
     * A custom method which asks for and reads user input to get the total number of
     * an item in the barber shop.
     *
     * @param itemToRequestNumberOf the item to request amount of.
     * @return the total number of the item.
     */
    private int getUserIntResponse(String itemToRequestNumberOf) {
        Scanner sc = new Scanner(System.in);
        int userinput = 1;

        try {
            System.out.println("Please enter the number of " + itemToRequestNumberOf + "(s) in the shop:");
            userinput = sc.nextInt();
            while (userinput < 0) {
                System.out.println("The amount of " + itemToRequestNumberOf +"(s) have to be a positive number \n" +
                        "Please try again");
                userinput = sc.nextInt();
            }
        } catch (InputMismatchException e) {
            int randomNumber =  utility.Utility.getRandomNumberInRange(50, 1);
            System.out.println("Invalid value detected, using a randomly generated number for " +
                    itemToRequestNumberOf + "(s): " + randomNumber);
            userinput = randomNumber;
        }
        return userinput;
    }

    /**
     * Starts the application and runs the simulation.
     */
    public static void main(String[] args) {
        SleepingBarber sleepingBarber = new SleepingBarber();
        try {
            sleepingBarber.runSimulation();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
