package com.example.demo.service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ExpenseDTO;
import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.util.DateTimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
	
	private final ExpenseRepository expenseRepository;
	private final ModelMapper modelMapper;
	private final UserService userService;
	
	
	public List<ExpenseDTO> getAllExpenses() {
		//List<Expense> list = expenseRepository.findAll();
		User user = userService.getLoggedInUser();
		List<Expense> list = expenseRepository.findByUserId(user.getId());
		List<ExpenseDTO> expenseList = list.stream().map(this::mapToDTO).collect(Collectors.toList());
		return expenseList;
	}
	
	private ExpenseDTO mapToDTO(Expense expense) {
		ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class);
		expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getDate()));
		return expenseDTO;
	}
	
	

	public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException {
		//map the DTO to entity
		Expense expense = mapToEntity(expenseDTO);
		// add the logged in user to the expense entity
		expense.setUser(userService.getLoggedInUser());
		//Save the entity to database
		expense = expenseRepository.save(expense);
		//map the entity to DTO
		return mapToDTO(expense);
	}

	private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException {
		//map the DTO to entity
		Expense expense = modelMapper.map(expenseDTO, Expense.class);
		//TODO: generate the expense id
		if (expense.getId() == null) {
			expense.setExpenseId(UUID.randomUUID().toString());
		}
		
		//expense.setExpenseId(UUID.randomUUID().toString());
		//TODO: set the expense date
		expense.setDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
		//return the expense entity
		return expense;
	}
	
	public void deleteExpense(String id) {
		Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(() -> new RuntimeException("not found id" + id));
		expenseRepository.delete(existingExpense);
	}
	
	
	public ExpenseDTO getExpenseById(String id) {
		Expense existingExpense = expenseRepository.findByExpenseId(id).orElseThrow(() -> new RuntimeException("not found id" + id));
		return mapToDTO(existingExpense);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}