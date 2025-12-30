
// menu item . java
package com.example.hqrestaurant;

public class MenuItem {

    private final int id;
    private final String name;
    private final String price;

    // stored in DB (e.g. "burger")
    private final String imageName;

    // resolved drawable id (e.g. R.drawable.burger)
    private final int imageResId;

    public MenuItem(int id, String name, String price, String imageName, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageName = imageName;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageName() { return imageName; }
    public int getImageResId() { return imageResId; }
}






