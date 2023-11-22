package org.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(generator = "clients_id_generator")
    @GenericGenerator(
            name = "clients_id_generator",
            strategy = "sequence",
            parameters = {
                    @Parameter(name = "sequence_name", value = "clients_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @Column(length = 200, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "client",
            cascade = {CascadeType.DETACH, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Ticket> tickets = new HashSet<>();

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ticket> getTickets() {
        return Collections.unmodifiableSet(tickets);
    }

    public void addTicket(Planet from, Planet to) {
        Ticket ticket = new Ticket(this, from, to);
        tickets.add(ticket);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
