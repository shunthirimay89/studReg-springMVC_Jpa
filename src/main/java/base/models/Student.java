package base.models;

import base.models.Course;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Student {

    @Id
    private String id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty
    private String dob;
    @NotEmpty
    private String phone;
    private String gender;
    @NotEmpty
    private String education;
    private String image;
    @Transient
    private MultipartFile file;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "Enroll",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @Transient
    private List<String> courseIds;

    public List<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<String> courseIds) {
        this.courseIds = courseIds;
    }


    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", education='" + education + '\'' +
                ", image='" + image + '\'' +
                ", file=" + file +
                ", courses=" + courses +
                ", courseIds=" + courseIds +
                '}';
    }
}
