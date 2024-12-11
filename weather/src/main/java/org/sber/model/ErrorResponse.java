package org.sber.model;

import lombok.Data;
import org.sber.model.json.Error;

@Data
public class ErrorResponse {
    private Error error;
}
