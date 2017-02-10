package com.codingblocks.ChatBot_And_InterestRanker.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by megha on 29/01/17.
 */

public class ExpenditureModel implements Serializable{

    private int id;
    String description;
    String type;                        // Tell whether its to be paid, already paid, budget entry, payment entry or anything else
    double amountRecord;                // Note of the amount to be dealt
    String amountUnit;                  // Like its in dollars, rupees, pounds, etc.
    String date;
    int split;                          // to be splitted or not
    ArrayList<String> notes;
    ArrayList<String> names;            // Names of people with whom bill is to be splitted
    ArrayList<Double> splitAmounts;     // Amounts corresponding to various people

    public ExpenditureModel(int id, String description, String type, double amountRecord, String amountUnit, String date,
            int split, String notes, String names, String splitAmounts){
        this.id = id;
        this.description = description;
        this.type = type;
        this.amountRecord = amountRecord;
        this.amountUnit = amountUnit;
        this.date = date;
        this.split = split;
        this.notes = getStringArray(notes);
        this.names = getStringArray(names);
        this.splitAmounts = getDoubleArray(splitAmounts);
    }

    public ExpenditureModel(String description, String type, double amountRecord, String amountUnit, String date,
                            int split, String notes, String names, String splitAmounts){
        this.description = description;
        this.type = type;
        this.amountRecord = amountRecord;
        this.amountUnit = amountUnit;
        this.date = date;
        this.split = split;
        this.notes = getStringArray(notes);
        this.names = getStringArray(names);
        this.splitAmounts = getDoubleArray(splitAmounts);
    }

    public static ArrayList<Double> getDoubleArray(String in) {
        if(in == null){
            return null;
        }
        ArrayList<Double> out = new ArrayList<>();
        for(int i=0; i<in.length(); i++){
            StringBuffer s = new StringBuffer();
            while(in.charAt(i) != ','){
                s.append(in.charAt(i));
                i++;
            }
            i+=2;
            out.add(Double.parseDouble(s.toString()));
        }
        return out;
    }

    public static ArrayList<String> getStringArray(String in) {
        if(in == null){
            return null;
        }
        ArrayList<String> out = new ArrayList<>();
        for(int i=0; i<in.length(); i++){
            StringBuffer s = new StringBuffer();
            while(in.charAt(i) != ','){
                s.append(in.charAt(i));
                i++;
            }
            i+=2;
            out.add(s.toString());
        }
        return out;
    }

    public static String stringArrayToString(ArrayList<String> in) {
        if(in == null){
            return null;
        }
        StringBuffer out = new StringBuffer();
        for(int i=0; i<in.size(); i++){
            out.append(in.get(i));
            out.append(", ");
        }
        return out.toString();
    }

    public static String intArrayToStrings(ArrayList<Double> in) {
        if(in == null){
            return null;
        }
        StringBuffer out = new StringBuffer();
        for(int i=0; i<in.size(); i++){
            out.append(in.get(i));
            out.append(", ");
        }
        return out.toString();
    }

    public int getID(){
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getAmountRecord() {
        return amountRecord;
    }

    public String getAmountUnit() {
        return amountUnit;
    }

    public String getDate() {
        return date;
    }

    public int getSplit() {
        return split;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Double> getSplitAmounts() {
        return splitAmounts;
    }

    public  String getNotesString() {
        return stringArrayToString(notes);
    }

    public String getNamesString() {
        return stringArrayToString(names);
    }

    public String getSplitAmountsString() {
        return intArrayToStrings(splitAmounts);
    }
}
