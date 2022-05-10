package com.service;

import com.entity.Authority;
import com.entity.dto.AuthorityDto;
import com.entity.model.AuthorityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorityService extends BaseService<AuthorityModel, AuthorityDto, Integer> {


    Authority findAuthorityByAndAuthorityName(String user);
}
