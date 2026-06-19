package com.airtribe.ridewise.stratergy;

import com.airtribe.ridewise.model.Ride;

public class PeakHourFareStratergy implements FareStratergy
{

    private static final double RATE_PER_KM = 10;

    @Override
    public double calculateFare(Ride ride) 
    {
        return ride.getDistance() * RATE_PER_KM;
    }    
}
