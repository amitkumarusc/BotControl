package com.deathadder.viper.clientapp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by viper on 19/4/15.
 */
public class Connection {
    private boolean connectionFlag = false;
    private Socket socket;
    OutputStream outputStream;
    String address;
    int port;

    public Connection(String ip, int port){
        this.address = ip;
        this.port = port;

    }

    public void connect(){
        try {
            Log.d("Connection","IP:" +address+" PORT: "+port);
            socket = new Socket(address,port);
            outputStream = socket.getOutputStream();
            connectionFlag = true;
        }
        catch (Exception ex){
            connectionFlag = false;
            Log.d("Connection","Connection failed");
        }
    }

    public void moveLeft(){
        try {
            outputStream.write(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveRight(){
        try {
            outputStream.write(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveUp(){
        try {
            outputStream.write(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveDown(){
        try {
            outputStream.write(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            outputStream.write(5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return connectionFlag;
    }
}
