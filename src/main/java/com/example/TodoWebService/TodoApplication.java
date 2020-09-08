package com.example.TodoWebService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
@CrossOrigin(origins="http://localhost:3000")
public class TodoApplication {
	
	private static final ServletUriComponentsBuilder ServletUriComponentBuilder = null;
	@Autowired
	private TodoHardcodedService todoService;
	
	@GetMapping(path="/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {
		// throws InterruptedException Thread.sleep(3000);
		return todoService.findAll();
	}
	
	//We need to do this for the express application to return an ID
	@GetMapping(path="/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
		// throws InterruptedException Thread.sleep(3000);
		return todoService.findById(id);
	}
	
	@DeleteMapping(path="/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
		Todo todo = todoService.deleteById(id);
		if(todo!=null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	//Edit/update a todo - Put req - /users/{username}/todos/{todo_id}
	@PutMapping("/users/{username}/todos/id")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, 
			@RequestBody Todo todo) {
		Todo todoUpdated = todoService.save(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}
	
	//Create a new todo - Post req - /users/{username}/todos/
	//For post request, we need to get the location and return the location of the created resource
	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Void> updateTodo(
			@PathVariable String username, @RequestBody Todo todo){
		Todo createdTodo = todoService.save(todo);
		//This facilitates to send the location in the URL. Get the id of the created resource
		//The build and expand method will get the createdTodo id and set it to the id of the path.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}

