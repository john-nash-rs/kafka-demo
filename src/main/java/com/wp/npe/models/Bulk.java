package com.wp.npe.models;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class Bulk {

    private String name;

    private String studentId;

    private String isPresent;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
