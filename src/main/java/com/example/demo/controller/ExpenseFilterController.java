package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.dto.ExpenseFilterDTO;

@Controller
public class ExpenseFilterController {
	
	@GetMapping("/filterExpense")
	public String filterExpense(@ModelAttribute("filter") ExpenseFilterDTO expenseFilterDTO) {
		System.out.println("print the filter dto" + expenseFilterDTO);
		return "expenses-list";
	}

}
