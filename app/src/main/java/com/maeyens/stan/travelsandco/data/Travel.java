package com.maeyens.stan.travelsandco.data;

import java.util.List;

/**
 * Created by Stan on 20-Jun-16.
 */
public class Travel {
    private String title, id;
    private List<String> people;

    public Travel(String id, String title, List<String> people){
        this.id = id;
        this.title = title;
        this.people = people;
    }

    public List<String> getPeople() {
        return people;
    }

    public String getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addPerson(String person){
        people.add(person);
    }

    public boolean removePerson(String person){
        return people.remove(person);
    }
}
