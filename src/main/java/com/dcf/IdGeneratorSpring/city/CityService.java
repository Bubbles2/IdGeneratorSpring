package com.dcf.IdGeneratorSpring.city;

import com.dcf.IdGeneratorSpring.DatabaseSetupService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            int sequence = getNextSequenceValue(city.getName().trim().toLowerCase() + "_sequence");
            // Dont really need to save squence number here
            city.setSequence(sequence);
            cityRepository.save(city);
            //
            String id = calcCheckSum(city.getCityCode() + "-" + String.format("%06d", sequence)) + "-" + cityB.getCityCode();
            System.out.println("City Id: " + id);
            return id;
        } else {
            City city = new City();
            City cityB = new City();
            if (cit.isPresent()) {
                city = cit.get();
            } else {
                city = createCity(city1, "--");
            }

            if (citB.isPresent()) {
                cityB = citB.get();
            } else {
                cityB = createCity(city1, "--");
            }

            String id = calcCheckSum(city.getCityCode() + "-" + String.format("%06d", city.getSequence())) + "-" + cityB.getCityCode();

            System.out.println("City Id: " + id);

            return "Create City First";
        }
    }

    private City createCity(String city1, String cityCode) {
        City city = new City(city1, cityCode);
        String sequenceName = String.format("%s_sequence", city1.trim().toLowerCase());

        if (!sequenceExists(sequenceName)) {
            databaseSetupService.createSequence(sequenceName);
        }

        saveCity(city, sequenceName);
        return city;
    }


    private String calcCheckSum(String baseSeqn) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(baseSeqn.getBytes(StandardCharsets.UTF_8));
            long sum = 0;
            for (byte b : digest) {
                sum += b & 0xff;
            }
            // Assume a basic set of 26 characters (A-Z)
            char checksum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (sum % 26));
            return "" + checksum;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return "";
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

    public String generateIdTwo(String city1, String cityCode, String cityCode2, String separator, boolean useName, boolean addChksum, String city2, String sex, String dob, String tob, String version) {
        Optional<City> cit = cityRepository.findCityByName(city1);
        Optional<City> citB = cityRepository.findCityByName(city2);
        String id = "";
        if (cit.isPresent() && citB.isPresent()) {
            City city = cit.get();
            City cityB = citB.get();
            // Get the sequence number and increment
            int sequence = getNextSequenceValue(city.getName().trim().toLowerCase() + "_sequence");
            // Dont really need to save squence number here
            city.setSequence(sequence);
            cityRepository.save(city);
            //
            id = city.getCityCode() + separator + String.format("%06d", city.getSequence());
            if (addChksum) {
                id = id + separator + calcCheckSum(city.getCityCode() + separator + String.format("%06d", city.getSequence()));
            }
            if (useName) {
                id = id + separator + city1.trim().toLowerCase();
            }
            if (city2 != null) {
                id = id + separator + cityB.getCityCode();
            }

        } else {
            City city = new City();
            City cityB = new City();
            if (cit.isPresent()) {
                city = cit.get();
            } else {
                city = createCity(city1, cityCode);
            }

            if (citB.isPresent()) {
                cityB = citB.get();
            } else {
                if(city2 != null) {
                    cityB = createCity(city2, cityCode2);
                }

            }
            id = city.getCityCode() + separator + String.format("%06d", city.getSequence());
            if (addChksum) {
                id = id + separator + calcCheckSum(city.getCityCode() + separator + String.format("%06d", city.getSequence()));
            }
            if (useName) {
                id = id + separator + city1.trim().toLowerCase();
            }
            if (city2 != null) {
                id = id + separator + cityB.getCityCode();
            }
        }
        if (sex != null) id = id + separator + sex;
        if (dob != null) id = id + separator + dob;
        if (tob != null) id = id + separator + tob;
        System.out.println("City Id: " + id);
        return id;
    }

    public boolean sequenceExists(String sequenceName) {
        try {
            String sql = "SELECT 1 FROM information_schema.sequences WHERE sequence_name = :sequenceName";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("sequenceName", sequenceName);
            Integer result = (Integer) query.getSingleResult();
            return result != null && result == 1;
        } catch (NoResultException e) {
            return false;
        }
    }

    public String generateIdThree(String city1, String city2, String idPattern) {
        Optional<City> cit = cityRepository.findCityByName(city1);
        Optional<City> citB = cityRepository.findCityByName(city2);
        String id = "";
        if (cit.isPresent() && citB.isPresent()) {
            City city = cit.get();
            City cityB = citB.get();
            // Get the sequence number and increment
            int sequence = getNextSequenceValue(city.getName().trim().toLowerCase() + "_sequence");
            // Dont really need to save squence number here
            city.setSequence(sequence);
            cityRepository.save(city);
            //
            id = city.getCityCode() + "-" + String.format("%06d", city.getSequence());
                id = id +  "-" + calcCheckSum(city.getCityCode() +  "-" + String.format("%06d", city.getSequence()));
                id = id +  "-" + city1.trim().toLowerCase();
                id = id +  "-" + cityB.getCityCode();
            }
         else {
            City city = new City();
            City cityB = new City();
            if (cit.isPresent()) {
                city = cit.get();
            } else {
                city = createCity(city1, "***");
            }

            if (citB.isPresent()) {
                cityB = citB.get();
            } else {
                if(city2 != null) {
                    cityB = createCity(city2, "***");
                }

            }
            id = city.getCityCode() + "-" + String.format("%06d", city.getSequence());

                id = id + "-" + calcCheckSum(city.getCityCode() + "-" + String.format("%06d", city.getSequence()));

                id = id + "-" + city1.trim().toLowerCase();
            if (city2 != null) {
                id = id + "-" + cityB.getCityCode();
            }
        }
        System.out.println("City Id: " + id);
        //33-000009-J-bordeaux-06
        String[] codeArray = {"C1", "S1","CS","CN","C2"};

        String[] idComponents = id.split("-");

        System.out.println("Components Array " + Arrays.toString(idComponents));
        String finalString = "";
        try {
            Pair<List<Integer>, List<String>> result = findPositions(codeArray, idPattern);

            System.out.println("idPattern: " + idPattern);
            System.out.println("Positions: " + result.first);
            System.out.println("Separators: " + result.second);

            finalString = createFinalString(result.first, result.second, idComponents);
            System.out.println("Final String: " + finalString);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
             finalString = e.getMessage();

        }
        return finalString;
    }

    public static Pair<List<Integer>, List<String>> findPositions(String[] codeArray, String codeString) {
        List<String> codes = Arrays.asList(codeArray);
        List<Integer> positions = new ArrayList<>();
        List<String> separators = new ArrayList<>();

        Pattern pattern = Pattern.compile("([a-zA-Z0-9]{2,})([^a-zA-Z0-9])?");
        Matcher matcher = pattern.matcher(codeString);

        while (matcher.find()) {
            String code = matcher.group(1);
            String separator = matcher.group(2);

            // Validation: Ensure the group is exactly 2 characters and exists in the codes array
            if (code.length() != 2 || !codes.contains(code)) {
                throw new IllegalArgumentException("Invalid pattern found: " + code);
            }

            int index = codes.indexOf(code);
            positions.add(index);

            if (separator != null) {
                separators.add(separator);
            }
        }

        return new Pair<>(positions, separators);
    }


    public static String createFinalString(List<Integer> positions, List<String> separators, String[] idComponents) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < positions.size(); i++) {
            sb.append(idComponents[positions.get(i)]);
            if (i < separators.size()) {
                sb.append(separators.get(i));
            }
        }
        return sb.toString();
    }
}

class Pair<T, U> {
    public final T first;
    public final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}



