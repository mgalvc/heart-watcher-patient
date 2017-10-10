/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author matheus
 */
public class Client {
    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    
    public static HashMap<String, Object> send(HashMap<String, Object> message, String addr, int port) throws IOException, ClassNotFoundException {
        socket = new Socket(addr, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        
        out.writeObject(message);
        return (HashMap<String, Object>) in.readObject();
    }
    
    
}
