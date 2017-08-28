package com.zumper.apps.restaurants.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant Opening Hours information.
 */

public class OpeningHours {

    private Boolean openNow;
    private List<Object> weekdayText = new ArrayList<Object>();

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
