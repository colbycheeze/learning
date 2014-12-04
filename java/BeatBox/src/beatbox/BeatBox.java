/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beatbox;

import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BeatBox {

    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;
    JFrame theFrame;
    JLabel tempoLabel;
    JList incoming;
    JTextField outgoing;
    Vector<String> listVector = new Vector<>();
    //ArrayList<String> incomingList = new ArrayList<>();
    String userName;
    HashMap<String, boolean[]> otherSeqsMap = new HashMap<>();
    
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    
    ObjectOutputStream out;
    ObjectInputStream in;

    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", 
       "Open Hi-Hat","Acoustic Snare", "Crash Cymbal", "Hand Clap", 
       "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", 
       "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", 
       "Open Hi Conga"};
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    

    public static void main (String[] args) {
        if(args.length >= 1){
            new BeatBox().startUp(args[0]);
        } else {
            new BeatBox().startUp("AnonymousUser");
        }
    }
    
    public void startUp(String name){
        userName = name;
        System.out.println(userName);
        
        try{
            Socket sock = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(sock.getOutputStream());
            in = new ObjectInputStream(sock.getInputStream());
            Thread remote = new Thread(new RemoteReader());
            remote.start();
        } catch(IOException e){
            System.out.println("Could not connect to server!");
            e.printStackTrace();
        }
        
        buildGUI();
        setUpMidi();
    }
    
    public class RemoteReader implements Runnable{

        boolean[] checkboxState = null;
        String nameToShow = null;
        Object obj = null;
        
        @Override
        public void run(){
            try{
                while((obj=in.readObject()) != null) {
                    System.out.println("got an object from server!");
                    System.out.println(obj.getClass());
                    
                    nameToShow = (String) obj;
                    checkboxState = (boolean[]) in.readObject();
                    otherSeqsMap.put(nameToShow, checkboxState);
                    listVector.add(nameToShow);
                    incoming.setListData(listVector);
                }
            } catch(ClassNotFoundException | IOException e){
                e.printStackTrace();
            }
        }
        
    }
  
    public void buildGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
           ex.printStackTrace();
        }
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkboxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        //JPanel buttonBox = new JPanel(new GridLayout(10,1));

        JButton start = new JButton("Start");        
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);         
          
        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);
        
        JButton clear = new JButton("Clear");
        clear.addActionListener(new MyClearListener());
        buttonBox.add(clear);
        
        JButton send = new JButton("Send");
        send.addActionListener(new MySendListener());
        buttonBox.add(send);
        
        tempoLabel = new JLabel("Tempo: ");
        buttonBox.add(tempoLabel);
        
        incoming = new JList();
        incoming.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        incoming.addListSelectionListener(new MyListSelectionListener());
        JScrollPane theList = new JScrollPane(incoming);
        buttonBox.add(theList);
        incoming.setListData(listVector);
        
        outgoing = new JTextField();
        //outgoing.setEditable(false);
        buttonBox.add(outgoing);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
           nameBox.add(new Label(instrumentNames[i]));
        }
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        saveMenuItem.addActionListener(new SaveMenuListener());
        loadMenuItem.addActionListener(new LoadMenuListener());
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        theFrame.setJMenuBar(menuBar);
        
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background);
          
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {                    
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);            
        } // end loop
        
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    } // close methodszx


    public void setUpMidi() {
      try {
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequence = new Sequence(Sequence.PPQ,4);
        track = sequence.createTrack();
        sequencer.setTempoInBPM(120);
        
        tempoLabel.setText("Tempo: " + (int)sequencer.getTempoInBPM());
        
      } catch(MidiUnavailableException | InvalidMidiDataException e) {e.printStackTrace();}
    } // close method
    
    public void clearBoxes() {
        for (int i=0; i<256; i++){
            checkboxList.get(i).setSelected(false);
        }
        
        sequencer.stop();
    }
    
    public boolean[] getCheckBoxArray(){
        boolean[] checkboxState = new boolean[256];
            
            for(int i=0; i<256; i++){
                JCheckBox check = (JCheckBox) checkboxList.get(i);
                if(check.isSelected()){
                    checkboxState[i] = true;
                }
            }
            
            return checkboxState;
    }

    public void buildTrackAndStart() {
      int[] trackList = null;
    
      sequence.deleteTrack(track);
      track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
          trackList = new int[16];
 
          int key = instruments[i];   

          for (int j = 0; j < 16; j++ ) {         
              JCheckBox jc = (JCheckBox) checkboxList.get(j + (16*i));
              if ( jc.isSelected()) {
                 trackList[j] = key;
              } else {
                 trackList[j] = 0;
              }                    
           } // close inner loop
         
           makeTracks(trackList);
           track.add(makeEvent(176,1,127,0,16));  
       } // close outer

       track.add(makeEvent(192,9,1,0,15));      
       try {
           sequencer.setSequence(sequence); 
	     sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);                   
           sequencer.start();
           sequencer.setTempoInBPM(120);
       } catch(Exception e) {e.printStackTrace();}
    } // close buildTrackAndStart method
      
    public class MySendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            try{
                out.writeObject(userName + ": " + outgoing.getText());
                out.writeObject(getCheckBoxArray());
            } catch(IOException e){
                System.out.println("Nope, can't send it bro!");
                e.printStackTrace();
            }
            
            outgoing.setText("");
            
        }      
    }
    
    public class MyListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                String selected = (String) incoming.getSelectedValue();
                if (selected != null){
                    JOptionPane optionPane = new JOptionPane("Hi, I am an option dialog!", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                    JDialog dialog = new JDialog(theFrame, "Click a button", true);
                    dialog.setContentPane(optionPane);
                    dialog.pack();
                    dialog.setVisible(true);
                    
                    boolean[] selectedState = (boolean[]) otherSeqsMap.get(selected);
                    changeSequence(selectedState);
                    sequencer.stop();
                    buildTrackAndStart();
                }
            }
        }
        
    }
           
    public class MyStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    } // close inner class
    
    public class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showSaveDialog(theFrame);
            saveFile(fileOpen.getSelectedFile());
        }
        
        private void saveFile(File file){
            try {
                FileOutputStream fileStream = new FileOutputStream(file);
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(getCheckBoxArray());
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    
    public class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(theFrame);
            loadFile(fileOpen.getSelectedFile());
        }
        
        private void loadFile(File file){
            boolean[] checkboxState = null;
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fileIn);
                checkboxState = (boolean[]) is.readObject();
            } catch(Exception ex) {ex.printStackTrace();}
            
            clearBoxes();
            changeSequence(checkboxState);
        }
    }
    
    public class MyClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            clearBoxes();
        }
    }

    public class MyStopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    } // close inner class

    public class MyUpTempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
	      float tempoFactor = sequencer.getTempoFactor(); 
            sequencer.setTempoFactor((float)(tempoFactor * 1.03));
            tempoLabel.setText("Tempo: " + (int)(sequencer.getTempoInBPM() * tempoFactor));
        }
        
        
     } // close inner class

     public class MyDownTempoListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent a) {
	      float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * .97));
            tempoLabel.setText("Tempo: " + (int)(sequencer.getTempoInBPM() * tempoFactor));
        }
    } // close inner class

     public void changeSequence(boolean[] checkboxState) {
         for(int i=0; i<256; i++){
                JCheckBox checkBox = (JCheckBox)checkboxList.get(i);
                if(checkboxState[i]){
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
            }
     }
     
     public void makeTracks(int[] list) {        
       
       for (int i = 0; i < 16; i++) {
          int key = list[i];

          if (key != 0) {
             track.add(makeEvent(144,9,key, 100, i));
             track.add(makeEvent(128,9,key, 100, i+1));
             
          }
       }
    }
        
    public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        } catch(Exception e) {e.printStackTrace(); }
        return event;
    }

} // close class
