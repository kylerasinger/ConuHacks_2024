
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
import java.time.temporal.ChronoUnit;
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
	private int mTotalActualVehicles;
	private int mTotalMissedVehicles;
	
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
	
    public int convertToLocalDateToInt(LocalDate date) {
        // Define the start date (October 1st)
        LocalDate startDate = LocalDate.of(date.getYear(), 10, 1);

        // Calculate the number of days between the given date and October 1st
        long days = ChronoUnit.DAYS.between(startDate, date);

        // Ensure the result is within the range [0, 60]
        int result = (int) Math.min(Math.max(days, 0), 60);

        return result;
    }
    
    public static int convertToLocalTimeToInt(LocalTime time) {
        // Define the start time (7:00 AM)
        LocalTime startTime = LocalTime.of(7, 0);

        // Calculate the number of minutes between the given time and 7:00 AM
        long minutes = ChronoUnit.MINUTES.between(startTime, time);

        // Ensure the result is within the range [0, 719]
        int result = (int) Math.min(Math.max(minutes, 0), 719);

        return result;
    }
    
//<<<<<<< HEAD
	@GetMapping("/create-schedule")
    public ResponseEntity<ScheduleResponseDTO> createSchedule() {
	    
		int days = 61;
		int bays = 10;
		int minutes = 720;
		
	    //initialize triple array
		int[][][] schedule = new int[days][bays][minutes];
		
		for (Vehicle vehicle : getSortedVehicles()) {
        String classType = vehicle.getClassType();

        //translate requested date into day between 0 - 60
        int currentDate = convertToLocalDateToInt(vehicle.getDateOfService());
        //store in currentDate
        
        //translate requested time into minute between 0 - 719
        int currentMinute = convertToLocalTimeToInt(vehicle.getServiceTime());
        //store in currentMinute
        
        switch (classType) {
            case "CompactCar":
            	int reservedBay = 0;
            	//check bay 0 bc compactCar
            	boolean isEmpty;
            	boolean isEmptyBay;
            	
            	for(int i = currentMinute; i < currentMinute+30; i++) {
            		
            		if(schedule[currentDate][reservedBay][i] == 0) {
            			
            			isEmpty = true;
            		}
            		else
            			isEmpty = false;
            		 
            	}
            	
            	if(isEmpty = true) {
            		mCompactCarCount++;
            		
                	for(int i = currentMinute; i < currentMinute+30; i++) {
                		
                       schedule[currentDate][reservedBay][i] = 1; 
                		 
                	}
            	}
            	
            	else {
            		
            		int j = currentMinute;
            		
            		for(int i = 5; i < 10; i++) {
            			for( ; j < currentMinute+30; j++) {
                		
            				if(schedule[currentDate][j][i] == 0) {
            					
            					isEmptyBay = true;
            					
            				}
            				
            				else {
            					isEmptyBay = false;
            				}
            			}
                 	}
                	if(isEmpty = true) {
                		mCompactCarCount++;
                		
                    	for(int i = currentMinute; i < currentMinute+30; i++) {
                    		
                           schedule[currentDate][j][i] = 1; 
                    		 
                    	}
                	}
                	
                	else
                		mCompactCarCountMissed++;
            	}
                break;
            case "MediumCar":
            	int reservedBay1 = 1;
            	//check bay 1 bc compactCar
            	boolean isEmpty1;
            	boolean isEmptyBay1;
            	
            	for(int i = currentMinute; i < currentMinute+30; i++) {
            		
            		if(schedule[currentDate][reservedBay1][i] == 0) {
            			
            			isEmpty1 = true;
            		}
            		else
            			isEmpty1 = false;
            		 
            	}
            	
            	if(isEmpty1 = true) {
            		mMediumCarCount++;
            		
                	for(int i = currentMinute; i < currentMinute+30; i++) {
                		
                       schedule[currentDate][reservedBay1][i] = 1; 
                		 
                	}
            	}
            	
            	else {
            		
            		int j = currentMinute;
            		
            		for(int i = 5; i < 10; i++) {
            			for( ; j < currentMinute+30; j++) {
                		
            				if(schedule[currentDate][j][i] == 0) {
            					
            					isEmptyBay1 = true;
            					
            				}
            				
            				else {
            					isEmptyBay1 = false;
            				}
            			}
                 	}
                	if(isEmpty1 = true) {
                		mMediumCarCount++;
                		
                    	for(int i = currentMinute; i < currentMinute+30; i++) {
                    		
                           schedule[currentDate][j][i] = 1; 
                    		 
                    	}
                	}
                	
                	else
                		mMediumCarCountMissed++;
            	}
                break;
            case "FullSizeCar":
            	int reservedBay2 = 2;
            	//check bay 1 bc compactCar
            	boolean isEmpty2;
            	boolean isEmptyBay2;
            	
            	for(int i = currentMinute; i < currentMinute+30; i++) {
            		
            		if(schedule[currentDate][reservedBay2][i] == 0) {
            			
            			isEmpty2 = true;
            		}
            		else
            			isEmpty2 = false;
            		 
            	}
            	
            	if(isEmpty2 = true) {
            		mFullSizeCarCount++;
            		
                	for(int i = currentMinute; i < currentMinute+30; i++) {
                		
                       schedule[currentDate][reservedBay2][i] = 1; 
                		 
                	}
            	}
            	
            	else {
            		
            		int j = currentMinute;
            		
            		for(int i = 5; i < 10; i++) {
            			for( ; j < currentMinute+30; j++) {
                		
            				if(schedule[currentDate][j][i] == 0) {
            					
            					isEmptyBay2 = true;
            					
            				}
            				
            				else {
            					isEmptyBay2 = false;
            				}
            			}
                 	}
                	if(isEmpty2 = true) {
                		mFullSizeCarCount++;
                		
                    	for(int i = currentMinute; i < currentMinute+30; i++) {
                    		
                           schedule[currentDate][j][i] = 1; 
                    		 
                    	}
                	}
                	
                	else
                		mFullSizeCarCountMissed++;
            	}
                break;
            case "Class1Trucks":
            	int reservedBay3 = 3;
            	//check bay 1 bc compactCar
            	boolean isEmpty3;
            	boolean isEmptyBay3;
            	
            	for(int i = currentMinute; i < currentMinute+60; i++) {
            		
            		if(schedule[currentDate][reservedBay3][i] == 0) {
            			
            			isEmpty3 = true;
            		}
            		else
            			isEmpty3 = false;
            		 
            	}
            	
            	if(isEmpty3 = true) {
            		mClass1TrucksCount++;
            		
                	for(int i = currentMinute; i < currentMinute+60; i++) {
                		
                       schedule[currentDate][reservedBay3][i] = 1; 
                		 
                	}
            	}
            	
            	else {
            		
            		int j = currentMinute;
            		
            		for(int i = 5; i < 10; i++) {
            			for( ; j < currentMinute+60; j++) {
                		
            				if(schedule[currentDate][j][i] == 0) {
            					
            					isEmptyBay3 = true;
            					
            				}
            				
            				else {
            					isEmptyBay3 = false;
            				}
            			}
                 	}
                	if(isEmpty3 = true) {
                		mClass1TrucksCount++;
                		
                    	for(int i = currentMinute; i < currentMinute+60; i++) {
                    		
                           schedule[currentDate][j][i] = 1; 
                    		 
                    	}
                	}
                	
                	else
                		mClass1TrucksCountMissed++;
            	}
                break;
            case "Class2Trucks":
            	int reservedBay4 = 4;
            	//check bay 1 bc compactCar
            	boolean isEmpty4;
            	boolean isEmptyBay4;
            	
            	for(int i = currentMinute; i < currentMinute+120; i++) {
            		
            		if(schedule[currentDate][reservedBay4][i] == 0) {
            			
            			isEmpty4 = true;
            		}
            		else
            			isEmpty4 = false;
            		 
            	}
            	
            	if(isEmpty4 = true) {
            		setClass2TrucksCount(mClass2TrucksCount);
            		
                	for(int i = currentMinute; i < currentMinute+120; i++) {
                		
                       schedule[currentDate][reservedBay4][i] = 1; 
                		 
                	}
            	}
            	
            	else {
            		
            		int j = currentMinute;
            		
            		for(int i = 5; i < 10; i++) {
            			for( ; j < currentMinute+120; j++) {
                		
            				if(schedule[currentDate][j][i] == 0) {
            					
            					isEmptyBay4 = true;
            					
            				}
            				
            				else {
            					isEmptyBay4 = false;
            				}
            			}
                 	}
                	if(isEmpty4 = true) {
                		setClass2TrucksCount(mClass2TrucksCount);
                		
                    	for(int i = currentMinute; i < currentMinute+120; i++) {
                    		
                           schedule[currentDate][j][i] = 1; 
                    		 
                    	}
                	}
                	
                	else
                		setClass2TrucksCountMissed(mClass2TrucksCountMissed);
            	}
                break;
            default:
                // Handle unknown class type
                break;
        }
    }



    mTotalActualRevenue = getTotalActualRevenue();
    mTotalMissedRevenue = getTotalMissedRevenue();
    mTotalActualVehicles = getTotalActualVehicles();
    mTotalMissedVehicles = getTotalMissedVehicles();
    }

    // return ResponseEntity.ok("Schedule created successfully. Serviced vehicles: " + mTotalActualVehicles +
    //                          ", Turned away vehicles: " + mTotalMissedVehicles +
    //                          ", Total Actual Revenue: " + mTotalActualRevenue +
    //                          ", Total Missed Revenue: " + mTotalMissedRevenue);

    //JSON object to return
    ScheduleResponseDTO response = new ScheduleResponseDTO();
    response.setTotalActualVehicles(mTotalActualVehicles);
    response.setTotalMissedVehicles(mTotalMissedVehicles);
    response.setTotalActualRevenue(mTotalActualRevenue);
    response.setTotalMissedRevenue(mTotalMissedRevenue);

    return ResponseEntity.ok(response);                             
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

    public class ScheduleResponseDTO {
        private int totalActualVehicles;
        private int totalMissedVehicles;
        private double totalActualRevenue;
        private double totalMissedRevenue;
    
        // Default Constructor
        public ScheduleResponseDTO() {
        }
    
        // Constructor with all fields
        public ScheduleResponseDTO(int totalActualVehicles, int totalMissedVehicles, double totalActualRevenue, double totalMissedRevenue) {
            this.totalActualVehicles = totalActualVehicles;
            this.totalMissedVehicles = totalMissedVehicles;
            this.totalActualRevenue = totalActualRevenue;
            this.totalMissedRevenue = totalMissedRevenue;
        }
    
        // Getters and Setters
        public int getTotalActualVehicles() {
            return totalActualVehicles;
        }
    
        public void setTotalActualVehicles(int totalActualVehicles) {
            this.totalActualVehicles = totalActualVehicles;
        }
    
        public int getTotalMissedVehicles() {
            return totalMissedVehicles;
        }
    
        public void setTotalMissedVehicles(int totalMissedVehicles) {
            this.totalMissedVehicles = totalMissedVehicles;
        }
    
        public double getTotalActualRevenue() {
            return totalActualRevenue;
        }
    
        public void setTotalActualRevenue(double totalActualRevenue) {
            this.totalActualRevenue = totalActualRevenue;
        }
    
        public double getTotalMissedRevenue() {
            return totalMissedRevenue;
        }
    
        public void setTotalMissedRevenue(double totalMissedRevenue) {
            this.totalMissedRevenue = totalMissedRevenue;
        }
    }
    
    
	
	public void setClass2TrucksCount(int input) {
		
		input++; 
		mClass2TrucksCount = input;
	}
	
	public void setClass2TrucksCountMissed(int input) {
		
		input++; 
		mClass2TrucksCountMissed = input;
	}
	
	
}



    
