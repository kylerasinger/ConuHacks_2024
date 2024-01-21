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
		//get csv

		//turn csv to array list 

		//sort array list with quicksort
		// Remove the unreachable code
		int days = 42;
		int bays = 10;


		//initialize 10 int[720] per day 


		//for loop across sorted array
			//if reserved bay is free at time of reservation
				//if reserved bay is free every 30 minutes for X amount of times
					//add to the reserved list
			//if not free 
				//loop free bay 1 to 5
					//if free bay is free at time of reservation
						//if free bay is free every 30 minutes for X amount of times
							//add to the respective free bay

		return "hello";
	}

}
