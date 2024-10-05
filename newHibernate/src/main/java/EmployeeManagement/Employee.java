package EmployeeManagement;
import jakarta.persistence.*;


import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany
    @JoinTable(
        name = "employee_project")
    private Set<Project> projects;

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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	  @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("Employee [id=").append(id)
	          .append(", name=").append(name)
	          .append(", position=").append(position)
	          .append(", department=").append(department != null ? department.getName() : "N/A")
	          .append(", projects=[");

	        if (projects != null) {
	            for (Project project : projects) {
	                sb.append(project.getName()).append(" ");
	            }
	        }
	        sb.append("]]");
	        return sb.toString();
	    }
}
