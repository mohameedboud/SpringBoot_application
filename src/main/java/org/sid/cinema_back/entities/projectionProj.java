package org.sid.cinema_back.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "p1", types = {projection.class})

public interface projectionProj {
    public Long getId();

    public double getPrix();

    public Date getDateProjection();

    public salle getSalle();

    public film getFilm();

    public seance getSeance();

    public Collection<ticket> getTickets();
}
