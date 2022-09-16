package com.johnson.KenyaCountiesDatasetApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "counties")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Counties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long county_id;

    @Column(name = "county_name")
    private String countyName;

    @Column(name = "county_code")
    private String countyCode;

    @Column(name = "county_total_constituencies")
    private Integer totalConstituencies;

    @Column(name = "county_total_wards")
    private Integer totalWards;

    @OneToMany()
    @JoinColumn(name = "constituency_county_id", referencedColumnName = "county_id")
    private List<Constituencies> constituencies;

//    constructor
    public Counties(String county_name, String county_code, int county_total_constituencies, int county_total_wards){
        super();
        this.countyName = county_name;
        this.countyCode = county_code;
        this.totalConstituencies = county_total_constituencies;
        this.totalWards = county_total_wards;
    }

}
