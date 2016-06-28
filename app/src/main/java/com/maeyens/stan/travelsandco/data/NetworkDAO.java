package com.maeyens.stan.travelsandco.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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
        String data = properties.getProperty("get_user") + ":" + email;
        ByteBuffer buf = ByteBuffer.allocate(BUFSIZE);
        buf.clear();
        buf.put(data.getBytes());
        buf.flip();
        while(buf.hasRemaining()) {
            try {
                openConnection();
                socketChannel.write(buf);
                System.out.println(data);
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void addLogin(String email, String pwd) {

    }

    @Override
    public List<Travel> getTravels() {
        return null;
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
}
