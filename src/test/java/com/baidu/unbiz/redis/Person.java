package com.baidu.unbiz.redis;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -4751228792114485251L;

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person() {

    }

    public Person(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "id:" + id + ", name: " + name;
    }

}
