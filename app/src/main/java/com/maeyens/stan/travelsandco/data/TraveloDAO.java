package com.maeyens.stan.travelsandco.data;

import java.util.List;

/**
 * Created by Stan on 12-Jun-16.
 */
public interface TraveloDAO {

    public List<Transaction> getTransactions();
    public Transaction getTransaction(int id);
}
