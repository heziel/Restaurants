package com.zumper.apps.restaurants.model;

import java.util.ArrayList;
import java.util.List;

/**
 * For Thw Photo of each Restaurants.
 */

public class Photo {
    private Integer height;
    private List<String> htmlAttributions = new ArrayList<String>();
    private String photoReference;
    private Integer width;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
