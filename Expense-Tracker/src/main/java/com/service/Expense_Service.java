package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.Expense_Dao;
import com.entity.Expense;

@Service
public class Expense_Service {
	  @Autowired
	  private Expense_Dao expenseDao;
	  
	  public void addExpense(Expense expense) {
	        expenseDao.saveExpense(expense);
	    }

	    // Update an existing expense
	    public void updateExpense(Expense expense) {
	        expenseDao.updateExpense(expense);
	    }

	    // Get all expenses
	    public List<Expense> getAllExpenses() {
	        return expenseDao.getAllExpenses();
	    }

	    // Get expenses by category
	    public List<Expense> getExpensesByCategory(String category) {
	        return expenseDao.getExpensesByCategory(category);
	    }
	    public void deleteExpenseById(Long id) {
	        // You could add business logic here if needed
	        expenseDao.deleteExpenseById(id);
	    }
	    public double calculateTotalAmount() {
	        return expenseDao.calculateTotalAmount();
	    }

	  
}
