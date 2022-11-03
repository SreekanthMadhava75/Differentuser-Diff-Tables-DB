package com.example.demo.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ExpenseDTO;
import com.example.demo.dto.ExpenseFilterDTO;
import com.example.demo.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExpenseController {
	
	private final ExpenseService expenseService;
	
	@GetMapping("/expenses")
	public String showExpenseList(Model model) {
		model.addAttribute("expenses", expenseService.getAllExpenses());
		model.addAttribute("filter", new ExpenseFilterDTO());
		return "expenses-list";
	}
	
	@GetMapping("/createExpense")
	public String showExpenseForm(Model model) {
		model.addAttribute("expense", new ExpenseDTO());
		return "expense-form";
	}
	
	@PostMapping("/saveOrUpdateExpense")
	public String saveOrUpdateExpenseDetails(@ModelAttribute("expense") ExpenseDTO expneseDTO) throws ParseException {
		System.out.println("Printing the Expense DTO: "+expneseDTO);
		expenseService.saveExpenseDetails(expneseDTO);
		return "redirect:/expenses";
	}
	
	@GetMapping("/deleteExpense")
	public String deleteExpense(@RequestParam String id) {
		System.out.println("printing the expense id" + id);
		expenseService.deleteExpense(id);
		return "redirect:/expenses";
	}
	
	@GetMapping("/updateExpense")
	public String updateExpense(@RequestParam String id, Model model) {
		System.out.println("update" + id);		
		ExpenseDTO expense = expenseService.getExpenseById(id);
		model.addAttribute("expense", expense);
		return "expense-form";
	}
}

 