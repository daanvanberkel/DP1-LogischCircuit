package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.*;
import nl.daanvanberkel.LogischCircuit.Views.CircuitView;
import nl.daanvanberkel.LogischCircuit.Views.MainView;
import nl.daanvanberkel.LogischCircuit.Views.NodeView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircuitController extends MouseAdapter {
    private final ReadFileController readFileController = new ReadFileController();
    private MainView main;
    private CircuitView circuitView;
    private Circuit circuit;

    public void showCircuit(String path) {
        circuit = readFileController.buildCircuitFromFile(path);
        circuit.start();

        circuitView = new CircuitView(circuit, this);
        JPanel circuitPanel = circuitView.drawCircuit();

        main.showCircuit(circuitPanel);
    }

    public void showWindow(){
        main = new MainView(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle clicks on NodeView
        if (e.getSource() instanceof NodeView) {
            NodeView view = (NodeView) e.getSource();
            Node node = view.getNode();

            // Only toggle node if the clicked node is an inputNode
            if (node instanceof InputNode) {
                InputNode inputNode = (InputNode) node;

                inputNode.toggleValue();

                // Recalculate the output and repaint the circuit
                circuit.start();
                circuitView.repaint();
            }
        }
    }
}
