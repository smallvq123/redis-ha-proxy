package com.baidu.unbiz.redis.util;

import java.io.Serializable;

public class KvPair implements Serializable {

    private static final long serialVersionUID = 3905949474750277625L;

    private String key;

    private Object value;

    public KvPair() {
        super();
    }

    public KvPair(String key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
