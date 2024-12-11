package org.sber.model.json;

import lombok.Data;

@Data
public class Error {
    private int code;
    private String message;
}
