package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing Exercise information from json string
 */

public class ErrorResponse {
    @SerializedName("error")
    @Expose
    private ErrorJsonModel error;

    public ErrorJsonModel getError() {
        return error;
    }

    public void setError(ErrorJsonModel error) {
        this.error = error;
    }
}

