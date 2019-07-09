package com.example.maddin.bluepay;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Bluetooth_Connection {
    private static final String TAG = "Bluetooth_Connection";

    private UUID uuid = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private String mac;
    private boolean isInitialized = false;
    private BluetoothSocket bluetoothSocket;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private boolean isConnected = false;

    private InputStream in;
    private PrintWriter out;

    public Bluetooth_Connection(String uuid, String mac) {
        this.uuid = UUID.fromString(uuid);
        this.mac = mac;
        isInitialized = true;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void setAdapter(BluetoothAdapter adapter) {
        bluetoothAdapter = adapter;
    }

    public void connect() {
        if (mac == null || mac.isEmpty()) {
            Log.d(TAG, "Bluetooth-Fehler: keine MAC-Adresse!");
            return;
        }
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);

        /*Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getAddress().equals(mac)) {
                bluetoothDevice = device;
            }
        }*/

        // Socket erstellen
        try {
            bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);
        } catch (Exception e) {
            Log.e(TAG, "Socket-Fehler: Erstellung fehlgeschlagen.");
        }

        bluetoothAdapter.cancelDiscovery();

        // Socket verbinden
        try {
            bluetoothSocket.connect();
            isConnected = true;
        } catch (Exception e) {
            isConnected = false;
            Log.e(TAG, "Socket-Fehler: Keine Verbindung möglich.");
        }

        // Prüfung ob Verbindung aufgebaut ist, falls nicht wird der Socket beendet
        if (!isConnected) {
            try {
                bluetoothSocket.close();
                isConnected = false;
            } catch (Exception e) {
                Log.e(TAG, "Verbindungsfehler: Verbindung abgebrochen.");
            }
        }

        // Initialisierung des Inputstreams
        try {
            in = bluetoothSocket.getInputStream();
        } catch (Exception e) {
            Log.e(TAG, "Konnte Inputstream nicht verbinden");
        }

        //Initialisierung des Outputstreams
        try {
            out = new PrintWriter(new OutputStreamWriter(bluetoothSocket.getOutputStream()));
        } catch (Exception e) {
            Log.e(TAG, "Konnte Outputstream nicht verbinden");
        }
    }

    public void send(String s) {
        byte[] buffer = s.getBytes();

        if (isConnected) {
            try {
                out.write(s);
                out.flush();
            } catch (Exception e) {
                Log.e(TAG, "Inputstream fehlgeschlagen");
            }
        }
    }

    public String receive() {
        byte[] buffer = new byte[1024];
        int length;
        String message = "";

        try {
            if (in.available() > 0) {
                length = in.read(buffer);

                for (int i = 0; i < length; i++) {
                    message += (char) buffer[i];
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Outputstream fehlgeschlagen");
        }

        return message;
    }

    public void disconnect() {
        if (isConnected && out != null) {
            isConnected = false;

            try {
                out.flush();
                bluetoothSocket.close();
            } catch (Exception e) {
                Log.e(TAG, "Fehler beim schließen: Socket konnte nicht geschlossen werden");
            }
        }
    }

    public String getMacAdress() {
        return mac;
    }
}