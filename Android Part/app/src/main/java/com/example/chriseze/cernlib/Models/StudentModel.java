package com.example.chriseze.cernlib.Models;

/**
 * Created by CHRIS EZE on 6/27/2018.
 */

public class StudentModel {
    private String name, regno, department, level, _id;

    public StudentModel(String name, String regno, String department, String level, String _id){
        this.department = department;
        this.level = level;
        this.name = name;
        this.regno = regno;
        this._id = _id;
    }

    public String getName(){
        return name;
    }
    public String getRegno(){
        return regno;
    }
    public String getDepartment(){
        return department;
    }
    public String getLevel(){
        return level;
    }
    public String get_id(){
        return _id;
    }

}
