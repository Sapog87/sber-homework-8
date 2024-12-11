package org.sber.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.sber.model.json.Current;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    private Current current;
}
