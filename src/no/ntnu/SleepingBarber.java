package no.ntnu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 *
 */
public class SleepingBarber {

    public void runSimulation() {
        int numberOfCustomers;
        int numberOfChairs;
        int numberOfBarbers;

        numberOfBarbers = getUserIntResponse("barber");
        numberOfChairs = getUserIntResponse("chair");
        numberOfCustomers = getUserIntResponse("customer");

        ExecutorService executorService = Executors.newFixedThreadPool(16);

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
        sleepingBarber.runSimulation();
    }
}
