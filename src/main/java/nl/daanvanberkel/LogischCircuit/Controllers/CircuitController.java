package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.*;
import nl.daanvanberkel.LogischCircuit.Views.CircuitView;
import nl.daanvanberkel.LogischCircuit.Views.MainView;

import javax.swing.*;

public class CircuitController {
    private final ReadFileController readFileController = new ReadFileController();
    private MainView main;

    public void showCircuit(String path) {
        Circuit circuit = readFileController.buildCircuitFromFile(path);

        CircuitView circuitView = new CircuitView(circuit);
        JPanel circuitPanel = circuitView.drawCircuit();

        main.showCircuit(circuitPanel);
    }

    public void showWindow(){
        main = new MainView(this);
    }
}
