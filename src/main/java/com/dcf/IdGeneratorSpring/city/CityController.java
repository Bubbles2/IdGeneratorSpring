package com.dcf.IdGeneratorSpring.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return cityService.getCities();
    }

    //
    @GetMapping("/cityId")
    public ResponseEntity generateId(@RequestParam String city1, @RequestParam String city2) {
        String id = cityService.generateId(city1, city2);
        System.out.println("city1: " + city1);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Handles a GET request to generate an ID based on the given city parameters.
     *
     * @param city1     The first city for which an ID is to be generated. This parameter is required.
     * @param cityCode  This value will be used as the city code if  we need to create the city. This parameter is optional.
     *                  If it is not provided in the request "***" will be used.
     * @param cityCode2 This value will be used as the city code 2 if  we need to create the city 2. This parameter is optional.
     *                  If it is not provided in the request "***" will be used.
     * @param separator This value will be used seperate elements of the id. This parameter is optional.
     *                  If it is not provided in the request "-" will be used.
     * @param addChksum A boolean flag to indicate if a checksum generated letter is to be added to the generated ID.
     *                  This parameter is optional. If it is not provided in the request, its value defaults to true.
     * @param useName   A boolean flag to indicate if we use the city name in the id.
     *                  This parameter is optional. If it is not provided in the request, its value defaults to false.
     * @param city2     The second city for which an ID is to be generated. This parameter is optional.
     *                  If it is not provided in the request obviously city2 parameter values will not be included
     *                  in the id.
     * @param sex       Should we add a sex flag to id. This parameter is optional.
     *                  If it is not provided in the request obviously sex  will not be included
     *                  in the id.
     * @param dob       Should we add date of birth  to id. This parameter is optional.
     * @param tob       Should we add time  of birth  to id. This parameter is optional.
     * @param version   An integer to indicate the version of the ID generation algorithm to be used.
     *                  This parameter is optional. If it is not provided in the request, its value defaults to 1.
     *                  It is not implemented but may be used in the future to indicate the version of the ID
     * @return A ResponseEntity object containing the generated ID. ie. City Id: 33-000009-J-bordeaux-06-M-16/04/60-15:45  The ID is based on the
     * given city names and other parameters ie . The HTTP status code in the ResponseEntity object
     * indicates the success of the ID generation operation.
     * <p>
     * Note: This method uses the cityService's generateId method to generate the ID based on the
     * city parameters and other request parameters. If any error occurs during this process, it may throw an exception.
     */
    @GetMapping("/cityIdTwo")
    public ResponseEntity generateIdTwo(@RequestParam String city1, @RequestParam(required = false, defaultValue = "***") String cityCode, @RequestParam(required = false, defaultValue = "*2*") String cityCode2, @RequestParam(required = false, defaultValue = "-") String separator,
                                        @RequestParam(required = false, defaultValue = "false") boolean useName, @RequestParam(required = false, defaultValue = "true") boolean addChksum, @RequestParam(required = false) String city2, @RequestParam(required = false) String sex,
                                        @RequestParam(required = false) String dob, @RequestParam(required = false) String tob, @RequestParam(required = false, defaultValue = "1") String version) {
        String id = cityService.generateIdTwo(city1, cityCode, cityCode2, separator, useName, addChksum, city2, sex, dob, tob, version);
        System.out.println("city1: " + city1);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

 /*   We define a pattern  based on possible components.
    C1 City 1 Code
    CN City 1 name
    S1 City 1 sequence
    CS Checksum Letter
    C2 City 2 code

    We also use whatever separator or blank we use in the pattern. We validate the values used and return an error if required.
    Below is an example of the pattern and results for

    http://localhost:8080/api/v1/cityIdThree?city1=Paris&city2=Marseille>

    C1-CN-S1 CS/C2     = 01-paris-000059 P/12
    C1-S1 CS/C2 =  01-000060 W/12
    C2 CN-C1 =  12 paris-01
    Validation
    C1-CN-S1 CRR/C2 = Invalid pattern found: CRR
    C1R-CN-S1 C/C2 =  Invalid pattern found: C1R
 */
    @GetMapping("/cityIdThree")
    public ResponseEntity generateIdThree(@RequestParam String city1,@RequestParam String  city2, @RequestParam String idPattern) {
        String id = cityService.generateIdThree(city1, city2, idPattern);
        System.out.println("city1: " + city1);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping
    public void addCity(@RequestBody City city) {
        cityService.addCity(city);
    }
}

