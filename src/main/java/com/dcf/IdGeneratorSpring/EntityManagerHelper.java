package com.dcf.IdGeneratorSpring;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerHelper {

    private static EntityManager entityManager;

    @Autowired
    public EntityManagerHelper(EntityManager entityManager) {
        EntityManagerHelper.entityManager = entityManager;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
