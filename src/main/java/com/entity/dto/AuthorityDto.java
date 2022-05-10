package com.entity.dto;

import com.entity.Authority;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class AuthorityDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String authorityName;


    public static AuthorityDto toDto(Authority authority){
        if(authority == null) return null;
        return AuthorityDto.builder()
                .id(authority.getId())
                .authorityName(authority.getAuthorityName())
                .build();
    }

}