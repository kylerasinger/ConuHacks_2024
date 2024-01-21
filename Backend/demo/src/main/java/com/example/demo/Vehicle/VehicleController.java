package com.example.demo.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VehicleController {
	
	@GetMapping
	public List<Vehicle> hello() {
		return List.of(
			new Vehicle(500,
			               "full-size",
			               LocalDate.of(1, 1, 1),
			               LocalTime.now(),
			               LocalDate.of(1, 1, 1),
			               LocalTime.now()
			               )
				);
	}
	
}
