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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone", schema = "public")
public class Phone implements Cloneable {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "phone_sequencer", schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_sequencer")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Phone(String number) {
        this.number = number;
    }

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public Phone clone() {
        return new Phone(this.id, this.number);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id= " + id +
                ", number = " + number +
                '}';
    }
}
