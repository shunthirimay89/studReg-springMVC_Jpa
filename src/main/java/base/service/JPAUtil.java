package base.service;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    static EntityManagerFactory emfactory = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        return emfactory = Persistence.createEntityManagerFactory("StudentRegistration_SpringJPA");
    }
}
