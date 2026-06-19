package com.airtribe.ridewise.model;

public class Driver 
{
    //id name currr
    private String id;
    private String name;
    private String currentLocation;
    private boolean available;
    private VehicleType vehicleType;

    public Driver(String id, String name, String currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true;  //New drivers : Available by default
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCurrentLocation() {
        return currentLocation;
    }
    public boolean isAvailable() {
        return available;
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    @Override
    public String toString() {
        return "Driver [id=" + id + ", name=" + name + ", currentLocation=" + currentLocation + ", available="
                + available + ", vehicleType=" + vehicleType + "]";
    }
    
}
