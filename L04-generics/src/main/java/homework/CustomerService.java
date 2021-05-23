package homework;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CustomerService {

    Map<Customer, String> map = new HashMap<>();
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        Map.Entry<Customer, String> minScope = null;
        while (iterator.hasNext()) {
            Map.Entry<Customer, String> o1 = (Map.Entry<Customer, String>) iterator.next();
            if (minScope != null) {
                if (minScope.getKey().getScores() > o1.getKey().getScores()) {
                    minScope = o1;
                }
            } else {
                minScope = o1;
            }
        }
        return Map.entry(new Customer(minScope.getKey()), minScope.getValue());
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
}
