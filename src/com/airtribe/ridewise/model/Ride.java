package com.airtribe.ridewise.model;

public class Ride 
{
    private String id;
    private Driver driver;
    private Rider rider;
    private double distance;
    private RideStatus status;

    public Ride(String id, Rider rider, double distance) {
        this.id         = id;
        this.rider      = rider;
        this.distance   = distance;
        this.status     = RideStatus.REQUESTED;
        this.driver     = null;   //When ride is created initially driver is not assigned 
    }

    public String getId() {
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public Rider getRider() {
        return rider;
    }

    public double getDistance() {
        return distance;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ride [id=" + id + ", driver=" + (driver != null ? driver.getName() : "Unassigned") 
        + ", rider=" + rider.getName() + ", distance=" + distance + ", status="
                + status + "]";
    }     
}
