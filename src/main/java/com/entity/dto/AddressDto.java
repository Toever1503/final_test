package com.entity.dto;

import com.entity.District;
import com.entity.Province;
import com.entity.User;
import com.entity.Ward;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;



    private Province provinceId;

    private District districtId;

    private Ward wardId;

    private String detail;

    private UserDto userId;

}