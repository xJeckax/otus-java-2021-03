package homework;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    private final long id;
    private String name;
    private long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name=" + name +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id == customer.getId()) return true;
        if (scores == customer.getScores()) return true;
        return name != null && name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public static void main(String[] args) {
        Customer customer = new Customer(1, "cffc", 233);
        Customer customer1 = new Customer(2, "Sdsc", 111);
        Set<Customer> customers = new HashSet<>();
        customers.add(customer);
        Customer customer2 = new Customer(customer1.getId(), customer1.getName(), customer1.getScores());

        System.out.println(customer1.equals(customer2));
        System.out.println(customer1 == customer2);



    }
}
