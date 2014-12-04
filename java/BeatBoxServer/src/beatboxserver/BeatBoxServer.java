/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beatboxserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author NEWColbyCheeZe
 */
public class BeatBoxServer {

    ArrayList<ObjectOutputStream> clientOutputStreams;
    
    
    public static void main(String[] args) {
        new BeatBoxServer().go();
    }
    
    public void go(){
        clientOutputStreams = new ArrayList<>();
        
        try{
            ServerSocket serverSock = new ServerSocket(4242);
            
            while(true){
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(out);
                
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                
                System.out.println("Got a connection from " + clientSocket.getInetAddress());
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {

        ObjectInputStream in;
        Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            try {
                clientSocket = socket;
                in = new ObjectInputStream(clientSocket.getInputStream());
                
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            Object o2;
            Object o1;
            
            try{
                while ((o1 = in.readObject()) != null){
                    
                    o2 = in.readObject();
                    
                    System.out.println("read two objects");
                    tellEveryone(o1, o2);
                }
            } catch(ClassNotFoundException | IOException e){
                e.printStackTrace();
            }
        }

        private void tellEveryone(Object o1, Object o2) {
            Iterator it = clientOutputStreams.iterator();
            
            while(it.hasNext()){
                try{
                    ObjectOutputStream out = (ObjectOutputStream) it.next();
                    out.writeObject(o1);
                    out.writeObject(o2);                    
                } catch(IOException e){
                    e.printStackTrace();
                }
                
            }
            
        }
    }
    
}
