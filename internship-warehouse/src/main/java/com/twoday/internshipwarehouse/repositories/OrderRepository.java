package com.twoday.internshipwarehouse.repositories;

import com.twoday.internshipwarehouse.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // veiks be
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByTimestampBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
