package org.sid.cinema_back.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private double duree;
    private String realisateur, description, photo;
    private Date dateSortie;
    @OneToMany(mappedBy = "film")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<org.sid.cinema_back.entities.projection> projections;
    @ManyToOne
    private org.sid.cinema_back.entities.categorie categorie;
}
