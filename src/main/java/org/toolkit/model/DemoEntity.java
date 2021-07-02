package org.toolkit.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "demo1")
public class DemoEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "string")
    private String string;

    @Column(name = "date")
    private Date date;


    @Column(name = "doubleData")
    private Double doubleData;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private Double price;

    @Column(name = "descmarks")
    private String descmarks;

    @Column(name = "validPeriod")
    private String validPeriod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescmarks() {
        return descmarks;
    }

    public void setDescmarks(String desc) {
        this.descmarks = desc;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    @Override
    public String toString() {
        return "DemoEntity{" +
                "id=" + id +
                ", string='" + string + '\'' +
                ", date=" + date +
                ", doubleData=" + doubleData +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", desc='" + descmarks + '\'' +
                ", validPeriod='" + validPeriod + '\'' +
                '}';
    }
}