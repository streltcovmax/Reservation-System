package com.example.demo.repositories;

import com.example.demo.model.AdminData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<AdminData, String> {
    AdminData findByName(String name);
}
