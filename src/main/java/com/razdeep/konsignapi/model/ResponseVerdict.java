package com.razdeep.konsignapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVerdict implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;
}
