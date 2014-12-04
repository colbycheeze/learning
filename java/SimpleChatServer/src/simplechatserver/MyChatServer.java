/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechatserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author NEWColbyCheeZe
 */
public class MyChatServer {
    
    ArrayList clientOutputStreams;
    
    public class ClientHandler implements Runnable {
        
        BufferedReader reader;
        Socket sock;
        
        public ClientHandler(Socket clientSocket){
            //set up input stream reader etc
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch(Exception ex){ex.printStackTrace();}
        }
        
        @Override
        public void run(){
            //read in messages from client
            String message;
            try{
                while((message = reader.readLine()) != null){
                    System.out.println("read " + message);
                    tellEveryone(message);
                }             
            }catch(Exception ex) { ex.printStackTrace(); }
        }
    }
    
    public static void main(String args[]){
        new MyChatServer().go();
    }
    
    public void go(){
        //create server socket
        //continually check for clients, add them to the server when connection is established
        
        //set up client handler thread
        
        clientOutputStreams = new ArrayList();
        try{
            ServerSocket serverSock = new ServerSocket(5000);
            while(true){
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);
                
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void tellEveryone(String message) {
        //send messages to all clients
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()){
            try{
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch(Exception ex) { ex.printStackTrace(); }
        }
    }
    
}
