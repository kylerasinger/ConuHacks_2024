
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
	

	public int mCompactCarCount = 0;
	public int mMediumCarCount = 0;
	public int mFullSizeCarCount = 0;
	public int mClass1TrucksCount = 0;
	public int mClass2TrucksCount = 0;
	public int mCompactCarCountMissed = 0;
	public int mMediumCarCountMissed = 0;
	public int mFullSizeCarCountMissed = 0;
	public int mClass1TrucksCountMissed = 0;
	public int mClass2TrucksCountMissed = 0;
	private int mTotalActualRevenue = 0;
	private int mTotalMissedRevenue = 0;
	private int mTotalActualVehicles = 0;
	private int mTotalMissedVehicles = 0;
	
	public VehicleController() {
		
	
	}

	
	public static List<Vehicle> data = new ArrayList(); 

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
    public List<Vehicle> getSortedVehicles() {
        // Sort the list based on DateOfRequest and RequestTime
        Collections.sort(data, Comparator.comparing(Vehicle::getDateOfRequest)
                                          .thenComparing(Vehicle::getRequestTime));

        return data;
    }
    
	@PostMapping("/create-schedule")
    public ResponseEntity<String> createSchedule() {
	for (Vehicle vehicle : getSortedVehicles()) {
        String classType = vehicle.getClassType();
        boolean isTurnedAway = true;

        switch (classType) {
            case "CompactCar":
                if (mCompactCarCount < 1) {
                    mCompactCarCount++;
                    isTurnedAway = false;
                }
                break;
            case "MediumCar":
                if (mMediumCarCount < 1) {
                    mMediumCarCount++;
                    isTurnedAway = false;
                }
                break;
            case "FullSizeCar":
                if (mFullSizeCarCount < 1) {
                    mFullSizeCarCount++;
                    isTurnedAway = false;
                }
                break;
            case "Class1Trucks":
                if (mClass1TrucksCount < 1) {
                    mClass1TrucksCount++;
                    isTurnedAway = false;
                }
                break;
            case "Class2Trucks":
                if (mClass2TrucksCount < 1) {
                    mClass2TrucksCount++;
                    isTurnedAway = false;
                }
                break;
            default:
                // Handle unknown class type
                break;
        }

        if (isTurnedAway) {
            mCompactCarCountMissed++;
            mMediumCarCountMissed++;
            mFullSizeCarCountMissed++;
            mClass1TrucksCountMissed++;
            mClass2TrucksCountMissed++;
        } else {
            mCompactCarCount++;
            mMediumCarCount++;
            mFullSizeCarCount++;
            mClass1TrucksCount++;
            mClass2TrucksCount++;
        }
    }

    mTotalActualRevenue = getTotalActualRevenue();
    mTotalMissedRevenue = getTotalMissedRevenue();
    mTotalActualVehicles = getTotalActualVehicles();
    mTotalMissedVehicles = getTotalMissedVehicles();
    

    return ResponseEntity.ok("Schedule created successfully. Serviced vehicles: " + mTotalActualVehicles +
                             ", Turned away vehicles: " + mTotalMissedVehicles +
                             ", Total Actual Revenue: " + mTotalActualRevenue +
                             ", Total Missed Revenue: " + mTotalMissedRevenue);
}
	

	public int getTotalActualRevenue() {
		
		return mCompactCarCount*150 + mMediumCarCount*150 + mFullSizeCarCount*150 + mClass1TrucksCount*250 + mClass2TrucksCount*700;
	}
	
	
	public int getTotalMissedRevenue() {
		
		return mCompactCarCountMissed*150 + mMediumCarCountMissed*150 + mFullSizeCarCountMissed*150 + mClass1TrucksCountMissed*250 + mClass2TrucksCountMissed*700;
	}
	
	public int getTotalActualVehicles() {
		
		return mCompactCarCount + mMediumCarCount + mFullSizeCarCount + mClass1TrucksCount + mClass2TrucksCount;
	}
	
	public int getTotalMissedVehicles() {
		
		return mCompactCarCountMissed + mMediumCarCountMissed + mFullSizeCarCountMissed + mClass1TrucksCountMissed + mClass2TrucksCountMissed;
	}
	
	
}



    
