package com.example.expensetracker.service;




import com.example.expensetracker.ExpenseRepository;
import com.example.expensetracker.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Service //bean ho springboot lai vancha
public class ExpenseService {

    @Autowired // automatically object banaucha
    private ExpenseRepository expenseRepository;

    //new expense add garney method
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense); //save le insert ra update dubai garcha


    }

    //sabai expense list garney list ma dekhauney method
    public List<Expense> getAllExpenses() {

        return expenseRepository.findAll(); //database ko sabai row list ma dekhauney
    }

    //expense id le khojney method
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);//id bata vetayo vane return garney navaye null auney
    }

    //expense update garney method
    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(id).orElse(null); //paila purano data khojney
        if (expense != null) { //vete mattra update garney

            expense.setAmount(updatedExpense.getAmount());
            expense.setName(updatedExpense.getName());
            expense.setCategory(updatedExpense.getCategory());
            expense.setDate(updatedExpense.getDate());
            expense.setDescription(updatedExpense.getDescription());
            return expenseRepository.save(expense);
        }


        return null; // if not found return is null
    }

    //expense lai delete garney method
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id); // id le khojera delete garne
    }

//sabaiko total amount nikalna

    public Double getTotalExpense() {
        List<Expense> expenses = expenseRepository.findAll(); //sabai expense list liney
        double total = 0;  //start ma zero
        for (Expense expense : expenses) {
            total += expense.getAmount(); //jodney
        }

        return total;
    }

    //category anusar total nikalney
    public Map<String, Double> getCategoryWiseTotal() {
        List<Expense> expenses = expenseRepository.findAll();
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) { // every expense ma loop garne
            String category = expense.getCategory(); //expense ko category nikalney
            double amount = expense.getAmount(); //expense ko amount nikalney

            if (categoryTotals.containsKey(category)) { //if paile vaisakeko cha bhaney yo diney
                categoryTotals.put(category, categoryTotals.get(category) + amount); //purano total ma jodney
            } else { // pailo choti category ako cha bhaney
                categoryTotals.put(category, amount); //naya entry banauney

            }
        }
        return categoryTotals;// like  food:800 transport :600
    }

    //month anusar garna lako
    public Map<String , Double> getMonthlyTotal() {
        List<Expense> expenses = expenseRepository.findAll();
        Map<String , Double> monthlyTotals = new TreeMap<>(); //hash map ko ssatta treemap haleko  2026-10 pachi 2026-11 gardai aucha

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM"); // year month ma nikalney


        for (Expense expense : expenses) {
            String month = expense.getDate().format(formatter); //date bata year month 2026 -09 jasto
            double amount = expense.getAmount();

            if (monthlyTotals.containsKey(month)) { //yo paile bhaisakeko cha bhaney
                monthlyTotals.put(month, monthlyTotals.get(month) + amount
                );
            }//purano total ma jodney
                else {//pahilo patak yo month ayeko cha bhaney
                monthlyTotals.put(month, amount); //naya entry banauna

                }

            }
            return monthlyTotals;
        }

        public List<Expense> getExpensesByCategory(String category) {

        return expenseRepository.findByCategory(category); //repoko method call
        }

        public List<Expense> getExpensesByName(String name) {
        return expenseRepository.findByNameContainingIgnoreCase(name);
        }


        public  List<Expense> getExpensesByMonth(String month) {
            List<Expense> allExpenses = expenseRepository.findAll();
            List<Expense> filtered = new java.util.ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

            for (Expense expense : allExpenses) {
                String expenseMonth = expense.getDate().format(formatter); // expense ko month nikalcha
                if (expenseMonth.equals(month)) { // yo chai match bayo vane list ma rakhney
                    filtered.add(expense);

                }

            }
                return filtered;

            }

    public double getCurrentMonthTotal() {
        String currentMonth = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM"));

        return getMonthlyTotal().getOrDefault(currentMonth, 0.0);
    }

//yo chai day ko everyday
    public  List<Expense> getExpenseByDate(LocalDate date) {
        List<Expense> result = new java.util.ArrayList<>(); //khali list banayo
        for(Expense expense : expenseRepository.findAll()) {
            if(expense.getDate().toLocalDate().equals(date)) {
                result.add(expense); // tyo din ko bhayema list ma halney
            }
        }
        return result;
    }





        }