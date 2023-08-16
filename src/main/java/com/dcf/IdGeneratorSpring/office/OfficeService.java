package com.dcf.IdGeneratorSpring.office;
import org.springframework.stereotype.Service;
@Service
public class OfficeService {
    private final OfficeRepository officeRepository;
    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }
    public void saveOffice(Office office){
        officeRepository.save(office);
    }
}
