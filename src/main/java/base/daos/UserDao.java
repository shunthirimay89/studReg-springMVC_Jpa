package base.daos;

import base.models.User;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    public int insertUser(User user) {
        int i = 0;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            i = 1;
        } catch (Exception e) {
            System.out.println("Inserting user is failed" + e.getMessage());
            // Handle exceptions appropriately, log or throw a custom exception
            e.printStackTrace(); // Log the exception or use a logging framework
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

    public String getLatestUserId() {
        String latestUserId = "USR001";

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPQL to get the maximum user number as String
            Query query = em.createQuery("SELECT MAX(SUBSTRING(u.id, 4)) FROM User u");

            String maxUserNumberStr = (String) query.getSingleResult();
            int newUserNumber = (maxUserNumberStr != null) ? Integer.parseInt(maxUserNumberStr) + 1 : 1;

            // Format the latestUserId based on the new user number
            if (newUserNumber < 10) {
                latestUserId = "USR00" + newUserNumber;
            } else if (newUserNumber < 100) {
                latestUserId = "USR0" + newUserNumber;
            } else {
                latestUserId = "USR" + newUserNumber;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately in a production environment
        }

        return latestUserId;
    }

    public int updateUser(User user) {
        int i = 0;
        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();

            // Find the user to update by ID
            User existingUser = em.find(User.class, user.getId());

            if (existingUser != null) {
                // Update the user properties with the new values
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());
                // Persist the changes to the database
                em.merge(existingUser);

                em.getTransaction().commit();
                i = 1;
            } else {

                System.out.println("User not found with ID: " + user.getId());
            }
        } catch (Exception e) {
            System.out.println("Updating user is failed" + e.getMessage());
            e.printStackTrace();
        }
        return i;
    }

    public List<User> getAllUsers() {
        List<User> userList = null;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select all users
            Query query = em.createQuery("SELECT u FROM User u WHERE u.role = 'user'");

            // Execute the query and get the result list
            userList = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error retrieving all users: " + e.getMessage());
            e.printStackTrace();
        }

        return userList;
    }

    public User getUserById(String userId) {
        User user = null;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :userId");
            query.setParameter("userId", userId);
            List<User> userList = query.getResultList();
            if (!userList.isEmpty()) {
                user = userList.get(0); // Retrieve the first user from the list
            } else {
                System.out.println("User not found with ID: " + userId);

            }
        } catch (NoResultException e) {
            System.out.println("User not found with ID: " + userId);

        } catch (Exception e) {
            System.out.println("Error retrieving user by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public int deleteUser(String userId) {
        EntityManager em = null;
        int i = 0;

        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();

            Query query = em.createQuery("DELETE FROM User u WHERE u.id=:userId");
            query.setParameter("userId", userId);
            i = query.executeUpdate();

            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return i;
    }

    public List<User> searchUserByName(String name) {
        List<User> users = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select users by name
            Query query = em.createQuery("SELECT u FROM User u WHERE u.name LIKE :name");
            query.setParameter("name", "%" + name + "%");

            // Execute the query and get the result list
            List<User> resultList = query.getResultList();

            // Add the result entities to the list
            users.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Searching user by name failed: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    public List<User> searchUserById(String userId) {
        List<User> users = new ArrayList<>();

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select a user by ID
            Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :userId");
            query.setParameter("userId", userId);

            // Execute the query and get the result list
            List<User> resultList = query.getResultList();

            // Add the result entities to the list
            users.addAll(resultList);
        } catch (Exception e) {
            System.out.println("Searching user by ID failed: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    public User getUserByEmail(String email) {
        User user = null;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select a user by email
            Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);

            // Execute the query and get the single result
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Getting user by email failed: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }
}







