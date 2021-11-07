package sample;

import javafx.collections.ObservableArray;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class users {

    String name;
    String age;
    String gender;
    String freedom;

    public users(String name, String age, String gender,String freedom) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.freedom = freedom;
    }


    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender(){
        return gender;
    }

    public String getFreedom() {
        return freedom;
    }




}
