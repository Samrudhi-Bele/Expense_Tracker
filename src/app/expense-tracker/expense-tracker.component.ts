import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';


import { FormsModule } from '@angular/forms';
import { CommonModule, NgFor } from '@angular/common';
import { ExpenseServiceService } from '../expense-service.service';

@Component({
  selector: 'app-expense-tracker',
  standalone: true,
  imports: [RouterOutlet,FormsModule,CommonModule,],
  templateUrl: './expense-tracker.component.html',
  styleUrl: './expense-tracker.component.css'
})
export class ExpenseTrackerComponent implements OnInit {
  expenses: Expense[] = [];
  newExpense: Expense = { id: 0, name: '', description: '', amount: 0, category: '', date: '' };
  showTable = false;
  deleteMessage: string | null = null;
  totalAmount: number = 0;
  constructor(private expenseService: ExpenseServiceService) { }

  ngOnInit(): void {
    this.getAllExpenses();
    this.getTotalAmount();
  }

  // Fetch all expenses
  getAllExpenses(): void {
    this.expenseService.getAllExpenses().subscribe(expenses => {
      this.expenses = expenses;
    });
  }

  // Add a new expense
  addExpense(): void {
    this.expenseService.addExpense(this.newExpense).subscribe(() => {
      this.getAllExpenses(); // Refresh the list after adding
      this.getTotalAmount();
      this.newExpense = { id: 0, name: '', description: '', amount: 0, category: '', date: '' }; // Reset the form
    });
  }

  // Update an existing expense
  updateExpense(id: number): void {
    this.expenseService.updateExpense(id, this.newExpense).subscribe(() => {
      this.getAllExpenses(); // Refresh the list after updating
    });
  }
  deleteExpense(id: number): void {
    this.expenseService.deleteExpense(id).subscribe(response => {
      const responseBody = response as { message?: string; error?: string };
      this.deleteMessage = response.message || response.error ||'Unknown error occurred'; // Handle JSON response

      // Update expenses list and show message
      this.expenses = this.expenses.filter(expense => expense.id !== id);

      // Show message for a short time before refresh
      setTimeout(() => {
        this.deleteMessage = null;
        sessionStorage.removeItem('deleteMessage');
        location.reload(); // Force a page refresh
      }, 100); // 3 seconds delay to show the message
    }, error => {
      console.error('Error deleting expense:', error);
    });
  }

  retrieveDeleteMessage(): void {
    this.deleteMessage = sessionStorage.getItem('deleteMessage');
  }

  toggleTable() {
    this.showTable = !this.showTable;
  }
  // Filter expenses by category
  filterByCategory(category: string): void {
    this.expenseService.getExpensesByCategory(category).subscribe(expenses => {
      this.expenses = expenses;
    });
  }
  getTotalAmount(): void {
    this.expenseService.getTotalAmount().subscribe({
      next: (amount) => {
        console.log('Total amount received:', amount); // Log the received amount
        this.totalAmount = amount;
       
      },
      error: (err) => {
        console.error('Error fetching total amount:', err); // Log any errors
      }
    });
  }


}
export class Expense{ constructor(
  public id: number,
  public name: string,
  public description: string,
  public amount: number,
  public category: string,
  public date: string
) {}}