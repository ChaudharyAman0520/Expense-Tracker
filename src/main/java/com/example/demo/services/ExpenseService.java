package com.example.demo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense createExpense(Expense expense) {
        User user = expense.getUser();
        BigDecimal walletBalance = user.getWalletBalance();
        BigDecimal expenseAmount = expense.getExpenseAmount();

        if (walletBalance == null) {
            throw new RuntimeException("User wallet is not set.");
        }
        if (expenseAmount.compareTo(walletBalance) > 0) {
            throw new RuntimeException("Expense amount (" + expenseAmount + ") exceeds your remaining wallet balance (" + walletBalance + ").");
        }

        // Deduct from wallet and save the user
        user.setWalletBalance(walletBalance.subtract(expenseAmount));
        userRepository.save(user);

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

    public List<Expense> findByCategoryNameRaw(String categoryName) {
        return expenseRepository.findByCategoryName(categoryName);
    }
    public List<Expense> findExpensesByUserId(Integer userId) {
        // You'll need to add the findByUserId method to your repository
        return expenseRepository.findByUser_UserId(userId);
    }
}
