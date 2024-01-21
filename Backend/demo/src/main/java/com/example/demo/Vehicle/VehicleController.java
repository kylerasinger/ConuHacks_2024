
package com.example.demo.Vehicle;

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
import java.util.Vector;

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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

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
         return ResponseEntity.ok("File uploaded successfully.");

    }
    
	@GetMapping
	String hello(){
		return "hello";
	}
//https://www.baeldung.com/java-merge-sort
	
}
/*
 public static void mergeSort(int[] a, int n) {
		if (n < 2) {
			return;
		}
		int mid = n / 2;
		int[] l = new int[mid];
		int[] r = new int[n - mid];
	
		for (int i = 0; i < mid; i++) {
			l[i] = a[i];
		}
		for (int i = mid; i < n; i++) {
			r[i - mid] = a[i];
		}
		mergeSort(l, mid);
		mergeSort(r, n - mid);
	
		merge(a, l, r, mid, n - mid);
	}
	public static void merge(int[] a, int[] l, int[] r, int left, int right) {
 
    	int i = 0, j = 0, k = 0;
    	while (i < left && j < right) {
        	if (l[i] <= r[j]) {
            	a[k++] = l[i++];
        	}
        	else {
            a[k++] = r[j++];
        	}
    	}
    	while (i < left) {
        	a[k++] = l[i++];
    	}
    	while (j < right) {
        	a[k++] = r[j++];
    	}
	}
 */
	
	


    
