package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Budget;
import com.example.demo.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Optional<Budget> getBudgetById(Integer id) {
        return budgetRepository.findById(id);
    }

    public Budget updateBudget(Integer id,Budget budget) {
        Optional<Budget> optionalBudget = budgetRepository.findById(id);
        if (!optionalBudget.isPresent()) {
            throw new RuntimeException("Budget not found with id " + id);
        }
        Budget existingBudget = optionalBudget.get();
        existingBudget.setUser(budget.getUser());
        existingBudget.setCategory(budget.getCategory());
        existingBudget.setBudgetAmount(budget.getBudgetAmount());
        existingBudget.setStartDate(budget.getStartDate());
        existingBudget.setEndDate(budget.getEndDate());
        return budgetRepository.save(existingBudget);
    }

    public void deleteBudget(Integer id) {
        budgetRepository.deleteById(id);
    }
}
