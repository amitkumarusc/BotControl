package com.deathadder.viper.clientapp;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {   //Client
    Button connect;
    TextView ip, port;
    static Connection connection;
    int array[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = (Button) findViewById(R.id.connect);

        /*
        Button up = (Button) findViewById(R.id.up);
        Button down = (Button) findViewById(R.id.down);
        Button left = (Button) findViewById(R.id.left);
        Button right = (Button) findViewById(R.id.right);

        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        */

        connect.setOnClickListener(this);

        ip = (TextView) findViewById(R.id.ip);
        port = (TextView) findViewById(R.id.port);

        array = new int[100];
    }

    public void connectToServer() {
        int portNumber = 0;
        String address = ip.getText().toString();
        try {
            portNumber = Integer.parseInt(port.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter valid address 1", Toast.LENGTH_LONG).show();
            return;
        }
        if (address.isEmpty() || portNumber < 1024) {
            Toast.makeText(this, "Enter valid address 2", Toast.LENGTH_LONG).show();
            return;
        }
        if(connection == null)
            connection = new Connection(address, portNumber);

        new Thread(new Runnable() {
            @Override
            public void run() {
                connection.connect();
            }
        }).start();

    }

    @Override
    public void onClick(View v) {

        Toast.makeText(this, "Something clicked", Toast.LENGTH_LONG).show();

        if (v.getId() == R.id.connect) {
            connectToServer();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if( connection.isConnected()){
                Intent intent = new Intent(this, Tracker.class);
                startActivity(intent);
            }
        }
      /*
        else if(v.getId() == R.id.up){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream.write(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        else if(v.getId() == R.id.down){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream.write(2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        else if(v.getId() == R.id.left){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream.write(3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else if(v.getId() == R.id.right){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        outputStream.write(4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        */
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
