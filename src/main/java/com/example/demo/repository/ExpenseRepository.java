package com.example.demo.repository;

import com.example.demo.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

    @Query(value = "SELECT expenses.* FROM expenses " +
            "JOIN category ON " +
            "expenses.category_id = category.category_id " +
            "WHERE category.category_name = :categoryName",nativeQuery = true)
    List<Expense> findByCategoryName(@Param("categoryName") String categoryName);
}
