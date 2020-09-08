
package com.example.HelloWorld;

public class HelloWorldBean {
	
	private String message;
	
	//constructor
	public HelloWorldBean(String message2) {
		this.message = message2;
	}
	
	//setters
	public void setMessage(String message) {
		this.message = message;
	}
	
	//getters
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return String.format("HelloWorldBean [message=%s]", message);
	}


	
	
	

	


}
