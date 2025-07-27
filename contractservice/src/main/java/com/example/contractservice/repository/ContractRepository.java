package com.example.contractservice.repository;

import com.example.contractservice.model.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    ContractEntity findByProcessInstanceId(String processInstanceId);
}
