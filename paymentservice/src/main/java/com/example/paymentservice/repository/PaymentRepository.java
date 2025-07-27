package com.example.paymentservice.repository;

import com.example.paymentservice.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByProcessInstanceId(String processInstanceId);
    List<PaymentEntity> findAllByProcessInstanceIdIsNullOrderByIdDesc();
    List<PaymentEntity> findAllByProcessInstanceIdIsNotNullOrderByIdDesc();
}
