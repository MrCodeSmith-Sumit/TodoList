package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

	@RestController
	@RequestMapping("/todos")
	 class GameController 
	{
		private List<Todo> todos = new ArrayList<>();
	    private AtomicLong idCounter = new AtomicLong();
	    @PostMapping
	    public Todo createTodo(@RequestBody Todo todo) {
	        todo.setId(idCounter.incrementAndGet());
	        todos.add(todo);
	        return todo;
	    }

	    @GetMapping
	    public List<Todo> getAllTodos() {
	        return todos;
	    }

	    @GetMapping("/{id}")
	    public Todo getTodo(@PathVariable long id) {
	        return todos.stream()
	                .filter(todo -> todo.getId() == id)
	                .findFirst()
	                .orElseThrow(() -> new RuntimeException("Todo not found"));
	    }

	    @PostMapping("/{id}/status")
	    public Todo updateTodoStatus(@PathVariable long id, @RequestBody String status) {
	        Todo todo = getTodo(id);
	        todo.setStatus(status);
	        return todo;
	    }

	    @PostMapping("/{id}/delete")
	    public String deleteTodo(@PathVariable long id) {
	        Todo todo = getTodo(id);
	        todos.remove(todo);
	        return "Todo deleted successfully";
	    }

	    @PostMapping("/reset")
	    public String resetTodos() {
	        todos.clear();
	        idCounter.set(0);
	        return "All todos have been cleared. The list is now empty.";
	    }
	}