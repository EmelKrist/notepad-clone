package ru.emelkrist;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.channels.FileChannel;

public class NotepadGUI extends JFrame {
    // file explorer
    private JFileChooser fileChooser;
    private JTextArea textArea;
    private File currentFile;

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
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewFile();
            }
        });
        fileMenu.add(newMenuItem);

        // "open" functionality - open a text file
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
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
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo горячую клавишу для сохранения
                saveFile();
            }
        });
        fileMenu.add(saveMenuItem);

        // "exit" functionality - ends programs process
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // dispose of this GUI
                NotepadGUI.this.dispose();
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    /**
     * Method to save current file.
     */
    private boolean saveFile() {
        // if the current file is null then we have to perform save as functionality
        if (currentFile == null) {
            return saveFileAs();
        }

        try {
            // write to current file
            FileWriter fileWriter = new FileWriter(currentFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(textArea.getText());
            bufferedWriter.close();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to open a file.
     */
    private void openFile() {
        try {
            // open file explorer
            int result = fileChooser.showOpenDialog(NotepadGUI.this);
            if (result != JFileChooser.APPROVE_OPTION) return;
            // reset notepad
            boolean reset = createNewFile();
            if (!reset) return;
            // get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            // read the file
            FileReader fileReader = new FileReader(selectedFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // store the text
            StringBuilder fileText = new StringBuilder();
            String readText;
            while ((readText = bufferedReader.readLine()) != null) {
                fileText.append(readText).append("\n");
            }
            bufferedReader.close();
            fileReader.close();
            // update textarea
            textArea.setText(fileText.toString());
            // update title header
            setTitle(selectedFile.getName());
            // update current file
            currentFile = selectedFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for creating a new file.
     * Note: contains confirmation of saving the current file
     */
    private boolean createNewFile() {
        // if "exit" or "cancel" was selected, the creation isn't confirmed
        boolean create = false;
        // saving confirmation message if textarea is not blank and there is no Notepad title in the frame
        if (!textArea.getText().isBlank() || !getTitle().equals("Notepad")) {
            int confirmationResult = JOptionPane.showConfirmDialog(NotepadGUI.this, "Do you want to save?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (confirmationResult) {
                case JOptionPane.YES_OPTION -> {
                    // if "yes" was selected, the creation is confirmed, but only if the save is successful
                    create = saveFile();
                    break;
                }
                case JOptionPane.NO_OPTION -> {
                    // if "no" was selected, the creation is confirmed, but without saving
                    create = true;
                    break;
                }
            }
        } else {
            // reset if textarea is blank and not a file
            create = true;
        }

        // if creation is confirmed
        if (create) {
            // reset title header
            setTitle("Notepad");
            // reset text area
            textArea.setText("");
            // reset current file
            currentFile = null;
        }
        return create;
    }

    /**
     * Method for saving a file with a specific extension.
     *
     * @return the saving process is successful of not
     */
    private boolean saveFileAs() {
        // open save dialog
        int result = fileChooser.showSaveDialog(NotepadGUI.this);
        if (result != JFileChooser.APPROVE_OPTION) return false;
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
            // update current file
            currentFile = selectedFile;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
