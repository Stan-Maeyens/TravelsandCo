package com.maeyens.stan.travelsandco.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.SocketHandler;

/**
 * Created by Stan on 28-Jun-16.
 */
public class NetworkDAO implements TravelsandCoDAO {
    private static NetworkDAO dao;
    private static Context context;
    private static SocketChannel socketChannel;
    private static Properties properties;
    private static final int BUFSIZE = 1024;

    private NetworkDAO(Context context){
        this.context = context;
        //get address and port
        properties = new Properties();
        try {
        AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("network.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NetworkDAO getInstance(Context context){
        if(dao==null){
            dao = new NetworkDAO(context);
        }
        return dao;
    }

    @Override
    public boolean checkLogin(String email, String pwd) {
        openConnection();
        sendMessage(properties.getProperty("get_user") + ":" + email);
        String[] parts = readMessage();
        closeConnection();
        if(parts[0].equals(email) && parts[1].equals(pwd))
            return true;
        else
            return false;
    }

    @Override
    public boolean addLogin(String email, String pwd, String name) {
        openConnection();
        sendMessage(properties.getProperty("add_user") + ":" + email + ":" + pwd + ":" + name);
        String[] parts = readMessage();
        closeConnection();
        return Boolean.parseBoolean(parts[0]);
    }

    @Override
    public List<Travel> getTravels() {
        List<Travel> ret = new ArrayList<>();
        openConnection();
        sendMessage(properties.getProperty("get_travels") + ":" + SaveSharedPreference.getUserName(context));
        String[] parts = readMessage();
        for(int i=0; i<Integer.parseInt(parts[0]); i++){
            parts = readMessage();
            List<String> p = new ArrayList<>();
            for(int j=2; j<parts.length; j++){
                p.add(parts[j]);
            }
            Travel t = new Travel(parts[0], parts[1], p);
            ret.add(t);
        }
        closeConnection();
        return ret;
    }

    @Override
    public List<Transaction> getTransactions() {
        return null;
    }

    @Override
    public Transaction getTransaction(int id) {
        return null;
    }

    private void openConnection(){
        //open socketchannel
        try {
            System.out.println("connecting to server");
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(properties.getProperty("server_ip"), Integer.parseInt(properties.getProperty("port"))));
            System.out.println("connected to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        try {
            socketChannel.close();
            System.out.println("disconnected from server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message){
        ByteBuffer buf = ByteBuffer.allocate(BUFSIZE);
        buf.clear();
        buf.put(message.getBytes());
        buf.flip();
        while(buf.hasRemaining()) {
            try {
                socketChannel.write(buf);
                System.out.println("sent message: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String[] readMessage(){
        String message = null;
        ByteBuffer buf = ByteBuffer.allocate(BUFSIZE);
        try {
            int bytesRead = socketChannel.read(buf);
            buf.flip();
            message = new String(buf.array()).trim();
            System.out.println("received message: "+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[]parts = message.split(":");
        return parts;
    }
}
