package com.example.demo.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
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
