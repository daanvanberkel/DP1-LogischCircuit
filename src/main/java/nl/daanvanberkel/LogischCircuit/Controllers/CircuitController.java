package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.*;
import nl.daanvanberkel.LogischCircuit.Views.CircuitView;
import nl.daanvanberkel.LogischCircuit.Views.MainView;
import nl.daanvanberkel.LogischCircuit.Views.NodeView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircuitController extends MouseAdapter {
    private final DrawCircuitFacade drawCircuitFacade = new DrawCircuitFacade();
    private MainView main;
    private CircuitView circuitView;

    public void showCircuit(String path) {
        drawCircuitFacade.createCircuit(path);

        if(drawCircuitFacade.getErrorMessage() != ""){
            main.showError(drawCircuitFacade.getErrorMessage());
            return;
        }

        circuitView = new CircuitView(drawCircuitFacade.getCircuit(), this);
        circuitView.drawCircuit();

        main.showCircuit(circuitView);
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
                drawCircuitFacade.getCircuit().start();
                circuitView.repaint();
            }
        }
    }
}
