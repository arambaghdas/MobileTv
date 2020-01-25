package com.pttrackershared.models.eventbus;

import java.io.Serializable;

/**
 * Model class for deserializing Exercise information from json string
 */

public class ErrorResponse implements Serializable {

    private ErrorModel error;

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
