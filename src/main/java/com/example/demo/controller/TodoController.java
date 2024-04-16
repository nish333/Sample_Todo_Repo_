package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepo;
import com.example.demo.exception.ResourceNotFoundException;

@RestController
public class TodoController {
	
	@Autowired
	private TodoRepo todorepo;
	
	@GetMapping("/todo")
	public List<Todo> root() {
		return this.todorepo.findAll();
	}
	
	@GetMapping("/todo/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
		return this.todorepo.findById(id)
				.map(todo -> ResponseEntity.ok().body(todo))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/todo")
	public Todo createTodo(@RequestBody Todo todo) {
		return todorepo.save(todo);
	}
	
	@PutMapping("/todo/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable Long id , @RequestBody Todo todoDetails){
		Todo todo = todorepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo not exist in this id :" +id));
		todo.setId(todoDetails.getId());
		todo.setName(todoDetails.getName());
		todo.setTask(todoDetails.getTask());
		todo.setBranch(todoDetails.getBranch());
		
		Todo updateTodo=todorepo.save(todo);
		return ResponseEntity.ok(updateTodo);
	}
	
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<Object> deleteTodo(@PathVariable Long id){
		return this.todorepo.findById(id)
				.map(todo -> {
					this.todorepo.delete(todo);
					return ResponseEntity.ok().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}

}


























