package com.example.demo.service;

import com.example.demo.model.IncrementCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.FindAndModifyOptions;

@Service
public class IncrementService {

    @Autowired
    private MongoOperations mongoOperations;
    public long generateCounter(String counterName) {
        IncrementCounter counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(counterName)),
                new Update().inc("counter", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                IncrementCounter.class);
        return counter != null ? counter.getCounter() : 1;
    }
}