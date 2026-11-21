package com.example.expensetracker.entity;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="expenses")
public class Expense {

  @Id  //primary key part ho
  @GeneratedValue(strategy = GenerationType.IDENTITY) //id automatic increase huncha

  private Long id;


  private String name;

  private double amount;

  private String category;
@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // browser ko datetime local le bujhney gari send garcha
  private LocalDateTime date;

  private String description;

  public Expense() { //jpa lai empty constructor

  }

public Expense(Double amount, String name ,String category, LocalDateTime date,String description) {
  this.name = name;
  this.amount = amount;
  this.category = category;
  this.date = date;
  this.description = description;

  }

  //Getter Setter banauney

    //id ko getter setter

    public Long getId() {
      return id;
    }

    public void setId(Long  id) {

      this.id = id;
    }

    //name ko getter setter

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  //amount ko getter setter

    public Double getAmount() {
      return amount;
    }
    public void setAmount(double amount) {
      this.amount =amount;
    }

  //category ko getter setter

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category =category;
    }

    //date ko getter setter
    public LocalDateTime getDate() {
      return date;
    }

    public void setDate(LocalDateTime date) {
      this.date= date;
    }

  //description ko getter setter

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description= description;
    }








}
