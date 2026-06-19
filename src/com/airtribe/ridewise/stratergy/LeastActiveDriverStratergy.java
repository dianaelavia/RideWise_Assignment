package com.airtribe.ridewise.stratergy;


import java.util.List;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

/**
 * Concrete Strategy: Find the driver with least rides completed
 * 
 * SOLID - LSP (Liskov Substitution Principle):
 * This implementation can replace RideMatchingStrategy
 */

public class LeastActiveDriverStratergy implements RideMatchingStratergy{

    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers) 
    {
        if(availableDrivers.isEmpty())
            return null;
        //Need to change this ?

        return availableDrivers.get(0);
    }
}
