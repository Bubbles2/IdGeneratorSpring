package com.dcf.IdGeneratorSpring.city;
import jakarta.persistence.*;
import com.dcf.IdGeneratorSpring.EntityManagerHelper;
import lombok.Data;



@Entity
@Table
public class City {
    @Id
    @SequenceGenerator(
            name = "city_sequence",
            sequenceName = "city_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_sequence")
    private Long id;
    private String name;
    private String cityCode;
    private Integer sequence;
//    @PrePersist
//    public void onPrePersist() {
//        System.out.println("onPrePersist");
//        EntityManager entityManager = EntityManagerHelper.getEntityManager();
//        this.sequence = ((Number) entityManager.createNativeQuery("SELECT nextval('paris_sequence')").getSingleResult()).intValue();
//    }

    public City() {
    }

    public City(String name, String cityCode) {
        this.name = name;
        this.cityCode = cityCode;
    }

    public City(String name, String cityCode, Integer sequence) {
        this.name = name;
        this.cityCode = cityCode;
        this.sequence = sequence;
    }

    public City(Long id, String name, String cityCode, Integer sequence) {
        this.id = id;
        this.name = name;
        this.cityCode = cityCode;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}

