package com.airtribe.ridewise;

import java.util.List;
import java.util.Scanner;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.stratergy.DefaultFareStratergy;
import com.airtribe.ridewise.stratergy.NearestDriverStratergy;

public class Main 
{

    private static RiderService riderService    = new RiderService();
    private static DriverService driverService  = new DriverService();
    private static RideService rideService      = new RideService(
                                                    new NearestDriverStratergy(), 
                                                    new DefaultFareStratergy());
    private static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addRider();
                    break;
                case 2:
                    addDriver();
                    break;
                case 3:
                    viewAvailableDrivers();
                    break;
                case 4:
                    requestRide();
                    break;
                case 5:
                    completeRide();
                    break;
                case 6:
                    viewAllRides();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using Ride Sharing System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== RIDE SHARING SYSTEM ===");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View All Rides");
        System.out.println("7. Exit");
    }

    private static void addRider() {
        System.out.print("Enter rider name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter rider location: ");
        String location = scanner.nextLine();
        
        Rider rider = riderService.registerRider(name, location);
        System.out.println("!! Rider registered successfully: " + rider);
    }

    private static void addDriver() {
        System.out.print("Enter driver name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter driver location: ");
        String location = scanner.nextLine();
        
        System.out.println("Select vehicle type:");
        System.out.println("1. BIKE");
        System.out.println("2. AUTO");
        System.out.println("3. CAR");
        int vehicleChoice = getIntInput("Choose: ");
        
        VehicleType vehicleType = VehicleType.BIKE; // Default
        if (vehicleChoice == 2) {
            vehicleType = VehicleType.AUTO;
        } else if (vehicleChoice == 3) {
            vehicleType = VehicleType.CAR;
        }
        
        Driver driver = driverService.registerDriver(name, location, vehicleType);
        System.out.println("!! Driver registered successfully: " + driver);
    }

    private static void viewAvailableDrivers() {
        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        
        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers available at the moment.");
            return;
        }
        
        System.out.println("\n=== AVAILABLE DRIVERS ===");
        for (Driver driver : availableDrivers) {
            System.out.println(driver);
        }
    }
    private static void requestRide() {
        System.out.print("Enter rider ID: ");
        String riderId = scanner.nextLine();
        
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            System.out.println("!! Rider not found.");
            return;
        }
        
        System.out.print("Enter distance (in km): ");
        double distance = getDoubleInput();
        
        // Create ride
        Ride ride = rideService.requestRide(rider, distance);
        System.out.println("!! Ride requested: " + ride);
        
        // Try to assign driver
        if (rideService.assignDriver(ride.getId(), driverService)) {
            System.out.println("✓ Driver assigned: " + ride.getDriver().getName());
        } else {
            System.out.println("!!! Could not assign a driver at the moment.");
        }
    }

    private static void completeRide() {
        System.out.print("Enter ride ID: ");
        String rideId = scanner.nextLine();
        
        Ride ride = rideService.getRideById(rideId);
        if (ride == null) {
            System.out.println("✗ Ride not found.");
            return;
        }
        
        if (rideService.completeRide(rideId, driverService)) {
            FareReceipt receipt = rideService.getReceiptByRideId(rideId);
            System.out.println("✓ Ride completed!");
            System.out.println("Fare: Rs " + receipt.getAmount());
        } else {
            System.out.println("✗ Could not complete this ride.");
        }
    }
    private static void viewAllRides() {
        List<Ride> allRides = rideService.getAllRides();
        
        if (allRides.isEmpty()) {
            System.out.println("No rides found.");
            return;
        }
        
        System.out.println("\n=== ALL RIDES ===");
        for (Ride ride : allRides) {
            System.out.println(ride);
        }
    }

     private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
    
    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid distance.");
            return 0;
        }
    }

}
