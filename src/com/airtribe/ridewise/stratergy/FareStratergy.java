package com.airtribe.ridewise.stratergy;

import com.airtribe.ridewise.model.Ride;

/**
 * Strategy Interface for Fare Calculation
 * 
 * SOLID - DIP (Dependency Inversion Principle):
 * RideService depends on this interface, NOT concrete implementations
 * 
 * SOLID - ISP (Interface Segregation Principle):
 * Small focused interface with only fare calculation method
 */

public interface FareStratergy 
{
    double calculateFare(Ride ride);
}
