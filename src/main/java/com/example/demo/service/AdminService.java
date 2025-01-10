package com.example.demo.service;

import com.example.demo.model.AdminData;
import com.example.demo.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<AdminData> findAdminsByData(AdminData searchData){
        List<List<AdminData>> matchDataList = new ArrayList<>();
        matchDataList.add(adminRepository.findAll());

        if(searchData.getName() != null && !searchData.getName().isEmpty()){
            List<AdminData> matchByName = adminRepository.findAllByNameContains(searchData.getName());
            matchDataList.add(matchByName);
        }
        if(searchData.getRole() != null){
            List<AdminData> matchByAmount = adminRepository.findAllByRole(searchData.getRole());
            matchDataList.add(matchByAmount);
        }

        List<AdminData> intersection = new ArrayList<>(matchDataList.get(0));

        for (List<AdminData> list: matchDataList) {
            intersection.retainAll(list);
        }
        return intersection;
    }
}
