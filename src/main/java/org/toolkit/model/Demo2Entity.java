package org.toolkit.model;

import org.toolkit.easyexcel.read.MsgInfo;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "demo2")
public class Demo2Entity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "test1")
    private String test1;
    @Column(name = "test2")
    private String test2;
    @Column(name = "test3")
    private String test3;
    @Column(name = "test4")
    private String test4;
    @Column(name = "test5")
    private String test5;
    @Column(name = "test6")
    private String test6;
    @Column(name = "test7")
    private String test7;

    @Override
    public String toString() {
        return "Demo2Entity{" +
                "id=" + id +
                ", test1='" + test1 + '\'' +
                ", test2='" + test2 + '\'' +
                ", test3='" + test3 + '\'' +
                ", test4='" + test4 + '\'' +
                ", test5='" + test5 + '\'' +
                ", test6='" + test6 + '\'' +
                ", test7='" + test7 + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getTest3() {
        return test3;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return test4;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

    public String getTest5() {
        return test5;
    }

    public void setTest5(String test5) {
        this.test5 = test5;
    }

    public String getTest6() {
        return test6;
    }

    public void setTest6(String test6) {
        this.test6 = test6;
    }

    public String getTest7() {
        return test7;
    }

    public void setTest7(String test7) {
        this.test7 = test7;
    }
}