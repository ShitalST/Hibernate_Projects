package onlineCourseManagement;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    @Id
 
    private int studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "student_courses",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    // Constructors
    public Student() {}

    public Student(String studentName) {
        this.studentName = studentName;
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    // Override toString() for display
    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Student Name: " + studentName;
    }
}
