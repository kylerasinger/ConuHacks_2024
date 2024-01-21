
package com.example.demo.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.Comparator;

import javax.sql.rowset.spi.SyncFactory;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;


//import scala.collection.immutable.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="api/v1/vehicle")
public class VehicleController {
	

	public int mCompactCarCount;
	public int mMediumCarCount;
	public int mFullSizeCarCount;
	public int mClass1TrucksCount;
	public int mClass2TrucksCount;
	public int mCompactCarCountMissed;
	public int mMediumCarCountMissed;
	public int mFullSizeCarCountMissed;
	public int mClass1TrucksCountMissed;
	public int mClass2TrucksCountMissed;
	private int mTotalActualRevenue;
	private int mTotalMissedRevenue;
	
	public VehicleController() {
		
		this.mCompactCarCount = 0;
		this.mMediumCarCount = 0;
		this.mFullSizeCarCount = 0;
		this.mClass1TrucksCount = 0;
		this.mClass2TrucksCount = 0;
		this.mCompactCarCountMissed = 0;
		this.mMediumCarCountMissed = 0;
		this.mFullSizeCarCountMissed = 0;
		this.mClass1TrucksCountMissed = 0;
		this.mClass2TrucksCountMissed = 0;
		
	}

	
	public List<Vehicle> data = new ArrayList(); 

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("datafile") MultipartFile file){
        

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //data.add(line);
                    String[] parts = line.split(",");	
                    String dateofrequest = parts[0];
                    String dateofservice = parts[1];
                    String classtype = parts[2];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    LocalDateTime DateOfRequest = LocalDateTime.parse(dateofrequest, formatter);
                    LocalDateTime DateOfService = LocalDateTime.parse(dateofservice, formatter);
					


					LocalTime timeofrequest = DateOfRequest.toLocalTime();
					LocalTime timeofservice= DateOfService.toLocalTime();
 					LocalDate dateofRequest = DateOfRequest.toLocalDate();
        			LocalDate dateofService = DateOfService.toLocalDate();
	
         data.add(new Vehicle(classtype, dateofRequest, timeofrequest, dateofService, timeofservice));
                }
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
                return ResponseEntity.badRequest().body("Error processing the file.");
            }
         // Process the data or return a response as needed
		// Collections.sort(data, Comparator.comparing(Vehicle::getDateOfRequest).thenComparing(Vehicle::getRequestTime));

         return ResponseEntity.ok("File uploaded successfully.");

    }
	
	@GetMapping("/sorted-vehicles")
    public ResponseEntity<List<Vehicle>> getSortedVehicles() {
        // Sort the list based on DateOfRequest and RequestTime
        Collections.sort(data, Comparator.comparing(Vehicle::getDateOfRequest)
                                          .thenComparing(Vehicle::getRequestTime));

        // Return the sorted data in the response body
        return ResponseEntity.ok(data);
    }
    
	@GetMapping
	String hello(){
		return "hello";

	}
	
	public void CreateSchedule(ArrayList<Vehicle> iSortedVehicle) {
		
		
	}
	

	public int getTotalActualRevenue() {
		
		mTotalActualRevenue = mCompactCarCount*150 + mMediumCarCount*150 + mFullSizeCarCount*150 + mClass1TrucksCount*250 + mClass2TrucksCount*700;
		return mTotalActualRevenue;
	}
	
	
	public int getTotalMissedRevenue() {
		
		mTotalMissedRevenue = mCompactCarCountMissed*150 + mMediumCarCountMissed*150 + mFullSizeCarCountMissed*150 + mClass1TrucksCountMissed*250 + mClass2TrucksCountMissed*700;
		return mTotalMissedRevenue;
	}
	
	
}



    
