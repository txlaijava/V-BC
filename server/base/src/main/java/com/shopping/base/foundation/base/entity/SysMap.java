package com.shopping.base.foundation.base.entity;

public class SysMap {
    private Object id;
    private Object key;
    private Object value;
    private Object key2;
    private Object value2;
    public SysMap() {
    }
    public SysMap(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
    public SysMap(Object key, Object value, Object key2) {
        this.key = key;
        this.value = value;
        this.key2 = key2;
    }
    public SysMap(Object key, Object value, Object key2, Object value2) {
        this.key = key;
        this.value = value;
        this.key2 = key2;
        this.value2 = value2;
    }
    public SysMap(Object id, Object key, Object value, Object key2, Object value2) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.key2 = key2;
        this.value2 = value2;
    }
    public Object getId() {
        return id;
    }
    public void setId(Object id) {
        this.id = id;
    }
    public Object getKey() {
        return this.key;
    }
    public void setKey(Object key) {
        this.key = key;
    }
    public Object getValue() {
        return this.value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public Object getKey2() {
        return key2;
    }
    public void setKey2(Object key2) {
        this.key2 = key2;
    }
    public Object getValue2() {
        return value2;
    }
    public void setValue2(Object value2) {
        this.value2 = value2;
    }
}