package base.daos;


import base.models.Course;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseDao {

    public int insertCourse(Course course) {
        int i = 0;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
            i = 1;
        } catch (Exception e) {
            System.out.println("Inserting  course is failed" + e.getMessage());

            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback the transaction if an exception occurs
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return i;
    }

    public String getLatestCourseId() {
        String latestCourseId = "COR001";

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery("SELECT MAX(SUBSTRING(c.id, 4)) FROM Course c");

            String maxCourseNumberStr = (String) query.getSingleResult();
            int newCourseNumber = (maxCourseNumberStr != null) ? Integer.parseInt(maxCourseNumberStr) + 1 : 1;

            // Format the latestCourseId based on the new course number
            if (newCourseNumber < 10) {
                latestCourseId = "COR00" + newCourseNumber;
            } else if (newCourseNumber < 100) {
                latestCourseId = "COR0" + newCourseNumber;
            } else {
                latestCourseId = "COR" + newCourseNumber;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately in a production environment
        }

        return latestCourseId;
    }

    //get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
            entityManager.getTransaction().commit();
        } finally {
            assert entityManager != null;
            entityManager.close();
        }
        return courses;
    }

    public int updateCourse(Course course) {
        int result = 0;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();

            // Find the course to update by ID
            Course existingCourse = em.find(Course.class, course.getId());

            if (existingCourse != null) {
                existingCourse.setCourseName(course.getCourseName());
                em.merge(existingCourse);
                em.getTransaction().commit();
                result = 1;
            } else {
                System.out.println("Course not found with ID: " + course.getId());
            }
        } catch (Exception e) {
            System.out.println("Updating course is failed: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public int deleteCourse(String courseId) {
        EntityManager em = null;
        int i = 0;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Course c WHERE c.id=:courseId");
            query.setParameter("courseId", courseId);
            i = query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return i;
    }

    public Course getCourseById(String courseID) {
        Course course = null;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery("SELECT c FROM Course c WHERE c.id = :courseID");
            query.setParameter("courseID", courseID);
            List<Course> courseList = query.getResultList();
            if (!courseList.isEmpty()) {
                course = courseList.get(0); // Retrieve the first user from the list
            } else {
                System.out.println("Course not found with ID: " + courseID);

            }
        } catch (NoResultException e) {
            System.out.println("Course not found with ID: " + courseID);

        } catch (Exception e) {
            System.out.println("Error retrieving course by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return course;
    }

    public List<Course> searchCourseByName(String name) {
        List<Course> courses = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select Courses by name
            Query query = em.createQuery("SELECT c FROM Course c WHERE c.courseName LIKE :name");
            query.setParameter("name", "%" + name + "%");

            // Execute the query and get the result list
            List<Course> resultList = query.getResultList();

            // Add the result entities to the list
            courses.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Searching course by name failed: " + e.getMessage());
            e.printStackTrace();
        }

        return courses;
    }

    public List<Course> searchCourseById(String courseId) {
        List<Course> courses = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery("SELECT c FROM Course c WHERE c.id = :courseId");
            query.setParameter("courseId", courseId);
            List<Course> resultList = query.getResultList();
            courses.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Searching course by ID failed: " + e.getMessage());
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseByName(String name) {
        Course course = null;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select a course by name
            Query query = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :name");
            query.setParameter("name", name);
            List<Course> courseList = query.getResultList();

            if (!courseList.isEmpty()) {
                course = courseList.get(0); // Retrieve the first course from the list
            } else {
                System.out.println("Course not found with name: " + name);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving course by name: " + e.getMessage());
            e.printStackTrace();
        }

        return course;
    }

}








