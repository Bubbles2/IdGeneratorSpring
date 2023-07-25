package com.dcf.IdGeneratorSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

@Service
public class DatabaseSetupService {

    private final EntityManager entityManager;

    @Autowired
    public DatabaseSetupService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void createSequence(String sequenceName) {
        entityManager.createNativeQuery("CREATE SEQUENCE " + sequenceName).executeUpdate();
    }
}

