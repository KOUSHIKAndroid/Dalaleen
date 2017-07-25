package com.dalaleen.Pojo;

import org.json.JSONObject;

/**
 * Created by su on 4/5/17.
 */

public class MyProperties {
    String id;
    String name;
    String address;
    String unique_identification_no;
    String Price;
    String property_description;
    String property_image;
    String Category_name;
    String category_sub;
    String details;

    public String getCategory_sub() {
        return category_sub;
    }

    public void setCategory_sub(String category_sub) {
        this.category_sub = category_sub;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnique_identification_no() {
        return unique_identification_no;
    }

    public void setUnique_identification_no(String unique_identification_no) {
        this.unique_identification_no = unique_identification_no;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProperty_description() {
        return property_description;
    }

    public void setProperty_description(String property_description) {
        this.property_description = property_description;
    }

    public String getProperty_image() {
        return property_image;
    }

    public void setProperty_image(String property_image) {
        this.property_image = property_image;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
