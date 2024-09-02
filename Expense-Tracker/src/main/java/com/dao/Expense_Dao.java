package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.entity.Expense;

@Repository
public class Expense_Dao{
	@Autowired
	SessionFactory factory;
	public void saveExpense(Expense expense) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(expense);
        tx.commit();
        session.close();
        System.out.println("Expense saved: " + expense);
    }
	public void updateExpense(Expense expense) {
	    Session session = factory.openSession();
	    Transaction tx = session.beginTransaction();
	    session.update(expense);
	    tx.commit();
	    session.close();
	    System.out.println("Expense updated: " + expense);
	}
	public List<Expense> getAllExpenses() {
	    Session session = factory.openSession();
	    Query<Expense> query = session.createQuery("from Expense", Expense.class);
	    List<Expense> expenses = query.list();
	    session.close();
	    return expenses;
	}
	public List<Expense> getExpensesByCategory(String category) {
	    Session session = factory.openSession();
	    Query<Expense> query = session.createQuery("from Expense where category = :category", Expense.class);
	    query.setParameter("category", category);
	    List<Expense> expenses = query.list();
	    session.close();
	    return expenses;
	}
	public void deleteExpenseById(Long id) {
        Session session = factory.openSession();
        
        Query<Expense> query = session.createQuery("delete from Expense where id = :id");
        query.setParameter("id", id);
        Transaction tx=session.beginTransaction();
        // Execute the update operation
        int result = query.executeUpdate();
        tx.commit();
        System.out.println(result + " expense(s) deleted with ID: " + id);

        // Close the session
        session.close();
    }
	public Double calculateTotalAmount() {
	    Session session = factory.openSession();
	    
	    // Create a query to sum up the amount field
	    Query<Double> query = session.createQuery("select sum(amount) from Expense", Double.class);
	    
	    // Get the result (total amount) or default to 0.0 if no records are found
	    Double totalAmount = query.uniqueResult();
	    session.close();
	    
	    return totalAmount != null ? totalAmount : 0.0;
	}

	
}
