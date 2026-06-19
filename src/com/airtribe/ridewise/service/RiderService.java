package com.airtribe.ridewise.service;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.ridewise.model.Rider;

public class RiderService 
{
    List<Rider> allRiders = new ArrayList<>();
    int riderCount             = 1; 

    //Adding a new Rider
    public Rider registerRider(String name, String location)
    {
        String riderId = "R" + riderCount++;
        Rider rider = new Rider(riderId,name,location);
        allRiders.add(rider);
        return rider;
    }

    //Get rider by Id
    public Rider getRiderById(String riderId)
    {
        for(Rider rider : allRiders)
        {
            if(rider.getId().equals(riderId))
                return rider;
        }
        return null;
    }

    public List<Rider> getAllRiders()
    {
        return new ArrayList<>(allRiders);
    }

}
