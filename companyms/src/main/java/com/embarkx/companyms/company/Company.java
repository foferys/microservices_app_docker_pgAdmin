package com.embarkx.companyms.company;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Company {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //gestisce l'id in automatio aumentandolo per i nuovi elementi nella tabella
    private Long id;


    private String name;
    private String description;

    //// USATO CON MONOLITICO - mapping tra company e job - 1 company ha piu lavori
    //@JsonIgnore // -> rimuove le chiamate ricorsive infinite
    //@OneToMany(mappedBy = "company") //con mappedBy indichiamo che questa relazione che esiste con job è mappata dal field presente nella classe jobs che si chiama company
    //private List<Job> jobs;
    
    ////relazione tra review e company
    //@OneToMany(mappedBy = "company")
    //private List<Review> reviews;


    public Company() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }





}
