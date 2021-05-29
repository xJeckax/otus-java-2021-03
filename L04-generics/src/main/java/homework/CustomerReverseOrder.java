package homework;


import java.util.ArrayList;
import java.util.List;

public class CustomerReverseOrder {

    private final List<Customer> customerList = new ArrayList<>();

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public Customer take() {
        return customerList.remove(customerList.size() - 1);
    }
}
