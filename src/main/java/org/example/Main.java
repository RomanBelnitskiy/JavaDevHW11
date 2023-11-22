package org.example;

import org.example.model.Client;
import org.example.model.Planet;
import org.example.model.Ticket;
import org.example.service.ClientService;
import org.example.service.PlanetService;
import org.example.service.TicketService;
import org.example.util.FlywayUtil;
import org.example.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        FlywayUtil.migrate();

        ticketServiceTest();

        HibernateUtil.close();
    }

    private static void ticketServiceTest() {
        ClientService clientService = new ClientService();
        PlanetService planetService = new PlanetService();
        TicketService ticketService = new TicketService();

        Client client = clientService.create("Rocket");
        Planet earth312 = planetService.create("EARTH312", "Earth 312");
        Planet mars333 = planetService.create("MARS333", "Mars 333");
        Ticket newTicket = ticketService.create(client, earth312, mars333);
        System.out.println("New ticket = " + newTicket);

        Ticket ticketFromDB = ticketService.findById(newTicket.getId());
        System.out.println("Get ticket from database => " + ticketFromDB);

        ticketService.delete(ticketFromDB);
        clientService.delete(client);
        planetService.delete(earth312);
        planetService.delete(mars333);
    }
}