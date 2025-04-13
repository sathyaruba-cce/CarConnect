package main;
import dao.*;
import entity.*;
import exception.*;

import java.util.*;
public class MainModule {
    public static void main(String[] args) throws UserNotFoundException {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        AuthenticationService authService = null;
		try {
			authService = new AuthenticationService();
		} catch (DatabaseConnectionException e) {
			
			e.printStackTrace();
		}
        System.out.print("Enter Username: ");
        String username = scan.nextLine();
        System.out.print("Enter Password: ");
        String password = scan.nextLine();
        authService.authenticateUser(username, password);
        
        while (!exit) {
            System.out.println("\n========== Entity Selection Menu ==========");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Vehicle");
            System.out.println("4. Reservation");
            System.out.println("5.  Generate Reports");
            System.out.println("6. Exit");
           
            System.out.print("Choose an entity to manage(give option): ");
            int entityChoice = scan.nextInt();

            switch (entityChoice) {
                case 1 -> {
                    try {
                        CustomerService service = new CustomerService();

                        System.out.println("1. RegisterCustomer");
                        System.out.println("2. FindCustomerById");
                        System.out.println("3. FindCustomerByUsername");
                        System.out.println("4. UpdateCustomer");
                        System.out.println("5. DeleteCustomer");
                        System.out.print("Option Please:\t");

                        int key = scan.nextInt();

                        switch (key) {
                            case 1 -> {
                                Customer customer = new Customer();
                                System.out.println("Enter customer details to register.");

                                System.out.print("Enter Customer ID: ");
                                customer.setCustomerID(scan.nextInt());
                                scan.nextLine();

                                System.out.print("Enter First Name: ");
                                customer.setFirstName(scan.nextLine());

                                System.out.print("Enter Last Name: ");
                                customer.setLastName(scan.nextLine());

                                System.out.print("Enter Email: ");
                                customer.setEmail(scan.nextLine());

                                System.out.print("Enter Phone Number: ");
                                customer.setPhoneNumber(scan.nextLine());

                                System.out.print("Enter Address: ");
                                customer.setAddress(scan.nextLine());

                                System.out.print("Enter Username: ");
                                customer.setUsername(scan.nextLine());

                                System.out.print("Enter Password: ");
                                customer.setPassword(scan.nextLine());

                                customer.setRegistrationDate(new java.util.Date());

                                try {
                                    service.registerCustomer(customer);
                                    System.out.println("Customer registered successfully!");
                                } catch (InvalidInputException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 2 -> {
                                try {
                                    System.out.print("Enter customer ID to find:\t");
                                    int customerId = scan.nextInt();
                                    Customer customer = service.getCustomerById(customerId);
                                    System.out.println(customer);
                                } catch (InputMismatchException ime) {
                                    System.out.println("Invalid input! Please enter a valid customer ID.");
                                } catch (InvalidInputException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 3 -> {
                                try {
                                    System.out.print("Enter username to find:\t");
                                    String username1 = scan.next();
                                    Customer customer = service.getCustomerByUsername(username1);
                                    System.out.println(customer);
                                } catch (AuthenticationException e) {
                                    System.out.println("Authentication failed: " + e.getMessage());
                                }
                            }
                            case 4 -> {
                                try {
                                    System.out.print("Enter customer ID to update:\t");
                                    int customerId = scan.nextInt();
                                    Customer customer = service.getCustomerById(customerId);
                                    scan.nextLine();

                                    System.out.println("Which field would you like to update?");
                                    System.out.println("1. First Name");
                                    System.out.println("2. Last Name");
                                    System.out.println("3. Email");
                                    System.out.println("4. Phone Number");
                                    System.out.println("5. Address");
                                    System.out.println("6. Username");
                                    System.out.println("7. Password");
                                    System.out.print("Enter option (1-7): ");
                                    int fieldOption = scan.nextInt();
                                    scan.nextLine();

                                    switch (fieldOption) {
                                        case 1 -> {
                                            System.out.println("Current First Name: " + customer.getFirstName());
                                            System.out.print("Enter new First Name: ");
                                            customer.setFirstName(scan.nextLine());
                                        }
                                        case 2 -> {
                                            System.out.println("Current Last Name: " + customer.getLastName());
                                            System.out.print("Enter new Last Name: ");
                                            customer.setLastName(scan.nextLine());
                                        }
                                        case 3 -> {
                                            System.out.println("Current Email: " + customer.getEmail());
                                            System.out.print("Enter new Email: ");
                                            customer.setEmail(scan.nextLine());
                                        }
                                        case 4 -> {
                                            System.out.println("Current Phone Number: " + customer.getPhoneNumber());
                                            System.out.print("Enter new Phone Number: ");
                                            customer.setPhoneNumber(scan.nextLine());
                                        }
                                        case 5 -> {
                                            System.out.println("Current Address: " + customer.getAddress());
                                            System.out.print("Enter new Address: ");
                                            customer.setAddress(scan.nextLine());
                                        }
                                        case 6 -> {
                                            System.out.println("Current Username: " + customer.getUsername());
                                            System.out.print("Enter new Username: ");
                                            customer.setUsername(scan.nextLine());
                                        }
                                        case 7 -> {
                                            System.out.println("Current Password: " + customer.getPassword());
                                            System.out.print("Enter new Password: ");
                                            customer.setPassword(scan.nextLine());
                                        }
                                        default -> System.out.println("Invalid option.");
                                    }

                                    service.updateCustomer(customer);
                                    System.out.println("Customer updated successfully in the database!");
                                } catch (InvalidInputException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 5 -> {
                                try {
                                    System.out.print("Enter customer ID to delete:\t");
                                    int customerId = scan.nextInt();
                                    service.deleteCustomer(customerId);
                                    System.out.println("Customer deleted successfully!");
                                } catch (InvalidInputException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            default -> System.out.println("Invalid option: " + key);
                        }

                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database connection error: " + e.getMessage());
                    }
                }

                case 2 -> {
                    try {
                        AdminService service = new AdminService();

                        System.out.println("1. RegisterAdmin");
                        System.out.println("2. FindAdminById");
                        System.out.println("3. FindAdminByUsername");
                        System.out.println("4. UpdateAdmin");
                        System.out.println("5. DeleteAdmin");
                        System.out.print("Option Please:\t");

                        int key = scan.nextInt();

                        switch (key) {
                            case 1 -> {
                                Admin admin = new Admin();
                                System.out.println("Enter admin details to register.");

                                System.out.print("Enter Admin ID: ");
                                admin.setAdminID(scan.nextInt());
                                scan.nextLine();

                                System.out.print("Enter First Name: ");
                                admin.setFirstName(scan.nextLine());

                                System.out.print("Enter Last Name: ");
                                admin.setLastName(scan.nextLine());

                                System.out.print("Enter Email: ");
                                admin.setEmail(scan.nextLine());

                                System.out.print("Enter Phone Number: ");
                                admin.setPhoneNumber(scan.nextLine());

                                System.out.print("Enter Username: ");
                                admin.setUsername(scan.nextLine());

                                System.out.print("Enter Password: ");
                                admin.setPassword(scan.nextLine());

                                System.out.print("Enter Role: ");
                                admin.setRole(scan.nextLine());

                                admin.setJoinDate(new java.util.Date());

                                try {
                                    service.registerAdmin(admin);
                                    System.out.println("Admin registered successfully!");
                                } catch (InvalidInputException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 2 -> {
                                try {
                                    System.out.print("Enter admin ID to find:\t");
                                    int adminId = scan.nextInt();
                                    Admin admin = service.getAdminById(adminId);
                                    System.out.println(admin);
                                } catch (InputMismatchException ime) {
                                    System.out.println("Invalid input! Please enter a valid admin ID.");
                                } catch (AdminNotFoundException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 3 -> {
                                try {
                                    System.out.print("Enter username to find:\t");
                                    String username1 = scan.next();
                                    Admin admin = service.getAdminByUsername(username1);
                                    System.out.println(admin);
                                } catch (AdminNotFoundException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 4 -> {
                                try {
                                    System.out.print("Enter admin ID to update:\t");
                                    int adminId = scan.nextInt();
                                    Admin admin = service.getAdminById(adminId);

                                    System.out.println("What would you like to update?");
                                    System.out.println("1. First Name");
                                    System.out.println("2. Last Name");
                                    System.out.println("3. Email");
                                    System.out.println("4. Phone Number");
                                    System.out.println("5. Username");
                                    System.out.println("6. Password");
                                    System.out.println("7. Role");
                                    System.out.print("Choose an option to update:\t");

                                    int updateChoice = scan.nextInt();
                                    scan.nextLine();

                                    switch (updateChoice) {
                                        case 1 -> {
                                            System.out.print("Enter new First Name: ");
                                            admin.setFirstName(scan.nextLine());
                                        }
                                        case 2 -> {
                                            System.out.print("Enter new Last Name: ");
                                            admin.setLastName(scan.nextLine());
                                        }
                                        case 3 -> {
                                            System.out.print("Enter new Email: ");
                                            admin.setEmail(scan.nextLine());
                                        }
                                        case 4 -> {
                                            System.out.print("Enter new Phone Number: ");
                                            admin.setPhoneNumber(scan.nextLine());
                                        }
                                        case 5 -> {
                                            System.out.print("Enter new Username: ");
                                            admin.setUsername(scan.nextLine());
                                        }
                                        case 6 -> {
                                            System.out.print("Enter new Password: ");
                                            admin.setPassword(scan.nextLine());
                                        }
                                        case 7 -> {
                                            System.out.print("Enter new Role: ");
                                            admin.setRole(scan.nextLine());
                                        }
                                        default -> System.out.println("Invalid option.");
                                    }

                                    service.updateAdmin(admin);
                                    System.out.println("Admin updated successfully!");
                                } catch (AdminNotFoundException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            case 5 -> {
                                try {
                                    System.out.print("Enter admin ID to delete:\t");
                                    int adminId = scan.nextInt();
                                    service.deleteAdmin(adminId);
                                    System.out.println("Admin deleted successfully!");
                                } catch (AdminNotFoundException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                            default -> System.out.println("Invalid option: " + key);
                        }

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 ->{
                	try {
                        
                        VehicleService service = new VehicleService();

                        
                        while (true) {
                            System.out.println("1. Add Vehicle");
                            System.out.println("2. Find Vehicle By ID");
                            System.out.println("3. Update Vehicle");
                            System.out.println("4. Remove Vehicle");
                            System.out.println("5. Show Available Vehicles");
                            System.out.println("6. Exit");
                            System.out.print("Option Please: ");
                            
                            int key = scan.nextInt();
                            scan.nextLine();  

                            switch (key) {
                                case 1 -> {  
                                    Vehicle vehicle = new Vehicle();
                                    System.out.println("Enter vehicle details to register.");

                                    System.out.print("Enter Vehicle ID: ");
                                    vehicle.setVehicleID(scan.nextInt());
                                    scan.nextLine();  // Consume newline

                                    System.out.print("Enter Model: ");
                                    vehicle.setModel(scan.nextLine());

                                    System.out.print("Enter Make: ");
                                    vehicle.setMake(scan.nextLine());

                                    System.out.print("Enter Year: ");
                                    vehicle.setYear(scan.nextInt());
                                    scan.nextLine();  // Consume newline

                                    System.out.print("Enter Color: ");
                                    vehicle.setColor(scan.nextLine());

                                    System.out.print("Enter Registration Number: ");
                                    vehicle.setRegistrationNumber(scan.nextLine());

                                    System.out.print("Is the Vehicle Available? (true/false): ");
                                    vehicle.setAvailability(scan.nextBoolean());

                                    System.out.print("Enter Daily Rate: ");
                                    vehicle.setDailyRate(scan.nextDouble());

                                    try {
                                        if (service.addVehicle(vehicle)) {
                                            System.out.println("Vehicle added successfully!");
                                        } else {
                                            System.out.println("Error: Unable to add vehicle.");
                                        }
                                    } catch (InvalidInputException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case 2 -> { 
                                    try {
                                        System.out.print("Enter Vehicle ID to find: ");
                                        int vehicleId = scan.nextInt();
                                        Vehicle vehicle = service.getVehicleById(vehicleId);
                                        System.out.println(vehicle); // Prints the vehicle details
                                    } catch (InputMismatchException ime) {
                                        System.out.println("Invalid input! Please enter a valid vehicle ID.");
                                    } catch (VehicleNotFoundException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case 3 -> {  
                                    try {
                                        System.out.print("Enter Vehicle ID to update: ");
                                        int vehicleId = scan.nextInt();
                                        Vehicle vehicle = service.getVehicleById(vehicleId);

                                        System.out.println("What would you like to update?");
                                        System.out.println("1. Model");
                                        System.out.println("2. Make");
                                        System.out.println("3. Year");
                                        System.out.println("4. Color");
                                        System.out.println("5. Registration Number");
                                        System.out.println("6. Availability");
                                        System.out.println("7. Daily Rate");
                                        System.out.print("Choose an option to update: ");
                                        int updateChoice = scan.nextInt();
                                        scan.nextLine();  

                                        switch (updateChoice) {
                                            case 1 -> {
                                                System.out.print("Enter new Model: ");
                                                vehicle.setModel(scan.nextLine());
                                            }
                                            case 2 -> {
                                                System.out.print("Enter new Make: ");
                                                vehicle.setMake(scan.nextLine());
                                            }
                                            case 3 -> {
                                                System.out.print("Enter new Year: ");
                                                vehicle.setYear(scan.nextInt());
                                                scan.nextLine();  
                                            }
                                            case 4 -> {
                                                System.out.print("Enter new Color: ");
                                                vehicle.setColor(scan.nextLine());
                                            }
                                            case 5 -> {
                                                System.out.print("Enter new Registration Number: ");
                                                vehicle.setRegistrationNumber(scan.nextLine());
                                            }
                                            case 6 -> {
                                                System.out.print("Enter new Availability (true/false): ");
                                                vehicle.setAvailability(scan.nextBoolean());
                                            }
                                            case 7 -> {
                                                System.out.print("Enter new Daily Rate: ");
                                                vehicle.setDailyRate(scan.nextDouble());
                                            }
                                            default -> System.out.println("Invalid option.");
                                        }

                                        if (service.updateVehicle(vehicle)) {
                                            System.out.println("Vehicle updated successfully!");
                                        } else {
                                            System.out.println("Error: Unable to update vehicle.");
                                        }
                                    } catch (VehicleNotFoundException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case 4 -> {  
                                    try {
                                        System.out.print("Enter Vehicle ID to remove: ");
                                        int vehicleId = scan.nextInt();
                                        if (service.removeVehicle(vehicleId)) {
                                            System.out.println("Vehicle removed successfully!");
                                        } else {
                                            System.out.println("Error: Unable to remove vehicle.");
                                        }
                                    } catch (VehicleNotFoundException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case 5 -> {  
                                    HashMap<Integer, Vehicle> availableVehicles = service.getAvailableVehicles();
                                    if (availableVehicles.isEmpty()) {
                                        System.out.println("No available vehicles at the moment.");
                                    } else {
                                        System.out.println("Available Vehicles:");
                                        availableVehicles.forEach((id, vehicle) -> System.out.println(vehicle));
                                    }
                                }
                                case 6 -> {  
                                    System.out.println("Exiting program...");
                                    return;
                                }
                                default -> System.out.println("Invalid option: " + key);
                            }
                        }
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Error: " + e.getMessage());
                    } finally {
                        scan.close();
                    }
                }
                case 4 ->{
                	try {
                        ReservationService reservationService = new ReservationService();

                        while (true) {
                            System.out.println("1. Create Reservation");
                            System.out.println("2. Find Reservation By ID");
                            System.out.println("3. Update Reservation");
                            System.out.println("4. Cancel Reservation");
                            System.out.println("5. Find Reservations by Customer ID");
                            System.out.println("6. Exit");
                            System.out.print("Option Please: ");

                            int option = scan.nextInt();
                            scan.nextLine();

                            switch (option) {
                                case 1:
                                    try {
                                        System.out.println("Enter reservation details:");

                                        Reservation reservation = new Reservation();

                                        System.out.print("Enter Customer ID: ");
                                        reservation.setCustomerID(scan.nextInt());
                                        scan.nextLine();

                                        System.out.print("Enter Vehicle ID: ");
                                        reservation.setVehicleID(scan.nextInt());
                                        scan.nextLine();

                                        System.out.print("Enter Start Date (YYYY-MM-DD): ");
                                        reservation.setStartDate(java.sql.Date.valueOf(scan.nextLine()));

                                        System.out.print("Enter End Date (YYYY-MM-DD): ");
                                        reservation.setEndDate(java.sql.Date.valueOf(scan.nextLine()));

                                        System.out.print("Enter Total Cost: ");
                                        reservation.setTotalCost(scan.nextDouble());
                                        scan.nextLine();

                                        System.out.print("Enter Status (e.g., Reserved, Completed): ");
                                        reservation.setStatus(scan.nextLine());

                                        if (reservationService.createReservation(reservation)) {
                                            System.out.println("Reservation created successfully!");
                                        } else {
                                            System.out.println("Error creating reservation.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                    break;

                                case 2:
                                    try {
                                        System.out.print("Enter Reservation ID to find: ");
                                        int reservationId = scan.nextInt();
                                        Reservation reservation = reservationService.getReservationById(reservationId);
                                        System.out.println("Reservation Details: " + reservation);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input! Please enter a valid Reservation ID.");
                                    } catch (ReservationException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                    break;

                                case 3:
                                    try {
                                        System.out.print("Enter Reservation ID to update: ");
                                        int reservationId = scan.nextInt();
                                        scan.nextLine();
                                        Reservation reservation = reservationService.getReservationById(reservationId);

                                        System.out.println("Current details: " + reservation);
                                        System.out.println("Enter new details:");

                                        System.out.print("Enter new Start Date (YYYY-MM-DD): ");
                                        reservation.setStartDate(java.sql.Date.valueOf(scan.nextLine()));

                                        System.out.print("Enter new End Date (YYYY-MM-DD): ");
                                        reservation.setEndDate(java.sql.Date.valueOf(scan.nextLine()));

                                        System.out.print("Enter new Total Cost: ");
                                        reservation.setTotalCost(scan.nextDouble());
                                        scan.nextLine();

                                        System.out.print("Enter new Status (e.g., Reserved, Completed): ");
                                        reservation.setStatus(scan.nextLine());

                                        if (reservationService.updateReservation(reservation)) {
                                            System.out.println("Reservation updated successfully!");
                                        } else {
                                            System.out.println("Error updating reservation.");
                                        }
                                    } catch (ReservationException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                    break;

                                case 4:
                                    try {
                                        System.out.print("Enter Reservation ID to cancel: ");
                                        int reservationId = scan.nextInt();
                                        scan.nextLine();

                                        if (reservationService.cancelReservation(reservationId)) {
                                            System.out.println("Reservation cancelled successfully!");
                                        } else {
                                            System.out.println("Error cancelling reservation.");
                                        }
                                    } catch (ReservationException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                    break;

                                case 5:
                                    try {
                                        System.out.print("Enter Customer ID to find reservations: ");
                                        int customerId = scan.nextInt();
                                        List<Reservation> reservations = reservationService.getReservationsByCustomerId(customerId);
                                        if (reservations.isEmpty()) {
                                            System.out.println("No reservations found for customer ID " + customerId);
                                        } else {
                                            System.out.println("Reservations:");
                                            reservations.forEach(res -> System.out.println(res));
                                        }
                                    } catch (ReservationException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                    break;

                                case 6:
                                    System.out.println("Exiting program...");
                                    return;

                                default:
                                    System.out.println("Invalid option.");
                            }
                        }
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Error: " + e.getMessage());
                    } finally {
                        scan.close();
                    }

                }
                case 5 -> {
                    try {
                        ReportGenerator generator = new ReportGenerator();
                        System.out.println("----- REPORT MENU -----");
                        System.out.println("1. Reservation History Report");
                        System.out.println("2. Vehicle Utilization Report");
                        System.out.println("3. Revenue Report");
                        System.out.print("Enter your choice: ");
                        
						int reportChoice = scan.nextInt();

                        switch (reportChoice) {
                            case 1 -> generator.generateReservationHistory();
                            case 2 -> generator.generateVehicleUtilization();
                            case 3 -> generator.generateRevenueReport();
                            default -> System.out.println("Invalid report option.");
                        }

                    } catch (Exception e) {
                        System.out.println("Error generating report: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
 
                case 6 -> {
                    exit = true;
                    System.out.println("Exiting the application.");
                }

                default -> System.out.println("Invalid entity option. Try again.");
            }
        }

        scan.close();
    }
}
