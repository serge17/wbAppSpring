package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Data;
@Repository
public interface DataRepository extends JpaRepository<Data, String> {

}
