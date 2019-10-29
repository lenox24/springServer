package com.example.demo.repo;

import com.example.demo.model.DataModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataModelRepo extends MongoRepository<DataModel, String> {
}
