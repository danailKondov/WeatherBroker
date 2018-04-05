package com.bellintegrator.weatherbroker.model;

import javax.persistence.*;

@Entity
@Table(name = "Example")
public class Example {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "some_int")
    private Integer someInt;

    @Column(name = "some_name")
    private String someName;

    public Example() {
    }

    public Example(Integer someInt, String someName) {
        this.someInt = someInt;
        this.someName = someName;
    }

    public Long getId() {
        return id;
    }

    public Integer getSomeInt() {
        return someInt;
    }

    public void setSomeInt(Integer someInt) {
        this.someInt = someInt;
    }

    public String getSomeName() {
        return someName;
    }

    public void setSomeName(String someName) {
        this.someName = someName;
    }
}
