package com.example.demo.repositories;

import com.example.demo.model.AdminData;
import com.example.demo.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdminRepository extends MongoRepository<AdminData, String> {
    AdminData findByName(String name);
    List<AdminData> findAllByNameContains(String name);
    List<AdminData> findAllByRole(Role role);
}
