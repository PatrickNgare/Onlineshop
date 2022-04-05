package com.filing.demo.models;

import lombok.Data;

import java.util.Calendar;

@Data
public class Customer {

    private String name;
    private Calendar birthday;
    private int id;
    private int transactions;

}
