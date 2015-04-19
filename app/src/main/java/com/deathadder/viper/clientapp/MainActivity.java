package com.deathadder.viper.clientapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{   //Client

    private boolean connectionFlag = false;
    private Socket socket;
    Button connect;
    TextView ip, port;
    OutputStream outputStream;
    int array[];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = (Button) findViewById(R.id.connect);

        Button up = (Button) findViewById(R.id.up);
        Button down = (Button) findViewById(R.id.down);
        Button left = (Button) findViewById(R.id.left);
        Button right = (Button) findViewById(R.id.right);

        connect.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        ip = (TextView) findViewById(R.id.ip);
        port = (TextView) findViewById(R.id.port);

        array = new int[100];

    }

    public void connectToServer(){
        int portNumber = 0;
        String address = ip.getText().toString();
        try {
            portNumber = Integer.parseInt(port.getText().toString());
        }
        catch (NumberFormatException e){
            //Toast.makeText(this,"Enter valid address 1", Toast.LENGTH_LONG).show();
            connectionFlag = false;
        }
        if(address.isEmpty() || portNumber <1024 ){
            //Toast.makeText(this,"Enter valid address 2", Toast.LENGTH_LONG).show();
            connectionFlag = false;
        }

        Socket attempToConnect = null;
        try {
            attempToConnect = new Socket(address,portNumber);
            this.socket = attempToConnect;
            this.outputStream = socket.getOutputStream();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connect.setText("Disconnect");
                }
            });
            connectionFlag = true;

        } catch (IOException e) {
            Log.d("connectToServer", "Unable to connect");
            connectionFlag = false;
        }
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(this,"Something clicked", Toast.LENGTH_LONG).show();

        if(v.getId() != R.id.connect && connectionFlag == false){
            Toast.makeText(this,"Please connect to server first", Toast.LENGTH_LONG).show();
            return;
        }
        if(v.getId() == R.id.connect) {
            if (connectionFlag == false) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connectToServer();
                    }
                }).start();
            }

            else{
                try {
                    outputStream.write(5);
                    outputStream.close();
                    socket.close();
                    connectionFlag = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            connect.setText("Connect");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


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
