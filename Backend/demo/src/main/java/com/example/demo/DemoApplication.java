package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@GetMapping
	public String hello() {
		return "hello";

		//get csv

		//turn csv to array list 

		//sort array list with quicksort

		//for loop across sorted array
			//if reserved bay is free at time of reservation
				//if reserved bay is free 
				//if free, add to list of free bays
			//if not free

	}

}
