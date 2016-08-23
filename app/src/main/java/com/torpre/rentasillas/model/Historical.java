package com.torpre.rentasillas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Historical {

    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String historicalTag;

    public Historical() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHistoricalTag() {
        return historicalTag;
    }

    public void setHistoricalTag(String historicalTag) {
        this.historicalTag = historicalTag;
    }
}
