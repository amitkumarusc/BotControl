package com.deathadder.viper.clientapp;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by viper on 19/4/15.
 */
public class Connection {
    private static boolean connectionFlag = false;
    private static Socket socket;
    static OutputStream outputStream;
    String address;
    int port;
    private LinkedList queue;

    public Connection(String ip, int port){
        this.address = ip;
        this.port = port;
        this.queue = new LinkedList();
    }

    public void connect(String ip, int port){
        this.address = ip;
        this.port = port;
        try {
            Log.d("Connection","IP:" +address+" PORT: "+port);
            socket = new Socket(address,this.port);
            outputStream = socket.getOutputStream();
            connectionFlag = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    send();
                }
            }).start();
        }
        catch (Exception ex){
            connectionFlag = false;
            Log.d("Connection","Connection failed");
        }
    }

    public void moveLeft(){
        try {
            synchronized (queue) {
                queue.add(3);
                queue.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveRight(){
        try {
            synchronized (queue) {
                queue.add(4);
                queue.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveUp(){
        try {
            synchronized (queue) {
                queue.add(1);
                queue.notify();
            }
            //Log.d("Connection","Moved UP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveDown(){
        try {
            synchronized (queue) {
                queue.add(2);
                queue.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            synchronized (queue) {
                queue.add(5);
                queue.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return connectionFlag;
    }

    public static void setConnected(boolean value){
        connectionFlag = value;
        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void send(){
        while(connectionFlag){
            try{
                synchronized (queue){
                    while(queue.isEmpty()) {
                        queue.wait();
                    }

                    int dir = (int) queue.remove();
                    outputStream.write(dir);
                }
            }
            catch (Exception ex){
                Log.d("Connection","Queue Exception");
                connect(this.address, this.port);
            }
        }
    }
}
