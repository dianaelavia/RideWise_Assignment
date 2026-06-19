# RideWise - Ride Sharing System

## Project Overview

RideWise is a Java-based ride-sharing system that manages the core operations of connecting riders with drivers, matching algorithms, and fare calculations. The system implements SOLID design principles and uses the Strategy pattern for flexible ride matching and fare calculation.

---

## Project Structure

```
RideWise/
├── src/
│   ├── App.java                                    # Legacy entry point
│   └── com/airtribe/ridewise/
│       ├── Main.java                               # Main application entry point
│       ├── ClassDiagram.png                        # Visual representation of system architecture
│       ├── model/                                  # Data Models
│       │   ├── Driver.java                         # Driver entity with availability tracking
│       │   ├── Rider.java                          # Rider entity with location
│       │   ├── Ride.java                           # Ride entity with status and details
│       │   ├── FareReceipt.java                    # Receipt for completed rides
│       │   ├── VehicleType.java                    # Enum: CAR, BIKE, AUTO
│       │   └── RideStatus.java                     # Enum: REQUESTED, ASSIGNED, COMPLETED, CANCELLED
│       ├── service/                                # Business Logic Layer
│       │   ├── RiderService.java                   # Manages rider registration and retrieval
│       │   ├── DriverService.java                  # Manages driver registration and availability
│       │   └── RideService.java                    # Core ride orchestration with strategy injection
│       ├── stratergy/                              # Strategy Pattern Implementations
│       │   ├── FareStratergy.java                  # Interface for fare calculation strategies
│       │   ├── RideMatchingStratergy.java          # Interface for driver matching strategies
│       │   ├── DefaultFareStratergy.java           # Base fare calculation (Rs 10/km with peak hour markup)
│       │   ├── PeakHourFareStratergy.java          # Alternative fare calculation
│       │   ├── NearestDriverStratergy.java         # Match rider with nearest available driver
│       │   └── LeastActiveDriverStratergy.java     # Match with least active driver
│       ├── exception/                              # Custom Exceptions (reserved for future use)
│       └── util/                                   # Utility Classes (reserved for future use)
├── bin/                                            # Compiled bytecode directory
└── lib/                                            # External libraries (if any)
```

---

## Architecture & Design Patterns

### 1. **Strategy Pattern**
- **Purpose**: Encapsulate different algorithms for ride matching and fare calculation
- **Implementation**:
  - `RideMatchingStratergy`: Abstracts driver selection algorithms
  - `FareStratergy`: Abstracts fare calculation algorithms

### 2. **Service Layer Pattern**
- Separates business logic from presentation
- Three core services:
  - `RiderService`: Manages rider lifecycle
  - `DriverService`: Manages driver lifecycle and availability
  - `RideService`: Orchestrates ride requests, assignment, and completion

### 3. **SOLID Principles**
- **DIP (Dependency Inversion Principle)**: Services depend on interfaces, not concrete implementations
- **ISP (Interface Segregation Principle)**: Focused, small interfaces for strategies
- **LSP (Liskov Substitution Principle)**: All strategy implementations are interchangeable

---

## Key Components

### Model Layer
| Class | Responsibility |
|-------|-----------------|
| `Driver` | Represents a driver with ID, name, location, vehicle type, and availability status |
| `Rider` | Represents a rider with ID, name, and current location |
| `Ride` | Represents a ride request with rider, driver, distance, and status |
| `FareReceipt` | Generates and stores fare details for completed rides |
| `VehicleType` | Enum defining vehicle types: CAR, BIKE, AUTO |
| `RideStatus` | Enum tracking ride lifecycle: REQUESTED, ASSIGNED, COMPLETED, CANCELLED |

### Service Layer
| Service | Responsibility |
|---------|-----------------|
| `RiderService` | Register riders, retrieve rider information |
| `DriverService` | Register drivers, manage availability, find available drivers |
| `RideService` | Request rides, assign drivers using matching strategy, calculate fares using fare strategy |

### Strategy Layer
| Strategy | Purpose |
|----------|---------|
| `NearestDriverStratergy` | Find the geographically nearest driver |
| `LeastActiveDriverStratergy` | Find the driver with least completed rides |
| `DefaultFareStratergy` | Calculate fare at Rs 10/km with 1.5x peak hour multiplier |
| `PeakHourFareStratergy` | Calculate fare at standard Rs 10/km rate |

---

## Features

### Rider Features
- ✅ Register as a rider with name and location
- ✅ Request a ride with specified distance
- ✅ Receive assigned driver information
- ✅ Complete rides and receive fare receipt
- ✅ View all past rides and receipts

### Driver Features
- ✅ Register as a driver with vehicle type
- ✅ Set current location
- ✅ Receive ride requests
- ✅ Accept/complete rides
- ✅ View availability status

### System Features
- ✅ Dynamic driver matching using pluggable strategies
- ✅ Flexible fare calculation with multiple algorithms
- ✅ Ride status tracking throughout lifecycle
- ✅ Driver availability management
- ✅ Fare receipt generation

---

## Main Menu Operations

The system provides an interactive command-line interface with the following options:

1. **Add Rider** - Register a new rider
2. **Add Driver** - Register a new driver with vehicle type selection
3. **View Available Drivers** - List all available drivers
4. **Request Ride** - Create a ride request
5. **Complete Ride** - Mark a ride as completed
6. **View All Rides** - Display all ride records
7. **Exit** - Close the application

---

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Compiler: `javac`

### Compilation
```bash
cd RideWise
javac -d bin src/com/airtribe/ridewise/*.java
javac -d bin src/com/airtribe/ridewise/model/*.java
javac -d bin src/com/airtribe/ridewise/service/*.java
javac -d bin src/com/airtribe/ridewise/stratergy/*.java
```

### Execution
```bash
java -cp bin com.airtribe.ridewise.Main
```

---

## System Architecture Diagram

For a visual representation of the system architecture, class relationships, and design patterns, refer to:

**📊 Class Diagram:** [`src/com/airtribe/ridewise/ClassDiagram.png`](src/com/airtribe/ridewise/ClassDiagram.png)

---

## Workflow Example

### 1. Rider Registration
```
User selects "Add Rider"
→ RiderService.registerRider(name, location)
→ New Rider object created with unique ID (R1, R2, ...)
```

### 2. Driver Registration
```
User selects "Add Driver"
→ DriverService.registerDriver(name, location, vehicleType)
→ New Driver object created with unique ID (D1, D2, ...)
→ Driver marked as available by default
```

### 3. Ride Request
```
User selects "Request Ride"
→ RideService.requestRide(rider, distance)
→ New Ride created with status: REQUESTED
→ Ride awaiting driver assignment
```

### 4. Driver Assignment
```
RideService.assignDriver(rideId, driverService)
→ Uses injected RideMatchingStratergy (e.g., NearestDriverStratergy)
→ Finds suitable driver from available drivers
→ Ride status updated to ASSIGNED
→ Driver marked as unavailable
```

### 5. Fare Calculation
```
RideService.completeRide(rideId)
→ Uses injected FareStratergy (e.g., DefaultFareStratergy)
→ Calculates fare based on distance and algorithm
→ Generates FareReceipt
→ Ride status updated to COMPLETED
```

---

## Future Enhancements

- [ ] Database integration for persistent storage
- [ ] Rating and review system
- [ ] Payment gateway integration
- [ ] Real-time GPS tracking
- [ ] Advanced matching algorithms
- [ ] Surge pricing during peak hours
- [ ] Promotional codes and discounts
- [ ] User authentication and authorization
- [ ] API endpoints for mobile apps

---

## Code Quality

- **Design Patterns**: Strategy Pattern, Service Layer Pattern
- **SOLID Principles**: DIP, ISP, LSP
- **Extensibility**: Easy to add new strategies without modifying existing code
- **Maintainability**: Clear separation of concerns with layered architecture

---

## Author

Airtribe Assignment - Ride Sharing System

---

## License

This project is part of the Airtribe assignment series.
