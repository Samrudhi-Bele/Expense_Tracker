package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Expense;
import com.service.Expense_Service;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("http://localhost:4200")
public class Expense_Controller {
	@Autowired
	 public Expense_Service expenseService;
	
	@PostMapping
    public void addExpense(@RequestBody Expense expense) {
        expenseService.addExpense(expense);
    }

    @PutMapping("/{id}")
    public void updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        expense.setId(id);
        expenseService.updateExpense(expense);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category) {
        return expenseService.getExpensesByCategory(category);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteExpenseById(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            expenseService.deleteExpenseById(id);
            response.put("message", "Expense deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Expense not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @GetMapping("/totalAmount")
    public ResponseEntity<Double> getTotalAmount() {
        double totalAmount = expenseService.calculateTotalAmount();
        return ResponseEntity.ok(totalAmount);
    }

	
}
