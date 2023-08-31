package com.company.gamestore.repository;

import com.company.gamestore.model.TShirt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TShirtRepository extends JpaRepository<TShirt, Integer> {
    List<TShirt> findByColor(String color);
    List<TShirt> findBySize(String size);
}
