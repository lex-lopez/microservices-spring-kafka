package com.demo.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int amountAvailable;
    private int amountReserved;

    public Customer() {
    }

    public Customer(Long id, String name, int amountAvailable, int amountReserved) {
        this.id = id;
        this.name = name;
        this.amountAvailable = amountAvailable;
        this.amountReserved = amountReserved;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public int getAmountReserved() {
        return amountReserved;
    }

    public void setAmountReserved(int amountReserved) {
        this.amountReserved = amountReserved;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountAvailable=" + amountAvailable +
                ", amountReserved=" + amountReserved +
                '}';
    }
}
