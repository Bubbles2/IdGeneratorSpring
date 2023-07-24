package com.dcf.IdGeneratorSpring.city;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
private final CityRepository cityRepository;
@Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public void addCity(City city) {
        cityRepository.save(city);
    }


    public void generateId(String city1, String city2) {
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
            } else {
                System.out.println("Invalid City Params " );
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
}
