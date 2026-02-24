package com.miaoubich.param;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paramName;
    public String paramValue;

    public Param() {}
    public Param(String paramValue) {
        this.paramValue = paramValue;
    }
    public Long getId() {
        return id;
    }
    public String getParamName() {
        return paramName;
    }
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    public String getParamValue() {
        return paramValue;
    }
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    @Override
    public String toString() {
        return "ParamValue [id=" + id + ", paramValue=" + paramValue + ", paramName=" + paramName + "]";
    }
}
