package nl.daanvanberkel.LogischCircuit.Controllers;

import nl.daanvanberkel.LogischCircuit.Models.*;
import nl.daanvanberkel.LogischCircuit.Views.CircuitView;

public class CircuitController {
    private final ReadFileController readFileController = new ReadFileController();

    public void showCircuit(String path) {
        Circuit circuit = readFileController.buildCircuitFromFile(path);

        CircuitView circuitView = new CircuitView(circuit);
        circuitView.drawCircuit();
    }
}
