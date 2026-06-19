package com.airtribe.ridewise.service;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.VehicleType;

public class DriverService {
    
    List<Driver> allDrivers = new ArrayList<>();
    int driverCount         = 1;

    //Register Drivers 
    public Driver registerDriver(String name,String currentLocation, VehicleType vehicle)
    {
        String driverId = "D" + driverCount++;
        Driver driver = new Driver(driverId,name,currentLocation,vehicle);
        allDrivers.add(driver);
        return driver;
    }

    public Driver getDriverById(String driverId)
    {
        for(Driver d : allDrivers)
        {
            if(d.getId().equals(driverId))
                return d;
        }
        return null;
    }

    //Get list of all available drivers
    public List<Driver> getAvailableDrivers()
    {
        List<Driver> availableDrivers = new ArrayList<>();
        for(Driver d : allDrivers)
        {
            if(d.isAvailable())
                availableDrivers.add(d);
        }
        return availableDrivers;
    }

    //Update Driver's availability
    public void updateDriverAvailibilty(String driverId, boolean availStatus)
    {
        Driver d  = getDriverById(driverId);
        if(d != null)
            d.setAvailable(availStatus);
    }

    //Get list of ALL Drivers
    public List<Driver> getAllDrivers()
    {
        return new ArrayList<>(allDrivers);
    }
}
