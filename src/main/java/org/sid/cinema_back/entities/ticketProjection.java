package org.sid.cinema_back.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj", types = {org.sid.cinema_back.entities.ticket.class})
public interface ticketProjection {
    public long getId();

    public String getNomClient();

    public double getPrix();

    public Integer getCodePayement();

    public boolean getReservee();

    public org.sid.cinema_back.entities.place getPlace();
}
