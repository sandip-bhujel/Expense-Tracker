package com.example.expensetracker.controller;



import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController // yo controller le json response API banauna help garcha
@RequestMapping("/expenses") // sabai URL expense bata start garcha
public class ExpenseController {

    @Autowired // springboot le automatic object banaucha
    private ExpenseService expenseService;





    //POST /expense/add- naya expense add garda
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        //request body bata json data lincha

        return expenseService.addExpense(expense);
    }

    //GET /expense /list  sabai expense herne list
    @GetMapping("/list")
    public List<Expense> getAllExpenses() {

        return  expenseService.getAllExpenses();
    }


//GET/expense/ {id} id le expense herna ko lagi
    @GetMapping("/{id}")
    public Expense getExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.getExpenseById(id);
    }

    //PUT expense id  expense update garna
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id , expense);
    }

//DElETE /expense /{id} expense lai delete garna ko lagi
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }




}
