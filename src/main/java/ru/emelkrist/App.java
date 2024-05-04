package ru.emelkrist;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // run with "invokeLater" for thread saving
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new NotepadGUI().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
