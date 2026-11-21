package com.example.expensetracker.controller;


import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller  // html page return garcha restcontroller hoina farak ho
public class ExpenseWebController {

    @Autowired
    private ExpenseService expenseService;

    //GET list html page dekhauna kaam garcha
//    @GetMapping("/expenses-page")
//    public String listExpense(Model model) { // model le html page lai data pass garcha
//        model.addAttribute("expenses", expenseService.getAllExpenses()); // expenses bhanney name le data pathaucha
//        model.addAttribute("totalExpense", expenseService.getTotalExpense());
//        model.addAttribute("categoryTotals",
//                expenseService.getCategoryWiseTotal());
//        model.addAttribute("monthlyTotals", expenseService.getMonthlyTotal());
//
//
//
//
//        return "list"; // list.html file return huncha
//
//    }

    //GET expenses page add  form ma add dekhauney
    @GetMapping("/expenses-page/add")
    public String showAddForm(Model model) {

        Expense expense = new Expense();
        expense.setDate(LocalDateTime.now().withSecond(0).withNano(0)); // ajako date time rakhyo
        model.addAttribute("expense",  expense);     //empty expense object pathayo
        return "add-expense";
    }

    //POST expenses-page add form submit garda handle garcha
    @PostMapping("/expenses-page/add")
    public String addExpense(@ModelAttribute Expense expense) { //formko data automatic object ma dincha
        expenseService.addExpense(expense); //database ma save garcha

        return "redirect:/expenses-page"; //save vaisakepachi list page ma firta pathaucha
    }

    //GET expenses-page/edit/{id} -  edit form dekhaune
    @GetMapping("/expenses-page/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("expense", expenseService.getExpenseById(id)); //pursno data form varcha

        return "edit";  //
    }

    @PostMapping("/expenses-page/edit/{id}")
    public String editExpense(@PathVariable Long id, @ModelAttribute Expense expense) {
        expenseService.updateExpense(id, expense);

        return "redirect:/expenses-page";
    }

    @GetMapping("/expenses-page/delete/{id}")
    public String deleteexpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);


        return "redirect:/expenses-page";
    }

//    @GetMapping("/expenses-page")
//    public String listExpenses(Model model) {
//        model.addAttribute("expense", expenseService.getAllExpenses());
//        model.addAttribute("total", expenseService.getTotalExpense()); // total pathaucha
//
//        return "list";
//    }

    @GetMapping("/expenses-page")
    public String listExpenses(
            Model model,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate date,
            @RequestParam(required = false) String category ,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String month) {
        List<Expense> expenses;

        if (date != null) {
            expenses = expenseService.getExpenseByDate(date);

        } else if (month != null && !month.isEmpty()) {
            expenses = expenseService.getExpensesByMonth(month);

        } else if (name != null && !name.isEmpty()) {
            expenses = expenseService.getExpensesByName(name);

        } else if (category != null && !category.isEmpty()) {
            expenses = expenseService.getExpensesByCategory(category);

        } else {
            expenses = expenseService.getAllExpenses();
        }


//            if (month != null && !month.isEmpty()) {
//                expenses = expenseService.getExpensesByMonth(month);
//
//            } else if (name != null && !name.isEmpty()) {
//                expenses = expenseService.getExpensesByName(name);
//            } else if (category != null && !category.isEmpty()) {
//                expenses = expenseService.getExpensesByCategory(category);
//            } else {
//                expenses = expenseService.getAllExpenses();
//            }

            model.addAttribute("expenses", expenses);

            model.addAttribute("totalExpense",
                    expenseService.getTotalExpense());

            model.addAttribute("categoryTotals",
                    expenseService.getCategoryWiseTotal());

            model.addAttribute("monthlyTotals",
                    expenseService.getMonthlyTotal());

            model.addAttribute("total", expenseService.getTotalExpense());


            return "list";
        }

        @GetMapping("/dashboard")
        public String showDashboard (Model model){
            model.addAttribute("total", expenseService.getTotalExpense());
            model.addAttribute("categoryTotals", expenseService.getCategoryWiseTotal());
            model.addAttribute("monthlyTotals", expenseService.getMonthlyTotal());
            model.addAttribute("expenses", expenseService.getAllExpenses()); //recent ko expense dekhuney
            model.addAttribute("monthTotal", expenseService.getCurrentMonthTotal());

            return "dashboard"; //template ko dashboard.html return huncha

        }
//Get login page dekhauney
    @GetMapping("/login")
    public String showLogin() {
        return "login";   //template login ko html page
    }

}
