
// menu item . java
package com.example.hqrestaurant;

public class MenuItem {
    public int id;
    public String name;
    public String price;
    public int imageResId;

    public MenuItem(int id, String name, String price, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }
}

