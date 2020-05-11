package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;
import nl.daanvanberkel.LogischCircuit.Models.Circuit;
import org.jfree.util.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainView extends JPanel{
    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel panel;
    private JMenu menu;
    CircuitController controller;

    public MainView(CircuitController circuitcontroller) {
        controller = circuitcontroller;

        frame = new JFrame();
        frame.setTitle("LogischCircuit");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(screenSize.width, screenSize.height));

        menuBar = new JMenuBar();

        menu = new JMenu("Circuits");

        String path = new File("src/Circuits").getAbsolutePath();
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                JMenuItem mi = new JMenuItem(child.getName());
                mi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        getCircuit(child.toString());
                    }
                });
                menu.add(mi);
            }
        }

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);

        panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void getCircuit(String path){
        controller.showCircuit(path);
    }

    public void showCircuit(JPanel circuit){
        panel.removeAll();
        panel.add(circuit);
        panel.revalidate();
    }
}
