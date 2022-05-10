package com.entity.dto;


import com.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;

    private String username;

    private String fullName;
    private String email;
    private Boolean status = true;
    private Integer activeCode = 0;
    private Integer timeFailed;
    private Date createdAt;
    private Date updatedAt;

    private java.util.List<AuthorityDto> authorities;
    private String imageProfile;

    public static UserDto toDto(User userEntity) {
        if (userEntity == null) return null;
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .activeCode(userEntity.getActiveCode())
                .timeFailed(userEntity.getTimeFailed())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .authorities(userEntity.getAuthorities().stream().map(AuthorityDto::toDto).collect(java.util.stream.Collectors.toList()))
                .imageProfile(userEntity.getImageProfile())
                .build();
    }
}
