package homework;


public class Customer implements Comparable<Customer> {

    private final long id;
    private String name;
    private long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    //Копирование обьекта
    public Customer(Customer other) {
        this(other.getId(), other.getName(), other.getScores());
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
        if (o == null || this.getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        /*if (this.id == customer.getId() && this.name.equals(customer.getName()) && this.scores == customer.getScores()) {
            return true;
        }*/
        return this.id == customer.getId();
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Customer o) {
        if (this.scores < o.getScores()) {
            return -1;
        }
        return 1;
    }
}
