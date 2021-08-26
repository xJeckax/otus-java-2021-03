package ru.otus.crm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client", schema = "public")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_sequencer", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequencer")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Phone> phoneList = new ArrayList<>();

    public Client(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public Client clone() {
        List<Phone> phoneList = new ArrayList<>();
        this.phoneList.forEach(phone -> {
            phoneList.add(phone.clone());
        });
        return new Client(this.id, this.name, this.address, phoneList);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address = " + address +
                ", phoneList = " + phoneList.toString() +
                '}';
    }
}
