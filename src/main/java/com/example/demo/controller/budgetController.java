package com.example.demo.controller;

import com.example.demo.entity.Budget;
import com.example.demo.services.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budget")
public class budgetController {

    private final BudgetService budgetService;

    public budgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget){
        return ResponseEntity.ok(budgetService.createBudget(budget));
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets(){
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Integer id){
        Optional<Budget> budget = budgetService.getBudgetById(id);
        if(budget.isPresent())
        {
            return ResponseEntity.ok(budget.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(
            @PathVariable Integer id,
            @RequestBody Budget budgetDetails){
        return ResponseEntity.ok(budgetService.updateBudget(id,budgetDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Integer id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
