import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Expense } from './expense-tracker/expense-tracker.component';

@Injectable({
  providedIn: 'root'
})
export class ExpenseServiceService {

  private apiUrl = 'http://localhost:8080/api/expenses'; // Adjust this URL to match your backend

  constructor(private http: HttpClient) { }

  getAllExpenses(): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.apiUrl}`);
  }
  getTotalAmount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/totalAmount`);
  }


  addExpense(expense: Expense): Observable<Expense> {
    return this.http.post<Expense>(`${this.apiUrl}`, expense);
  }

  updateExpense(id: number, expense: Expense): Observable<Expense> {
    return this.http.put<Expense>(`${this.apiUrl}/${id}`, expense);
  }

  getExpensesByCategory(category: string): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.apiUrl}/category/${category}`);
  }

  deleteExpense(id: number): Observable<{ message?: string; error?: string }> {
    return this.http.delete<{ message?: string; error?: string }>(`${this.apiUrl}/${id}`, { responseType: 'json' });
  }
 

}
