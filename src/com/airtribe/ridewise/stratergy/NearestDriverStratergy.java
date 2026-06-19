package com.airtribe.ridewise.stratergy;

import java.util.List;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

/**
 * Concrete Strategy: Find the driver nearest to the rider

 * SOLID - LSP (Liskov Substitution Principle):
 * This implementation can replace RideMatchingStrategy
 */ 

public class NearestDriverStratergy implements RideMatchingStratergy
{
    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers) {
        if(availableDrivers.isEmpty() || availableDrivers == null)
            return null;

        Driver nearestDriver = availableDrivers.get(0);
        double minDistance   = calculateDistance();

        for(Driver d : availableDrivers)
        {
            double distance = calculateDistance();
            if(distance < minDistance)
            {
                minDistance     = distance;
                nearestDriver   = d;
            }
        }
        return nearestDriver;
    }

    private double calculateDistance()
    {
        //Generate random distance between 1-10km
        return 1 + Math.random() * 9;
    }
}
