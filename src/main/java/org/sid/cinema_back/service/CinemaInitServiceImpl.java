package org.sid.cinema_back.service;

import org.sid.cinema_back.dao.*;
import org.sid.cinema_back.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements org.sid.cinema_back.service.ICinemaInitService {

    @Autowired
    private static villeRepository villeRepository;

    @Autowired
    private villeRepository repository;
    @Autowired
    private cinemaRepository cinemaRepository;
    @Autowired
    private salleRepository salleRepository;
    @Autowired
    private placeRepository placeRepository;
    @Autowired
    private seanceRepository seanceRepository;
    @Autowired
    private categorieRepository categorieRepository;
    @Autowired
    private filmRepository filmRepository;
    @Autowired
    private projectionRepository projectionRepository;
    @Autowired
    private ticketRepository ticketRepository;


    @Override
    public void initVilles() {
        Stream.of("sousse", "monastir", "mahdia", "Kalaa").forEach(nameVille -> {
            ville ville = new ville();
            ville.setName(nameVille);
            repository.save(ville);
        });
    }

    @Override
    public void initCinemas() {

        repository.findAll().forEach(v -> {
            Stream.of("MallOfSousse", "SidiDhaher", "Founoun", "IMAX").forEach((nameCinema -> {
                cinema cinema = new cinema();
                cinema.setName(nameCinema);
                cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                cinema.setVille(v);
                cinemaRepository.save(cinema);
            }));
        });

    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                salle salle = new salle();
                salle.setName("salle" + (i + 1));
                salle.setCinema(cinema);
                salle.setNombrePlace(15 + (int) (Math.random() * 20));
                salleRepository.save(salle);

            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                place place = new place();
                place.setSalle(salle);
                place.setNumero(i + 1);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
            seance seance = new seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void initCategories() {
        Stream.of("Histoire", "Action", "Fiction", "Drama").forEach(c -> {
            categorie categorie = new categorie();
            categorie.setName(c);
            categorieRepository.save(categorie);
        });
    }

    @Override
    public void initFilms() {
        double[] durees = new double[]{1, 1.5, 2, 2.5, 3};
        List<categorie> categories = categorieRepository.findAll();
        Stream.of("Game Of Thrones", "Seigneur des anneaux", "Spider Man", "IRON Man", "Cat Women").forEach(f -> {
            film film = new film();
            film.setTitre(f);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(f.replace(" ", "") + ".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[]{30, 50, 60, 70, 90, 100};
        List<film> films = filmRepository.findAll();

        repository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index = new Random().nextInt(films.size());
                    film film = films.get(index);
                    seanceRepository.findAll().forEach(seance -> {
                        projection projection = new projection();
                        projection.setDateProjection(new Date());
                        projection.setFilm(film);
                        projection.setPrix(prices[new Random().nextInt(prices.length)]);
                        projection.setSalle(salle);
                        projection.setSeance(seance);
                        projectionRepository.save(projection);
                    });

                });
            });
        });

    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(p -> {
            p.getSalle().getPlaces().forEach(place -> {
                ticket ticket = new ticket();
                ticket.setPlace(place);
                ticket.setPrix(p.getPrix());
                ticket.setProjection(p);
                ticket.setReservee(false);
                ticketRepository.save(ticket);
            });
        });

    }

}
