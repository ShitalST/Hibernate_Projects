package EmployeeManagement;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    // Getters and Setters

    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Project [id=").append(id)
          .append(", name=").append(name)
          .append(", employees=[");

        if (employees != null) {
            for (Employee employee : employees) {
                sb.append(employee.getName()).append(" ");
            }
        }
        sb.append("]]");
        return sb.toString();
    }

}

