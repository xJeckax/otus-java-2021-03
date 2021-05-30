package homework;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CustomerService {


    private final Map<Customer, String> map;

    public CustomerService() {
        this.map = new TreeMap<>();
    }

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstItem = map.entrySet().iterator().next();
        return Map.entry(new Customer(firstItem.getKey()), firstItem.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (!customer.getName().equals("Not exists")) {
            Set set = map.entrySet();
            Iterator iterator = set.iterator();
            Map.Entry<Customer, String> maxId = getSmallest();
            while (iterator.hasNext()) {
                Map.Entry<Customer, String> next = (Map.Entry<Customer, String>) iterator.next();
                if (next.getKey().getScores() > customer.getScores()) {
                    return next;
                } else {
                    if (next.getKey().getId() > maxId.getKey().getId()) {
                        maxId = next;
                    }
                }
            }
            return maxId;
        }
        return null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    public static void main(String[] args) {

    }
}
