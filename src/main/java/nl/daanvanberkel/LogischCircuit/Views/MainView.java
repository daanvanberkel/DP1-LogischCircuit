package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

        panel = new JPanel(new BorderLayout());
        frame.setContentPane(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void getCircuit(String path) {
        frame.setTitle("LogischCircuit - " + path);
        controller.showCircuit(path);
    }

    public void showCircuit(JPanel circuit){
        panel.removeAll();
        panel.add(circuit);
        panel.revalidate();
    }

    public void showError(String errorMessage){
        JOptionPane.showMessageDialog(null, errorMessage);
    }
}
