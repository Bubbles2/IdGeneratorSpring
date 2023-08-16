package com.dcf.IdGeneratorSpring.office;
// This class will act as a controller for the Office class. it will manage a post that will accept a location and address and return an id


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/office")
public class OfficeController {
    OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping
    public void addOffice(@RequestBody Office office){
        System.out.println(office);
        officeService.saveOffice(office);
    }


}
