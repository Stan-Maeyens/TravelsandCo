package com.maeyens.stan.travelsandco.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stan on 12-Jun-16.
 */
public class DummyDAO implements TravelsandCoDAO {
    private static TravelsandCoDAO dao;
    private List<Transaction> transactions;

    private DummyDAO(){
        Transaction t1 = new Transaction(1, "extra in pot", 25, "Stan");
        Transaction t2 = new Transaction(2, "drinken", -12.25, "Thomas");
        transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
    }

    public static TravelsandCoDAO getInstance(){
        if(dao == null){
            dao = new DummyDAO();
        }
        return dao;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public Transaction getTransaction(int id) {
        for(Transaction t: transactions){
            if(t.getID() == id)
                return t;
        }
        return null;
    }
}
