package com.johnson.KenyaCountiesDatasetApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "constituencies")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Constituencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long constituency_id;

    @Column(name = "constituency_county_id")
    private Integer countyId;

    @Column(name = "constituency_name")
    private String constituencyName;

    @Column(name = "constituency_code")
    private String constituencyCode;

    @Column(name = "constituency_total_wards")
    private Integer constituencyTotalWards;

//    @ManyToOne()
//    @JoinColumn(name = "constituency_county_id")
//    private Counties counties;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ward_constituency_id", referencedColumnName = "constituency_id")
    private List<Wards> wards;

    //constructor
    public Constituencies(int countyId, String constituencyName, String constituencyCode, Integer constituencyTotalWards){
        super();
        this.countyId = countyId;
        this.constituencyName = constituencyName;
        this.constituencyCode = constituencyCode;
        this.constituencyTotalWards = constituencyTotalWards;

    }
}
