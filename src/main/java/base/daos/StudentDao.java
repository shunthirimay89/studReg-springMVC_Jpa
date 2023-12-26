package base.daos;

import base.models.Student;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDao {

    public int insertStudent(Student student) {
        int i = 0;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            i = 1;
        } catch (Exception e) {
            System.out.println("Inserting course is failed" + e.getMessage());
            e.printStackTrace(); // Log the exception or use a logging framework
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return i;
    }

    public String getLatestStudentId() {
        String latestStudentId = "SDR001";

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery("SELECT MAX(SUBSTRING(s.id, 4)) FROM Student s");

            String maxStudentNumberStr = (String) query.getSingleResult();
            int newStudentNumber = (maxStudentNumberStr != null) ? Integer.parseInt(maxStudentNumberStr) + 1 : 1;

            // Format the latestUserId based on the new user number
            if (newStudentNumber < 10) {
                latestStudentId = "SDR00" + newStudentNumber;
            } else if (newStudentNumber < 100) {
                latestStudentId = "SDR0" + newStudentNumber;
            } else {
                latestStudentId = "SDR" + newStudentNumber;
            }
        } catch (Exception e) {
            System.out.println("GetLatestStudent id is failed" + e.getMessage());
            e.printStackTrace();
        }
        return latestStudentId;
    }

    //user view method
    public List<Student> getAllStudents() {
        List<Student> students;
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
            entityManager.getTransaction().commit();
        } finally {
            assert entityManager != null;
            entityManager.close();
        }
        return students;
    }

    public Student findStudentById(String studId) {
        EntityManager entityManager = null;
        Student student = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            student = entityManager.find(Student.class, studId);
            if (student == null) {
                // Handle the case where the user is not found, e.g., throw an exception
                throw new EntityNotFoundException("Student not found with ID: " + studId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return student;
    }

    public int updateStudent(Student updatedStudent) {
        EntityManager entityManager = null;
        int updateResult;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing user by ID
            Student existingStudent = entityManager.find(Student.class, updatedStudent.getId());

            if (existingStudent != null) {
                // Update the existing user with the new values
                existingStudent.setName(updatedStudent.getName());
                existingStudent.setDob(updatedStudent.getDob());
                existingStudent.setEducation(updatedStudent.getEducation());
                existingStudent.setImage(updatedStudent.getImage());
                existingStudent.setGender(updatedStudent.getGender());
                existingStudent.setCourses(updatedStudent.getCourses());

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the update was successful
                updateResult = 1;
            } else {
                // Handle the case where the user with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + updatedStudent.getId());
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return updateResult;
    }

    public List<Student> searchStudentByName(String name) {
        List<Student> students = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select users by name
            Query query = em.createQuery("SELECT s FROM Student s WHERE s.name LIKE :name");
            query.setParameter("name", "%" + name + "%");

            // Execute the query and get the result list
            List<Student> resultList = query.getResultList();

            // Add the result entities to the list
            students.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Searching user by name failed: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> searchStudentById(String studentId) {
        List<Student> students = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select a user by ID
            Query query = em.createQuery("SELECT s FROM Student s WHERE s.id = :studentId");
            query.setParameter("studentId", studentId);

            // Execute the query and get the result list
            List<Student> resultList = query.getResultList();

            // Add the result entities to the list
            students.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Searching user by ID failed: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }

    public int deleteStudent(String studentId) {
        EntityManager entityManager = null;
        int deleteResult;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing student by ID
            Student existingStudent = entityManager.find(Student.class, studentId);

            if (existingStudent != null) {
                // Remove the existing student from the database
                entityManager.remove(existingStudent);

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the delete was successful
                deleteResult = 1;
            } else {
                // Handle the case where the user with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("Student not found with ID: " + studentId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return deleteResult;
    }

    public List<Student> getStudentsByCourse(String courseName) {
        List<Student> students = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select students by course name
            Query query = em.createQuery("SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = :courseName");
            query.setParameter("courseName", courseName);

            // Execute the query and get the result list
            List<Student> resultList = query.getResultList();

            // Add the result entities to the list
            students.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Fetching students by course failed: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }


}
