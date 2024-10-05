package onlineCourseManagement;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import hotelBooking.Customer;

import java.util.*;
import java.util.Scanner;

public class OnlineCourseDemo {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        // Initialize SessionFactory
        sessionFactory = new Configuration().configure("Course.xml").buildSessionFactory();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            displayMainMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageCourses(scanner); 
                    break;
                case 2:
                    manageStudents(scanner); 
                    break;
                case 3:
                    enrollStudentInCourse(scanner); // Enroll a student in a course
                    break;
                case 4:
                	viewEnrollments();
                	break;
                case 5:
                    System.out.println("Thank you For using this system... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
        sessionFactory.close();
    }

    // Display the main menu
    private static void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Manage Courses");
        System.out.println("2. Manage Students");
        System.out.println("3. Enroll Student in Course");
        System.out.println("4. View all enrolled student in course.");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // Manage Courses Menu
    private static void manageCourses(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== Course Management ===");
            System.out.println("1. Add Course");
            System.out.println("2. View Courses");
            System.out.println("3.Update Course");
            System.out.println("4.Delete Course");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCourse(scanner);
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                	updateCourse(scanner);
                	break;
                case 4:
                	deleteCourse(scanner);
                	break;
                case 5:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Manage Students Menu
    private static void manageStudents(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3.Update Student");
            System.out.println("4.Delete Student");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                	updateStudent(scanner);
                	break;
                case 4:
                	deleteStudent(scanner);
                	break;
                case 5:
                	
                    return; // Back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    // Method to add a new course
    private static void addCourse(Scanner scanner) {
       System.out.println("Enter course Id");
       int id=scanner.nextInt();
       scanner.nextLine();
    	System.out.print("Enter Course Name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter Course Description: ");
        String description = scanner.nextLine();

        Course course = new Course();
        course.setCourseId(id);
        course.setCourseName(courseName);
        course.setDescription(description);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(course);

        transaction.commit();
        session.close();

        System.out.println("Course added successfully!");
    }

    // Method to view all courses
    private static void viewCourses() {
        Session session = sessionFactory.openSession();
        List<Course> courses = session.createQuery("FROM Course", Course.class).list();
        session.close();

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("\n=== List of Courses ===");
            courses.forEach(System.out::println);
        }
    }

    
    //updatecourse
    
    private static void updateCourse(Scanner scanner)
    {
    	System.out.println("Enter course id you want to update: ");
    	int id=scanner.nextInt();
    	scanner.nextLine();
    	System.out.println("Enter new Course name:");
    	String name=scanner.nextLine();
    	System.out.println("Enter course description: ");
    	String desc=scanner.nextLine();

        Course c = new Course();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        c.setCourseId(id);
    	c.setCourseName(name);
    	c.setDescription(desc);
    	session.saveOrUpdate(c);
    	transaction.commit();
    	session.close();
    	System.out.println("Course Updated Successfully...");
    }
    
    //Delete Course
    private static void deleteCourse(Scanner scanner)
    {
    	System.out.println("Enter Course id which is delete: ");
    	int id=scanner.nextInt();
    
    	 Session session = sessionFactory.openSession();
         try {
             Transaction tx=session.beginTransaction();
             Course c = session.get(Course.class, id);
             if (c != null) {
                 session.delete(c);
                 session.getTransaction().commit();
                 System.out.println("Course deleted successfully.");
             } else {
                 System.out.println("COurse not found.");
                 session.getTransaction().rollback();
             }
         }catch(Exception e) {e.printStackTrace();}
    }
    // Method to add a new student
    private static void addStudent(Scanner scanner) {
       System.out.println("Enter Student Id:");
       int id=scanner.nextInt();
       scanner.nextLine();
    	System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();

        Student student = new Student();
        student.setStudentId(id);
        student.setStudentName(studentName);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(student);

        transaction.commit();
        session.close();

        System.out.println("Student added successfully!");
    }

    // Method to view all students
    private static void viewStudents() {
        Session session = sessionFactory.openSession();
        List<Student> students = session.createQuery("FROM Student", Student.class).list();
        session.close();

        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("\n=== List of Students ===");
            students.forEach(System.out::println);
        }
    }

    
    //update Student
    private static void updateStudent(Scanner scanner)
    {
    	
    	System.out.println("Enter Student id you want to update: ");
    	int id=scanner.nextInt();
    	scanner.nextLine();
    	System.out.println("Enter new Course name:");
    	String name=scanner.nextLine();
    	

       Student s = new Student();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        s.setStudentId(id);
    	s.setStudentName(name);
    	
    	session.saveOrUpdate(s);
    	transaction.commit();
    	session.close();
    	System.out.println("Student Updated Successfully...");
    }
    //Delete Student
    
    private static void deleteStudent(Scanner scanner)
    {

    	System.out.println("Enter Student id which is delete: ");
    	int id=scanner.nextInt();
    
    	 Session session = sessionFactory.openSession();
         try {
             Transaction tx=session.beginTransaction();
             Student s = session.get(Student.class, id);
             if (s != null) {
                 session.delete(s);
                 session.getTransaction().commit();
                 System.out.println("Student deleted successfully.");
             } else {
                 System.out.println("Student not found.");
                 session.getTransaction().rollback();
             }
         }catch(Exception e) {e.printStackTrace();}
    }
    // Method to enroll a student in a course
    private static void enrollStudentInCourse(Scanner scanner) {
    	
    	
    	
        System.out.print("Enter Student ID to Enroll: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter Course ID to Enroll In: ");
        int courseId = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Student student = session.get(Student.class, studentId);
        Course course = session.get(Course.class, courseId);

        if (student != null && course != null) {
            student.getCourses().add(course);
            course.getStudents().add(student);
            session.update(student); // Update the student to reflect the changes
            session.update(course);  // Update the course to reflect the changes

            transaction.commit();
            System.out.println("Student enrolled in the course successfully!");
        } else {
            System.out.println("Invalid student or course ID.");
            transaction.rollback();
        }

        session.close();
    }
    
 // Method to view detailed enrollments (detailed student and course information)
    private static void viewEnrollments() {
        Session session = sessionFactory.openSession();
        List<Course> courses = session.createQuery("FROM Course", Course.class).list();

        // Initialize lazy collections (students) before closing the session
        for (Course course : courses) {
            Hibernate.initialize(course.getStudents()); // Initialize the students collection
        }

        session.close();

        if (courses.isEmpty()) {
            System.out.println("No courses or enrollments available.");
        } else {
            System.out.println("\n=== Detailed Enrollment Information ===");
            for (Course course : courses) {
                System.out.println("Course: " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                System.out.println("  Description: " + course.getDescription());
               
                
                List<Student> enrolledStudents = course.getStudents();
                if (enrolledStudents.isEmpty()) {
                    System.out.println("  No students enrolled.");
                } else {
                    System.out.println("  Enrolled Students:");
                    for (Student student : enrolledStudents) {
                        System.out.println("     Name: " + student.getStudentName() + " (ID: " + student.getStudentId() + ")"+"\n\n");
                      
                    }
                }
            }
        }
    }


}
