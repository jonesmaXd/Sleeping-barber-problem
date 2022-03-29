import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberShop {

    private AtomicBoolean shopOpen;
    private AtomicInteger customersCut;
    private AtomicInteger customersLeft;
    private int numberOfBarbers;
    private int numberOfCustomers;
    private LinkedList<Customer> waitingList;


    public BarberShop(AtomicBoolean shopOpen, AtomicInteger customersCut, AtomicInteger customersLeft, int numberOfBarbers, int numberOfCustomers) {
        this.shopOpen = shopOpen;
        this.customersCut = customersCut;
        this.customersLeft = customersLeft;
        this.numberOfBarbers = numberOfBarbers;
        this.numberOfCustomers = numberOfCustomers;
        this.waitingList = new LinkedList<>();
    }

    

}
