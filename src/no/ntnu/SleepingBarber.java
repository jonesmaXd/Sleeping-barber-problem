package no.ntnu;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SleepingBarber {

    public void runSimulation() throws InterruptedException {
        int numberOfCustomers;
        int numberOfChairs;
        int numberOfBarbers;

        numberOfBarbers = getUserIntResponse("barber");
        numberOfChairs = getUserIntResponse("chair");
        numberOfCustomers = getUserIntResponse("customer");
        BarberShop barberShop = new BarberShop(true, numberOfChairs, numberOfBarbers, numberOfCustomers);


        if(barberShop.getShopOpen()) {
            //Tracks the start time of the simulation
            long startTime = System.currentTimeMillis();

            System.out.println("\nThe barbershop is now open, " +
                    "it currently has " + numberOfBarbers +
                    " available barber(s)" +
                    " and " + numberOfChairs + " chairs for customers");

            ExecutorService executorService = Executors.newFixedThreadPool(50);

            //Initialises all the barber threads
            for (int i=0; i<numberOfBarbers; i++) {
                Barber barber = new Barber(barberShop, i);
                Thread threadBarber = new Thread(barber);
                executorService.execute(threadBarber);
            }

            //Initialises all the customer threads
            for (int i=0; i<numberOfCustomers; i++) {
                Customer customer = new Customer(barberShop);
                Thread threadCustomer = new Thread(customer);
                customer.setCustomerId(i);
                executorService.execute(threadCustomer);


                try {
                    Thread.sleep(utility.Utility.getRandomNumberInRange(4000, 500));
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

            }
            executorService.shutdown();
            executorService.awaitTermination(3, SECONDS);
            barberShop.closeShop();

            double elapsedTime = (System.currentTimeMillis() - startTime)*0.001;
            if(!barberShop.getShopOpen()){
                System.out.println("\nBarbershop closes for the day\n" +
                        "Total time used cutting " + numberOfCustomers + " customers by " +
                        numberOfBarbers + " barbers with " + numberOfChairs +
                        " chairs is: " + elapsedTime + " seconds");
                System.out.println("In total there were:\n" +
                        numberOfCustomers + " customers entered the shop\n" +
                        barberShop.getCustomersLost() + " customers left the shop\n" +
                        barberShop.getCustomersCut() + " customers which received a haircut");

            }

        }

    }


    /**
     *
     * @param expectedResponseVariable
     * @return
     */
    private int getUserIntResponse(String expectedResponseVariable) {
        Scanner sc = new Scanner(System.in);
        int userinput = 1;

        try {
            System.out.println("Please enter the number of " + expectedResponseVariable + "(s) in the shop:");
            userinput = sc.nextInt();
            while (userinput < 0) {
                System.out.println("The amount of " + expectedResponseVariable +"(s) have to be a positive number \n" +
                        "Please try again");
                userinput = sc.nextInt();
            }
        } catch (InputMismatchException e) {
            int randomNumber =  utility.Utility.getRandomNumberInRange(50, 1);
            System.out.println("Invalid value detected, using a randomly generated number for " +
                    expectedResponseVariable + "(s): " + randomNumber);
            userinput = randomNumber;
        }
        return userinput;
    }

    public static void main(String[] args) {
        SleepingBarber sleepingBarber = new SleepingBarber();
        try {
            sleepingBarber.runSimulation();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
