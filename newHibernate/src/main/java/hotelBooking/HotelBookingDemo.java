package hotelBooking;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class HotelBookingDemo {

    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration().configure("Hotel-cfg.xml").buildSessionFactory();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Hotel Booking System ---");
            System.out.println("1. Customer Operations");
            System.out.println("2. Hotel Operations");
            System.out.println("3. Room Operations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    hotelMenu();
                    break;
                case 3:
                    roomMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        factory.close();
    }

    // Customer Operations Menu
    private static void customerMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Customer Operations ---");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewAllCustomers();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Hotel Operations Menu
    private static void hotelMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Hotel Operations ---");
            System.out.println("1. Add Hotel");
            System.out.println("2. View All Hotels");
            System.out.println("3. Update Hotel");
            System.out.println("4. Delete Hotel");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addHotel();
                    break;
                case 2:
                    viewAllHotels();
                    break;
                case 3:
                    updateHotel();
                    break;
                case 4:
                    deleteHotel();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Room Operations Menu
    private static void roomMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Room Operations ---");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewAllRooms();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Add Customer
    private static void addCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Customer customer = new Customer(name, email);
            session.save(customer);
            session.getTransaction().commit();
            System.out.println("Customer added successfully.");
        } finally {
            session.close();
        }
    }

    // View All Customers
    private static void viewAllCustomers() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            List<Customer> customers = session.createQuery("from Customer", Customer.class).getResultList();
            for (Customer customer : customers) {
                System.out.println("ID: " + customer.getId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail());
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    // Update Customer
    private static void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                System.out.print("Enter new customer name: ");
                customer.setName(scanner.nextLine());
                System.out.print("Enter new customer email: ");
                customer.setEmail(scanner.nextLine());
                session.getTransaction().commit();
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer not found.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // Delete Customer
    private static void deleteCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer ID to delete: ");
        Long id = scanner.nextLong();

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
                session.getTransaction().commit();
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // Add Hotel
    private static void addHotel() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter hotel name: ");
        String name = scanner.nextLine();
        System.out.print("Enter hotel location: ");
        String location = scanner.nextLine();

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Hotel hotel = new Hotel(name, location);
            session.save(hotel);
            session.getTransaction().commit();
            System.out.println("Hotel added successfully.");
        } finally {
            session.close();
        }
    }

    // View All Hotels
    private static void viewAllHotels() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            List<Hotel> hotels = session.createQuery("from Hotel", Hotel.class).getResultList();
            for (Hotel hotel : hotels) {
                System.out.println("ID: " + hotel.getId() + ", Name: " + hotel.getName() + ", Location: " + hotel.getLocation());
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    // Update Hotel
    private static void updateHotel() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter hotel ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Hotel hotel = session.get(Hotel.class, id);
            if (hotel != null) {
                System.out.print("Enter new hotel name: ");
                hotel.setName(scanner.nextLine());
                System.out.print("Enter new hotel location: ");
                hotel.setLocation(scanner.nextLine());
                session.getTransaction().commit();
                System.out.println("Hotel updated successfully.");
            } else {
                System.out.println("Hotel not found.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // Delete Hotel
    private static void deleteHotel() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter hotel ID to delete: ");
        Long id = scanner.nextLong();

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Hotel hotel = session.get(Hotel.class, id);
            if (hotel != null) {
                session.delete(hotel);
                session.getTransaction().commit();
                System.out.println("Hotel deleted successfully.");
            } else {
                System.out.println("Hotel not found.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // Add Room
    private static void addRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter room price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter hotel ID for the room: ");
        Long hotelId = scanner.nextLong();

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Hotel hotel = session.get(Hotel.class, hotelId);
            if (hotel != null) {
                Room room = new Room(roomNumber, price, hotel);
                session.save(room);
                session.getTransaction().commit();
                System.out.println("Room added successfully.");
            } else {
                System.out.println("Hotel not found. Cannot add room.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // View All Rooms
    private static void viewAllRooms() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            List<Room> rooms = session.createQuery("from Room", Room.class).getResultList();
            for (Room room : rooms) {
                System.out.println("ID: " + room.getId() + ", Number: " + room.getRoomNumber() +
                        ", Price: " + room.getPrice() + ", Hotel: " + room.getHotel().getName());
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    // Update Room
    private static void updateRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room ID to update: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // consume newline

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Room room = session.get(Room.class, id);
            if (room != null) {
                System.out.print("Enter new room number: ");
                room.setRoomNumber(scanner.nextInt());
                System.out.print("Enter new room price: ");
                room.setPrice(scanner.nextDouble());
                session.getTransaction().commit();
                System.out.println("Room updated successfully.");
            } else {
                System.out.println("Room not found.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // Delete Room
    private static void deleteRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room ID to delete: ");
        Long id = scanner.nextLong();

        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Room room = session.get(Room.class, id);
            if (room != null) {
                session.delete(room);
                session.getTransaction().commit();
                System.out.println("Room deleted successfully.");
            } else {
                System.out.println("Room not found.");
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }
}
