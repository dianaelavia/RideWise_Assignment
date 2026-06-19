package com.airtribe.ridewise.service;


import java.util.ArrayList;
import java.util.List;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.RideStatus;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.stratergy.FareStratergy;
import com.airtribe.ridewise.stratergy.RideMatchingStratergy;

public class RideService 
{
    private List<Ride> allRides = new ArrayList<>();
    private List<FareReceipt> fareReceipts = new ArrayList<>();
    private int rideCounter = 1;

    private RideMatchingStratergy rideMatchStratergy;
    private FareStratergy fareStratergy;

    public RideService(RideMatchingStratergy rideMatchStratergy, FareStratergy fareStratergy)
    {
        this.rideMatchStratergy     = rideMatchStratergy;
        this.fareStratergy          = fareStratergy;
    }
    
    //Get Ride by ID
    public Ride getRideById(String rideId)
    {
        for(Ride r : allRides)
        {
            if(r.getId().equals(rideId))
                return r;
        }
        return null;
    }

    public List<Ride> getAllRides()
    {
        return new ArrayList<>(allRides);
    } 

    //Request a ride
    public Ride requestRide(Rider rider, double distance)
    {
        String riderId = "RIDE" + rideCounter++;
        Ride ride = new Ride(riderId, rider, distance);
        allRides.add(ride);
        return ride;
    }

    //Assign Ride to Driver
    public boolean assignDriver(String rideId, DriverService dservice)
    {
        //Ride by Id
        Ride ride = getRideById(rideId);
        // validate ride not null
        if(ride == null)    return false;
        // Validate status
        if(ride.getStatus() != RideStatus.REQUESTED)
            return false;
        
        //get all available drivers
        List<Driver> availableDrivers = dservice.getAvailableDrivers();
        
        // Find a driver - Stratergy
        Driver selectedDriver = rideMatchStratergy.findDriver(ride.getRider(), availableDrivers);
        
        // Validate not null
        if(selectedDriver == null)  return false;

        //Setdriver //setridestatus 
        ride.setDriver(selectedDriver);
        ride.setStatus(RideStatus.ASSIGNED);

        //change driver status
        selectedDriver.setAvailable(false);
        return true;
    }

    public boolean completeRide(String rideId, DriverService dService)
    {
        Ride ride = getRideById(rideId);
        //Validate ride
        if(ride == null)    return false;
        //Validate status > if not ASSIGNED
        if(ride.getStatus() != RideStatus.ASSIGNED) return false;
        
        //Fare startergy - Calculate fare
        double fare = fareStratergy.calculateFare(ride);

        //Add to receipts
        FareReceipt fareReceipt = new FareReceipt(rideId, fare);
        fareReceipts.add(fareReceipt);

        //Change ride Status and 
        ride.setStatus(RideStatus.COMPLETED);

        //Change Driver availability
        if(ride.getDriver() != null)
            ride.getDriver().setAvailable(true);

        return true;
    }

    public boolean cancelRide(String rideId)
    {
        Ride ride = getRideById(rideId);
        if(ride == null) return false;

        if(ride.getStatus() == RideStatus.COMPLETED || ride.getStatus() == RideStatus.CANCELLED)
            return false;

        ride.setStatus(RideStatus.CANCELLED);

        if(ride.getDriver() != null) ride.getDriver().setAvailable(true); 
        return true;
    }

    public FareReceipt getReceiptByRideId(String rideId)
    {
        for(FareReceipt fareReceipt : fareReceipts)
        {
            if(fareReceipt.getRideId().equals(rideId))
                return fareReceipt;
        }
        return null;
    }

}
