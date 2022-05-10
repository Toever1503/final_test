package com.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@Data
public class ResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String version = "1.0";
    private String result;
    private Object data;
    private String message;

    public ResponseDto(String s, Object data, String s1) {
        this.result = s;
        this.data = data;
        this.message = s1;
    }

    public static ResponseDto ofCreated(Object data) {
        return new ResponseDto(data != null ? "SUCCESS" : "ERROR", data, data != null ? "Created successfully!" : "Created failed!");
    }

    public static ResponseDto ofDelete(Object data) {
        return null;
    }

    public static ResponseDto of(Object data, String prefix) {
        return new ResponseDto(data != null ? "SUCCESS" : "ERROR", data, data != null ? prefix.concat(" successfully!") : prefix.concat(" failed!"));
    }

}
