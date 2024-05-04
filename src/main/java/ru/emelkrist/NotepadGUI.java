package ru.emelkrist;

import javax.swing.*;
import java.awt.*;

public class NotepadGUI extends JFrame {
    public NotepadGUI() {
        super("Notepad");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addGuiComponents();
    }

    /**
     * Method that adds GUI components to the frame.
     */
    private void addGuiComponents() {
        addToolBar();

        // area to type text into
        JTextArea textArea = new JTextArea();
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
        fileMenu.add(saveAsMenuItem);

        // "save" functionality - saves text into current text file
        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        // "exit" functionality - ends programs process
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }


}
