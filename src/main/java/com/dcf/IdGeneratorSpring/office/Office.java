package com.dcf.IdGeneratorSpring.office;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table
public class Office {
    @Id
    @SequenceGenerator(
            name = "office_sequence",
            sequenceName = "office_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "office_sequence")
    private Long id;
    private String location;
    private String address;

}
