package com.dclover.gpsutilities.taxi.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

/**
 * Created by Kinghero on 6/7/2016.
 */
public class SocketUtils implements Runnable {
    private static final int BUFFER_SIZE = 1024;
    private static final int THREAD_SLEEP = 500;
    private static final int THREAD_SLEEP_LONG = 5000;
    private static SocketUtils singleInstance;
    private BufferedReader bufferedReader;
    private StringBuilder inputMessageBuffer;
    private InputStream inputStream;
    private boolean isStartThread;
    private OutputStream outputStream;
    private Socket socket;

    private SocketUtils() {
        this.isStartThread = false;
    }

    public static SocketUtils getSingleton() {
        if (singleInstance == null) {
            singleInstance = new SocketUtils();
        }
        return singleInstance;
    }

    public boolean sendMessage(String message) {
        Log.d("dd","send:"+message);
        if (message == null) {
            return false;
        }
        try {
            if (this.socket == null || !this.socket.isConnected()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.socket == null || !this.socket.isConnected()) {
                    initConnect();
                }
            }
            this.outputStream.write(message.getBytes(Charset.defaultCharset()));
            this.outputStream.flush();
            return true;
        } catch (Exception ex) {
            if (!(ex instanceof SocketTimeoutException) && !(ex instanceof SocketException)) {
                return false;
            }
            initConnect();
            return false;
        }
    }

    public static void startServerMessageThread() {
        getSingleton();
        if (!singleInstance.isStartThread) {
            if (singleInstance.inputMessageBuffer != null) {
                singleInstance.inputMessageBuffer.delete(0, singleInstance.inputMessageBuffer.length());
            }
            singleInstance.isStartThread = true;
            new Thread(singleInstance).start();
        }
    }

    public static void stopServerMessageThread() {
        getSingleton();
        singleInstance.isStartThread = false;
    }

    public void run() {
        if (this.socket == null || !this.socket.isConnected()) {
            initConnect();
        }
        boolean checkNewMsg = false;
        char[] charBuffer = new char[BUFFER_SIZE];
        while (this.isStartThread) {
            try {
                if (this.inputStream.available() > 0) {
                    do {
                        int charRead = this.bufferedReader.read(charBuffer);
                        if (charRead <= -1) {
                            break;
                        }
                        this.inputMessageBuffer.append(charBuffer, 0, charRead);
                    } while (this.inputMessageBuffer.indexOf(Constants.CMD_END, 0) <= -1);
                    checkNewMsg = true;
                }
                if (checkNewMsg) {
                    checkNewMsg = false;
                    String serverMsg = this.inputMessageBuffer.toString();
                    String[] arrMsg = serverMsg.split(Constants.CMD_END_REGEX);
                    if (serverMsg.endsWith(Constants.CMD_END)) {
                        for (String command : arrMsg) {
                            ServerCommand.processCommand(command);
                        }
                        this.inputMessageBuffer.delete(0, serverMsg.length());
                    } else if (arrMsg.length > 1) {
                        for (int i = 0; i < arrMsg.length - 1; i++) {
                            ServerCommand.processCommand(arrMsg[i]);
                        }
                        this.inputMessageBuffer.delete(0, serverMsg.length());
                        this.inputMessageBuffer.append(arrMsg[arrMsg.length - 1]);
                    }
                } else {
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                try {
                    if (CommonUtils.isConnectInternet()) {
                        initConnect();
                        Thread.sleep(500);
                    } else {
                        Thread.sleep(Constants.WS_DELAY);
                    }
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    private void initConnect() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            if (this.outputStream != null) {
                this.outputStream.close();
            }
            if (this.bufferedReader != null) {
                this.bufferedReader.close();
            }
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(Constants.SOCKET_SERVER_IP, Constants.SOCKET_SERVER_PORT), THREAD_SLEEP_LONG);
            this.inputStream = this.socket.getInputStream();
            this.outputStream = this.socket.getOutputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
            if (this.inputMessageBuffer == null) {
                this.inputMessageBuffer = new StringBuilder();
            } else if (this.inputMessageBuffer.length() > 0) {
                this.inputMessageBuffer.delete(0, this.inputMessageBuffer.length());
            }
            ClientCommand.doLogin();
        } catch (Exception e) {
        }
    }
}

