package com.codingblocks.ChatBot_And_InterestRanker;

import java.io.Serializable;

/**
 * Created by HIman$hu on 9/4/2016.
 */
public class CourseDetail  implements Serializable{
    String name;
    String desc;

    public CourseDetail(String name,String desc){
        this.name=name;
        this.desc=desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
