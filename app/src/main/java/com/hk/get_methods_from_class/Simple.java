package com.hk.get_methods_from_class;

import com.hk.main.utils.LogUtils;

/**
 * Created by Hovhannisyan.Karo on 03.11.2017.
 */

public class Simple {

    private String name;
    private Integer age;
    private String sName;
    private String animalName;
    private String friendName;
    private Boolean gender;


    public Simple() {
    }

    public Simple(String name, Integer age, String sName, String animalName, String friendName, Boolean gender) {
        this.name = name;
        this.age = age;
        this.sName = sName;
        this.animalName = animalName;
        this.friendName = friendName;
        this.gender = gender;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getName() {
        d("get name");
        return name;
    }

    public void setName(String name) {
        d("set name");
        this.name = name;
    }

    public Integer getAge() {
        d("get age");
        return age;
    }

    public void setAge(Integer age) {
        d("set age");
        this.age = age;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "Simple{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sName='" + sName + '\'' +
                ", animalName='" + animalName + '\'' +
                ", friendName='" + friendName + '\'' +
                ", gender=" + gender +
                '}';
    }

    private void d(String msg) {
//        LogUtils.d(msg);
    }
}
