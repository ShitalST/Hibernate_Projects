package hospitalSystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class HospitalManagementSystem {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        // Set up Hibernate SessionFactory
        sessionFactory = new Configuration().configure("configuration_cfg.xml").buildSessionFactory();
        runMenu();
        // Clean up resources
        sessionFactory.close();
    }

    // Main menu method
    public static void runMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display the menu
            displayMenu();

            // Take user choice
            choice = scanner.nextInt();

            // Call methods based on choice using switch-case
            switch (choice) {
                case 1:
                    patientOperations();
                    break;
                case 2:
                    appointmentOperations();
                    break;
                case 3:
                    medicalHistoryOperations();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Method to display the main menu
    public static void displayMenu() {
        System.out.println("====== MAIN MENU ======");
        System.out.println("1. Patient Operations");
        System.out.println("2. Appointment Operations");
        System.out.println("3. Medical History Operations");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Patient operations menu
    public static void patientOperations() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display patient operations menu
            System.out.println("====== PATIENT MENU ======");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            // Call methods based on choice using switch-case
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Appointment operations menu
    public static void appointmentOperations() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display appointment operations menu
            System.out.println("====== APPOINTMENT MENU ======");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Update Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            // Call methods based on choice using switch-case
            switch (choice) {
                case 1:
                    addAppointment();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    updateAppointment();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Medical history operations menu
    public static void medicalHistoryOperations() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display medical history operations menu
            System.out.println("====== MEDICAL HISTORY MENU ======");
            System.out.println("1. Add Medical History");
            System.out.println("2. View Medical Histories");
            System.out.println("3. Update Medical History");
            System.out.println("4. Delete Medical History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            // Call methods based on choice using switch-case
            switch (choice) {
                case 1:
                    addMedicalHistory();
                    break;
                case 2:
                    viewMedicalHistories();
                    break;
                case 3:
                    updateMedicalHistory();
                    break;
                case 4:
                    deleteMedicalHistory();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Methods for Patient Operations
    public static void addPatient() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            Patient patient = new Patient();
            System.out.print("Enter patient name: ");
            patient.setName(scanner.nextLine());
            System.out.print("Enter age: ");
            patient.setAge(scanner.nextInt());
            scanner.nextLine(); // Consume newline
            System.out.print("Enter gender: ");
            patient.setGender(scanner.nextLine());

            session.save(patient);
            System.out.println("Patient added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding patient: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void viewPatients() {
        Session session = sessionFactory.openSession();
        try {
            session.createQuery("from Patient", Patient.class)
                    .list()
                    .forEach(patient -> System.out.println(patient.getId() + ": " + patient.getName() + ", " + patient.getAge() + " years old"));
        } catch (Exception e) {
            System.out.println("Error retrieving patients: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void updatePatient() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID to update: ");
            Long id = scanner.nextLong();
            scanner.nextLine(); // Consume newline
            Patient patient = session.get(Patient.class, id);

            if (patient != null) {
                System.out.print("Enter new name: ");
                patient.setName(scanner.nextLine());
                System.out.print("Enter new age: ");
                patient.setAge(scanner.nextInt());
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new gender: ");
                patient.setGender(scanner.nextLine());
                session.update(patient);
                System.out.println("Patient updated successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating patient: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void deletePatient() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID to delete: ");
            Long id = scanner.nextLong();
            Patient patient = session.get(Patient.class, id);

            if (patient != null) {
                session.delete(patient);
                System.out.println("Patient deleted successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting patient: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Methods for Appointment Operations
    public static void addAppointment() {
        Session session = sessionFactory.openSession();
        try {
            Appointment appointment = new Appointment();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter doctor's name: ");
            appointment.setDoctorName(scanner.nextLine());
            appointment.setDate(new java.util.Date()); // Set current date

            System.out.print("Enter Patient ID for the appointment: ");
            Long patientId = scanner.nextLong();
            Patient patient = session.get(Patient.class, patientId);

            if (patient != null) {
                appointment.setPatient(patient);
                session.save(appointment);
                System.out.println("Appointment added successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding appointment: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void viewAppointments() {
        Session session = sessionFactory.openSession();
        try {
            session.createQuery("from Appointment", Appointment.class)
                    .list()
                    .forEach(app -> System.out.println("Appointment ID " + app.getId() + " with Dr. " + app.getDoctorName()));
        } catch (Exception e) {
            System.out.println("Error retrieving appointments: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void updateAppointment() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Appointment ID to update: ");
            Long id = scanner.nextLong();
            scanner.nextLine(); // Consume newline
            Appointment appointment = session.get(Appointment.class, id);

            if (appointment != null) {
                System.out.print("Enter new doctor's name: ");
                appointment.setDoctorName(scanner.nextLine());
                session.update(appointment);
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void deleteAppointment() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Appointment ID to delete: ");
            Long id = scanner.nextLong();
            Appointment appointment = session.get(Appointment.class, id);

            if (appointment != null) {
                session.delete(appointment);
                System.out.println("Appointment deleted successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Methods for Medical History Operations
    public static void addMedicalHistory() {
        Session session = sessionFactory.openSession();
        try {
            MedicalHistory history = new MedicalHistory();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter medical history description: ");
            history.setDescription(scanner.nextLine());

            System.out.print("Enter Patient ID for the medical history: ");
            Long patientId = scanner.nextLong();
            Patient patient = session.get(Patient.class, patientId);

            if (patient != null) {
                history.setPatient(patient);
                session.save(history);
                System.out.println("Medical history added successfully.");
            } else {
                System.out.println("Patient not found.");
            }
        } catch (Exception e) {
            System.out.println("Error adding medical history: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void viewMedicalHistories() {
        Session session = sessionFactory.openSession();
        try {
            session.createQuery("from MedicalHistory", MedicalHistory.class)
                    .list()
                    .forEach(history -> System.out.println("History ID " + history.getId() + ": " + history.getDescription()));
        } catch (Exception e) {
            System.out.println("Error retrieving medical histories: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void updateMedicalHistory() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Medical History ID to update: ");
            Long id = scanner.nextLong();
            scanner.nextLine(); // Consume newline
            MedicalHistory history = session.get(MedicalHistory.class, id);

            if (history != null) {
                System.out.print("Enter new description: ");
                history.setDescription(scanner.nextLine());
                session.update(history);
                System.out.println("Medical history updated successfully.");
            } else {
                System.out.println("Medical history not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating medical history: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void deleteMedicalHistory() {
        Session session = sessionFactory.openSession();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Medical History ID to delete: ");
            Long id = scanner.nextLong();
            MedicalHistory history = session.get(MedicalHistory.class, id);

            if (history != null) {
                session.delete(history);
                System.out.println("Medical history deleted successfully.");
            } else {
                System.out.println("Medical history not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting medical history: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
