package com.maeyens.stan.travelsandco.data;

import java.util.List;

/**
 * Created by Stan on 12-Jun-16.
 */
public interface TravelsandCoDAO {

    public boolean checkLogin(String email, String pwd);
    public boolean addLogin(String email, String pwd, String name);
    public List<Travel> getTravels();
    public List<Transaction> getTransactions();
    public Transaction getTransaction(int id);
}
