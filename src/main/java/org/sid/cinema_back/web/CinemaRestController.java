package org.sid.cinema_back.web;

import lombok.Data;
import org.sid.cinema_back.dao.filmRepository;
import org.sid.cinema_back.dao.ticketRepository;
import org.sid.cinema_back.dao.villeRepository;
import org.sid.cinema_back.entities.film;
import org.sid.cinema_back.entities.ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private org.sid.cinema_back.dao.filmRepository filmRepository;
    @Autowired
    private org.sid.cinema_back.dao.ticketRepository ticketRepository;
    @Autowired
    private villeRepository repository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception {
        film f = filmRepository.findById(id).get();
        String photoName = f.getPhoto();
        File file = new File(System.getProperty("user.home") + "/images/" + photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<ticket> payerTickets(@RequestBody TicketForm ticketForm) {
        List<ticket> listTickets = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket -> {
            ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReservee(true);
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }


}

@Data
class TicketForm {
    private String nomClient;
    private int codePayement;
    private List<Long> tickets = new ArrayList<>();
}
