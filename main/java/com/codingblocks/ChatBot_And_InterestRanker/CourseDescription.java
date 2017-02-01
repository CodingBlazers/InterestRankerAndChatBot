package com.codingblocks.ChatBot_And_InterestRanker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HIman$hu on 9/4/2016.
 */
public class CourseDescription implements Serializable {

   String name;
   String desc;

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
