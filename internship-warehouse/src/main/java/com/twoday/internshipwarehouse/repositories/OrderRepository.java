package com.twoday.internshipwarehouse.repositories;

import com.twoday.internshipwarehouse.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}
