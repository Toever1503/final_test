package com.service.impl;

import com.entity.Authority;
import com.entity.dto.AuthorityDto;
import com.entity.model.AuthorityModel;
import com.repository.AuthorityRepository;
import com.service.AuthorityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }


    @Override
    public Authority findAuthorityByAndAuthorityName(String name) {
        return authorityRepository.findAuthorityByAndAuthorityName(name).orElse(null);
    }

    @Override
    public AuthorityDto findById(Integer id) {
        return null;
    }

    @Override
    public Page<AuthorityDto> findAll(Pageable page) {
        return this.authorityRepository.findAll(page).map(AuthorityDto::toDto);
    }

    @Override
    public List<AuthorityDto> findAll() {
        return this.authorityRepository.findAll().stream().map(AuthorityDto::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<AuthorityDto> search(String q, Pageable page) {
        return null;
    }

    @Override
    public AuthorityDto add(AuthorityModel model) {
        return null;
    }

    @Override
    public AuthorityDto update(AuthorityModel model) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
