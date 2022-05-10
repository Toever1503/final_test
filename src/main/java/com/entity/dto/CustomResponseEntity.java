package com.entity.dto;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class CustomResponseEntity {
    private String result = "error";
    private String message;
    private Object data;

    public static ResponseEntity of(String message, Object data) {
        CustomResponseEntity res = new CustomResponseEntity();
        res.setMessage(message);
        if (data == null) return ResponseEntity.status(200).body(res);
        res.setData(data);
        res.setResult("success");
        return ResponseEntity.status(200).body(res);
    }

}
