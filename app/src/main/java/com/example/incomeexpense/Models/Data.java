package com.example.incomeexpense.Models;

public class Data {
    String date;
    String category;
    String categoryNmonthNlabel;
    String categoryNyearNlabel;
    String note;
    String id;
    String label;
    String monthNlabel;
    String yearNlable;
    String dateNlable;


    int amount;
    int month;
    int year;
    public Data() {

    }

    public Data(String id,String date, String category, String categoryNmonthNlabel, String categoryNyearNlabel, String note,String label,String monthNlabel,String yearNlable,String dateNlable, int amount, int month, int year) {
        this.date = date;
        this.label = label;
        this.category = category;
        this.categoryNmonthNlabel = categoryNmonthNlabel;
        this.categoryNyearNlabel = categoryNyearNlabel;
        this.monthNlabel = monthNlabel;
        this.yearNlable = yearNlable;
        this.dateNlable = dateNlable;

        this.note = note;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.id = id;
    }

    public String getDateNlable() {
        return dateNlable;
    }

    public void setDateNlable(String dateNlable) {
        this.dateNlable = dateNlable;
    }

    public String getCategoryNmonthNlabel() {
        return categoryNmonthNlabel;
    }

    public void setCategoryNmonthNlabel(String categoryNmonthNlabel) {
        this.categoryNmonthNlabel = categoryNmonthNlabel;
    }

    public String getCategoryNyearNlabel() {
        return categoryNyearNlabel;
    }

    public void setCategoryNyearNlabel(String categoryNyearNlabel) {
        this.categoryNyearNlabel = categoryNyearNlabel;
    }

    public String getMonthNlabel() {
        return monthNlabel;
    }

    public void setMonthNlabel(String monthNlabel) {
        this.monthNlabel = monthNlabel;
    }

    public String getYearNlable() {
        return yearNlable;
    }

    public void setYearNlable(String yearNlable) {
        this.yearNlable = yearNlable;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}