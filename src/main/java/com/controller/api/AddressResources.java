package com.controller.api;

import com.entity.District;
import com.entity.dto.ResponseDto;
import com.repository.DistrictRepository;
import com.repository.ProvinceRepository;
import com.repository.WardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/address")
public class AddressResources {

    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    public AddressResources(WardRepository wardRepository, DistrictRepository districtRepository, ProvinceRepository provinceRepository) {
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping("districts/{provinceId}")
    public Object getDistricts(@PathVariable Integer provinceId) {
        return ResponseDto.of(this.districtRepository.findAllByProvinceId(provinceId), "Get districts");
    }

    @GetMapping("wards/{districtId}")
    public Object getWards(@PathVariable Integer districtId) {
        return ResponseDto.of(this.wardRepository.findByDistrictId(districtId), "Get wards");
    }
}
