package com.repository;

import com.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findByDistrictId(Integer districtId);
}
