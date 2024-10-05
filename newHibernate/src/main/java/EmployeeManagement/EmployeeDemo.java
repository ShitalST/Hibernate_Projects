package EmployeeManagement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EmployeeDemo {

    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {
        // Initialize Hibernate
        setUpHibernate();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Employee Operations");
            System.out.println("2. Department Operations");
            System.out.println("3. Project Operations");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    employeeOperations(scanner);
                    break;
                case 2:
                    departmentOperations(scanner);
                    break;
                case 3:
                    projectOperations(scanner);
                    break;
                case 4:
                    tearDownHibernate();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void setUpHibernate() {
        // Configure Hibernate
        Configuration configuration = new Configuration();
        configuration.configure("Employee-cfg.xml"); // Hibernate configuration file

        // Build session factory
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    private static void tearDownHibernate() {
        // Close session and session factory
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    private static void employeeOperations(Scanner scanner) {
        while (true) {
            System.out.println("Employee Operations:");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Back");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee(scanner);
                    break;
                case 4:
                    deleteEmployee(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        System.out.println("Enter employee name:");
        String name = scanner.nextLine();
        System.out.println("Enter employee position:");
        String position = scanner.nextLine();
        System.out.println("Enter department ID:");
        Long departmentId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Department department = session.get(Department.class, departmentId);

        if (department != null) {
            Employee employee = new Employee();
            employee.setName(name);
            employee.setPosition(position);
            employee.setDepartment(department);

            // Optionally, add projects if needed
            System.out.println("Enter project IDs (comma-separated):");
            String[] projectIds = scanner.nextLine().split(",");
            Set<Project> projects = new HashSet<>();
            for (String id : projectIds) {
                Project project = session.get(Project.class, Long.parseLong(id.trim()));
                if (project != null) {
                    projects.add(project);
                }
            }
            employee.setProjects(projects);

            session.save(employee);
            tx.commit();
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Department not found.");
            tx.rollback();
        }
    }

    private static void viewEmployees() {
        String hql = "FROM Employee";
        Query<Employee> query = session.createQuery(hql, Employee.class);
        List<Employee> employees = query.list();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee emp : employees) {
                System.out.println(emp.toString());
            }
        }
    }

    private static void updateEmployee(Scanner scanner) {
        System.out.println("Enter employee ID to update:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Employee employee = session.get(Employee.class, id);

        if (employee != null) {
            System.out.println("Enter new name:");
            String name = scanner.nextLine();
            System.out.println("Enter new position:");
            String position = scanner.nextLine();
            System.out.println("Enter new department ID:");
            Long departmentId = scanner.nextLong();
            scanner.nextLine(); // Consume newline

            Department department = session.get(Department.class, departmentId);

            if (department != null) {
                employee.setName(name);
                employee.setPosition(position);
                employee.setDepartment(department);

                // Optionally, update projects if needed
                System.out.println("Enter new project IDs (comma-separated):");
                String[] projectIds = scanner.nextLine().split(",");
                Set<Project> projects = new HashSet<>();
                for (String ids : projectIds) {
                    Project project = session.get(Project.class, Long.parseLong(ids.trim()));
                    if (project != null) {
                        projects.add(project);
                    }
                }
                employee.setProjects(projects);

                session.update(employee);
                tx.commit();
                System.out.println("Employee updated successfully.");
            } else {
                System.out.println("Department not found.");
                tx.rollback();
            }
        } else {
            System.out.println("Employee not found.");
            tx.rollback();
        }
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.println("Enter employee ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Employee employee = session.get(Employee.class, id);

        if (employee != null) {
            session.delete(employee);
            tx.commit();
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
            tx.rollback();
        }
    }

    private static void departmentOperations(Scanner scanner) {
        while (true) {
            System.out.println("Department Operations:");
            System.out.println("1. Add Department");
            System.out.println("2. View Departments");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("5. Back");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addDepartment(scanner);
                    break;
                case 2:
                    viewDepartments();
                    break;
                case 3:
                    updateDepartment(scanner);
                    break;
                case 4:
                    deleteDepartment(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addDepartment(Scanner scanner) {
        System.out.println("Enter department name:");
        String name = scanner.nextLine();

        Transaction tx = session.beginTransaction();
        Department department = new Department();
        department.setName(name);

        session.save(department);
        tx.commit();
        System.out.println("Department added successfully.");
    }

    private static void viewDepartments() {
        String hql = "FROM Department";
        Query<Department> query = session.createQuery(hql, Department.class);
        List<Department> departments = query.list();

        if (departments.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            for (Department dept : departments) {
                System.out.println(dept.toString());
            }
        }
    }

    private static void updateDepartment(Scanner scanner) {
        System.out.println("Enter department ID to update:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Department department = session.get(Department.class, id);

        if (department != null) {
            System.out.println("Enter new name:");
            String name = scanner.nextLine();

            department.setName(name);

            session.update(department);
            tx.commit();
            System.out.println("Department updated successfully.");
        } else {
            System.out.println("Department not found.");
            tx.rollback();
        }
    }

    private static void deleteDepartment(Scanner scanner) {
        System.out.println("Enter department ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Department department = session.get(Department.class, id);

        if (department != null) {
            session.delete(department);
            tx.commit();
            System.out.println("Department deleted successfully.");
        } else {
            System.out.println("Department not found.");
            tx.rollback();
        }
    }

    private static void projectOperations(Scanner scanner) {
        while (true) {
            System.out.println("Project Operations:");
            System.out.println("1. Add Project");
            System.out.println("2. View Projects");
            System.out.println("3. Update Project");
            System.out.println("4. Delete Project");
            System.out.println("5. Back");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProject(scanner);
                    break;
                case 2:
                    viewProjects();
                    break;
                case 3:
                    updateProject(scanner);
                    break;
                case 4:
                    deleteProject(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addProject(Scanner scanner) {
        System.out.println("Enter project name:");
        String name = scanner.nextLine();

        Transaction tx = session.beginTransaction();
        Project project = new Project();
        project.setName(name);

        session.save(project);
        tx.commit();
        System.out.println("Project added successfully.");
    }

    private static void viewProjects() {
        String hql = "FROM Project";
        Query<Project> query = session.createQuery(hql, Project.class);
        List<Project> projects = query.list();

        if (projects.isEmpty()) {
            System.out.println("No projects found.");
        } else {
            for (Project proj : projects) {
                System.out.println(proj.toString());
            }
        }
    }

    private static void updateProject(Scanner scanner) {
        System.out.println("Enter project ID to update:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Project project = session.get(Project.class, id);

        if (project != null) {
            System.out.println("Enter new name:");
            String name = scanner.nextLine();

            project.setName(name);

            session.update(project);
            tx.commit();
            System.out.println("Project updated successfully.");
        } else {
            System.out.println("Project not found.");
            tx.rollback();
        }
    }

    private static void deleteProject(Scanner scanner) {
        System.out.println("Enter project ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        Transaction tx = session.beginTransaction();
        Project project = session.get(Project.class, id);

        if (project != null) {
            session.delete(project);
            tx.commit();
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Project not found.");
            tx.rollback();
        }
    }
}
