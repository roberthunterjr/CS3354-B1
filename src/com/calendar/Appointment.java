package com.calendar;


import javax.swing.*;
import java.awt.*;

public class Appointment {

    private JPanel panel1;
    private JPanel calendar;

    public static void main(String[] av) {
        GridBagConstraints constraints = new GridBagConstraints();
        JFrame f = new JFrame("Appointment Calendar");
        Container pane = f.getContentPane();
        pane.setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.HORIZONTAL;


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
        JTextArea notesArea = new JTextArea("Enter event here", 5, 20);
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
}


