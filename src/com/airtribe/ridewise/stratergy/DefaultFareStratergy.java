package com.airtribe.ridewise.stratergy;

import com.airtribe.ridewise.model.Ride;

/**
 * Concrete Strategy: Calculate fare based on distance only
 * Formula: distance * 10 (Rs 10 per km)
*/


public class DefaultFareStratergy implements FareStratergy
{
    private static final double RATE_PER_KM = 10;
    private static final double PEAK_HOUR_RATE = 1.5;

    @Override
    public double calculateFare(Ride ride) 
    {
        double baseFare = ride.getDistance() * RATE_PER_KM;
        return baseFare * PEAK_HOUR_RATE;
    }
    
}
