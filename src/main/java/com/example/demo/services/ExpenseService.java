package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(Integer id) {
        return expenseRepository.findById(id);
    }

    public Expense updateExpense(Integer id, Expense expense) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(!optionalExpense.isPresent()){
            throw new RuntimeException("Category Not Found With id "+id);
        }
        Expense existingExpense = optionalExpense.get();
        existingExpense.setUser(expense.getUser());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setExpenseAmount(expense.getExpenseAmount());
        existingExpense.setExpenseDate(expense.getExpenseDate());
        existingExpense.setNote(expense.getNote());
        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Integer id) {
        expenseRepository.deleteById(id);
    }
}
