package ru.emelkrist;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class NotepadGUI extends JFrame {
    // file explorer
    private JFileChooser fileChooser;
    private JTextArea textArea;

    public NotepadGUI() {
        super("Notepad");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // file chooser setup
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        addGuiComponents();
    }

    /**
     * Method that adds GUI components to the frame.
     */
    private void addGuiComponents() {
        addToolBar();

        // area to type text into
        textArea = new JTextArea();
        add(textArea, BorderLayout.CENTER);
    }

    /**
     * Method that adds a toolbar with menus.
     */
    private void addToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        // menu bar
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);
        // add menus
        menuBar.add(addFileMenu());
        add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Method that adds a "File" menu in the toolbar
     * with basic functionality items.
     *
     * @return toolbar menu
     */
    private JMenu addFileMenu() {
        JMenu fileMenu = new JMenu("File");

        // "new" functionality - resets everything
        JMenuItem newMenuItem = new JMenuItem("New");
        fileMenu.add(newMenuItem);

        // "open" functionality - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        // "save as" functionality - creates a new text file and saves text
        JMenuItem saveAsMenuItem = new JMenuItem("Save as");
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFileAs();
            }
        });
        fileMenu.add(saveAsMenuItem);

        // "save" functionality - saves text into current text file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        // "exit" functionality - ends programs process
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    /**
     * Method for saving a file with a specific extension.
     */
    private void saveFileAs() {
        // open save dialog
        fileChooser.showSaveDialog(NotepadGUI.this);
        try {
            File selectedFile = fileChooser.getSelectedFile();
            // append txt to the file if it does not have the txt extension yet
            String fileName = selectedFile.getName();
            if (!fileName.substring(fileName.length() - 4).equalsIgnoreCase(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
            // create new file
            selectedFile.createNewFile();
            // write the user's text into the file that we just created
            FileWriter fileWriter = new FileWriter(selectedFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(textArea.getText());
            bufferedWriter.close();
            fileWriter.close();
            // update the title header of gui to save text file
            setTitle(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
