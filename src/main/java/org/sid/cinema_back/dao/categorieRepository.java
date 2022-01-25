package org.sid.cinema_back.dao;


//On utilise Spring Data

import org.sid.cinema_back.entities.categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
//tous les methodes qui son heriter de jpa repository il sont accecessible directement via des API
@CrossOrigin("*")
public interface categorieRepository extends JpaRepository<categorie, Long> {
}
