package com.maeyens.stan.travelsandco.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stan on 12-Jun-16.
 */
public class DummyDAO implements TravelsandCoDAO {
    private static TravelsandCoDAO dao;
    private List<Transaction> transactions;
    private Map<String, String> logins;
    private String userID;

    private DummyDAO(){
        logins = new HashMap<>();
        logins.put("yrago09@gmail.com", "Stanflicka1");

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
    public boolean checkLogin(String email, String pwd) {
        return logins.containsKey(email) && logins.get(email).equals(pwd);
    }

    @Override
    public void addLogin(String email, String pwd) {
        logins.put(email, pwd);
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
