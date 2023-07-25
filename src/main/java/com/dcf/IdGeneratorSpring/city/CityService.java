package com.dcf.IdGeneratorSpring.city;
import com.dcf.IdGeneratorSpring.DatabaseSetupService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
private final CityRepository cityRepository;
    private EntityManager entityManager;
    private DatabaseSetupService databaseSetupService;



    @Autowired
    public CityService(CityRepository cityRepository, EntityManager entityManager, DatabaseSetupService databaseSetupService) {
        this.cityRepository = cityRepository;
        this.entityManager = entityManager;
        this.databaseSetupService = databaseSetupService;
    }
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public void addCity(City city) {
        cityRepository.save(city);
    }


    public String generateId(String city1, String city2) {
        Optional<City> cit = cityRepository.findCityByName(city1);
        Optional<City> citB = cityRepository.findCityByName(city2);
        if (cit.isPresent() && citB.isPresent()) {
            City city = cit.get();
            City cityB = citB.get();
            // Get the sequence number and increment
            int sequence =  city.getSequence()+1;
            city.setSequence(sequence);
            cityRepository.save(city);
            //
            String id = calcCheckSum(city.getCityCode()+"-"+String.format("%06d", sequence))+"-"+cityB.getCityCode();
                System.out.println("City Id: " + id );
             return id ;
            } else {
            City city = new City(city1, "**");
            databaseSetupService.createSequence( String.format("%s_sequence", city1));
            saveCity(city, String.format("%s_sequence", city1));
            return "Create City First";
            }
        }


    private  String calcCheckSum(String baseSeqn) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(baseSeqn.getBytes(StandardCharsets.UTF_8));
            long sum = 0;
            for (byte b : digest) {
                sum += b & 0xff;
            }
            // Assume a basic set of 26 characters (A-Z)
            char checksum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int)(sum % 26));
           return baseSeqn+"-"+checksum;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return "Error";
        }
    }
    @Transactional
    public void saveCity(City city, String sequenceName) {
        city.setSequence(getNextSequenceValue(sequenceName));
        cityRepository.save(city);
    }

    private Integer getNextSequenceValue(String sequenceName) {
         return ((Number) entityManager.createNativeQuery("SELECT nextval('" + sequenceName + "')").getSingleResult()).intValue();
    }
}
