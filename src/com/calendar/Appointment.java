package com.calendar;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener;
import java.util.*;
public class Appointment {

    private JPanel panel1;
    private JPanel calendar;
    public static void main(String[] av) {
        GridBagConstraints constraints = new GridBagConstraints();
        JFrame f = new JFrame("Appointment Calendar");
        Container pane = f.getContentPane();
        pane.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Set up appointment controller
        JTextArea notesArea = new JTextArea("Enter event here", 5, 20);
        notesArea.getDocument().addDocumentListener(new handleTextChange(notesArea));
        AppointmentController ac = new AppointmentController(notesArea);

        //  Create Header

        JLabel Header = new JLabel("CS3354 Appointment App");
        Header.setFont(new Font("Serif",Font.PLAIN, 24));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridwidth = 12;
        constraints.insets = new Insets(20,0,20,0);
        pane.add(Header, constraints);

        // Create Calendar
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.ipadx = 1;
        constraints.ipady = 1;

        pane.add(new calendarBean(), constraints);


        // Create Text Area
        //notesArea = new JTextArea("Enter event here", 5, 20);
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth= 6;
        constraints.gridheight = 6;
        constraints.insets = new Insets(0,10,0,10);
        pane.add(notesArea, constraints);


        //  Create Buttons

        JButton okayButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        constraints.gridx = 3;
        constraints.gridy = 9;
        constraints.gridwidth = 4;
        pane.add(okayButton, constraints);
        constraints.gridx = 6;
        pane.add(cancelButton, constraints);


        f.pack();
        f.setVisible(true);
    }

    public static class AppointmentController {
        private static String stateCurrentDate;
        private static HashMap storage;
        private static JTextArea notes;
        public AppointmentController(JTextArea notes) {
            this.notes = notes;
            stateCurrentDate = "";
            storage = new HashMap();
        }

        public static void updateCurrentDate(String date) {
            // System.out.println("State updated with "+date);
            stateCurrentDate = date;
            updateNotesDisplayArea();
        }
        public static void updateCurrentEvent(String notes) {
            // System.out.println("Current date is "+stateCurrentDate);
            storage.put(stateCurrentDate, notes);
//            updateNotesDisplayArea();
        }
        public static String getCurrentDateState() {
            return stateCurrentDate;
        };

        public static String getCurrentEventState() {
            if(storage.get(stateCurrentDate) != null) {
                System.out.println(storage.get(stateCurrentDate));
                return ""+ storage.get(stateCurrentDate);
            }
            return "Please enter an event here";
        }
        public static void updateNotesDisplayArea() {
            notes.setText(getCurrentEventState());
        }
    }

    public static class handleTextChange implements DocumentListener {
        JTextArea target;
        handleTextChange(JTextArea target){
            this.target = target;
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            // System.out.println("insertTextChange" + target.getText());
            AppointmentController.updateCurrentEvent("" + target.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            // System.out.println("removeTextChange" + target.getText());
            AppointmentController.updateCurrentEvent("" + target.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("changeTextChange");
            //AppointmentController.updateCurrentEvent("change");
        }
    }
}


