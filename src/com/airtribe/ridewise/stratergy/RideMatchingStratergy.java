package com.airtribe.ridewise.stratergy;


import java.util.List;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

/**
 * Strategy Interface for Ride Matching
 * - RideService calls this method to find a driver.
 * - Adding a new strategy doesn't change RideService code

 * SOLID - DIP (Dependency Inversion Principle):
 * Services depend on this interface, NOT concrete implementations
*/
 
public interface RideMatchingStratergy
{
     /**
     * Find a suitable driver for the given rider
     * Param: rider - The rider requesting the ride
     * Param: availableDrivers - List of available drivers
     * Return: The selected driver, or null if no driver matches
     */

    Driver findDriver(Rider rider, List<Driver> availableDrivers);
}
