/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplechatclient;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author NEWColbyCheeZe
 */
public class MyChatClient {

    JTextArea incoming;
    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public void go() {
        // set up GUI
        // set up networking
        //create threads

        JFrame frame = new JFrame("My Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        
        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        outgoing = new JTextField(40);
        outgoing.addKeyListener(new OutgoingKeyListener());
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        //add key listener
        panel.add(qScroller);
        panel.add(outgoing);
        panel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        frame.setSize(650, 500);
        frame.setVisible(true);
        

    }

    private void setUpNetworking() {
        // create socket, activate reader and writer
        try{
            sock = new Socket("127.0.0.3", 5000);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        } 
        catch(IOException ex) 
        {
            ex.printStackTrace();
        }
        
    }
    
    private void sendTextToServer(){
        try {
                writer.println(outgoing.getText());
                writer.flush();;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            
            outgoing.setText("");
            outgoing.requestFocus();  
    }

    public class SendButtonListener implements ActionListener {

        @Override //implements
        public void actionPerformed(ActionEvent ev) {
              sendTextToServer();            
        }
    }

    public class OutgoingKeyListener implements KeyListener {

        @Override
        public void keyReleased(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                sendTextToServer();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            //nada
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //nada
        }
    }

    public static void main(String[] args) {
        new MyChatClient().go();
    }

    class IncomingReader implements Runnable {

        @Override
        public void run() {
            //read incoming messages from server            
            String message;
            try{
                while((message = reader.readLine()) != null) {
                    System.out.println("client read " + message);
                    incoming.append(message + "\n");
                }
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
