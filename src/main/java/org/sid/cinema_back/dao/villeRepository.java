package org.sid.cinema_back.dao;


//On utilise Spring Data


import org.sid.cinema_back.entities.ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface villeRepository extends JpaRepository<ville, Long> {
}
