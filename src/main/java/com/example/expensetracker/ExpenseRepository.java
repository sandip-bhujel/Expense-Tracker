package com.example.expensetracker;



import com.example.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository <Expense , Long>{


    List<Expense> findByCategory(String category);
    List<Expense> findByNameContainingIgnoreCase(String name);// containing le match garcha //ignore case le sano thulo
    //word farak nagarne
}
