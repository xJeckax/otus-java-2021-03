package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address", schema = "public")
public class Address {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "address_sequencer", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequencer")
    private Long id;

    @Column(name = "street")
    private String street;


    @OneToOne(mappedBy = "address")
    private Client client;

    public Address(String street) {
        this.street = street;
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address clone() {
        return new Address(this.id, this.street);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street= " + street +
                '}';
    }
}
