package com.johnson.KenyaCountiesDatasetApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "wards")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Wards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ward_id;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "ward_code")
    private String wardCode;

    @Column(name = "ward_county_id")
    private Integer wardCountyId;

    @Column(name = "ward_constituency_id")
    private Integer wardConstituencyId;

//    constructor
    public Wards(String wardName, String wardCode, Integer wardCountyId, Integer wardConstituencyId){
        super();
        this.wardName = wardName;
        this.wardCode = wardCode;
        this.wardCountyId = wardCountyId;
        this.wardConstituencyId = wardConstituencyId;
    }
}
