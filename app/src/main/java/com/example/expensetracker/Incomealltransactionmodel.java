package com.example.expensetracker;

public class Incomealltransactionmodel {
    String Id, Price, Category, Date, Discription, Paymentmode, Image;

    public Incomealltransactionmodel(String id, String price, String category, String date, String discription, String paymentmode, String image) {
        Id = id;
        Price = price;
        Category = category;
        Date = date;
        Discription = discription;
        Paymentmode = paymentmode;
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public String getPrice() {
        return Price;
    }

    public String getCategory() {
        return Category;
    }

    public String getDate() {
        return Date;
    }

    public String getDiscription() {
        return Discription;
    }

    public String getPaymentmode() {
        return Paymentmode;
    }

    public String getImage() {
        return Image;
    }
}