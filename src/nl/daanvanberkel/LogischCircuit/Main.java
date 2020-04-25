package nl.daanvanberkel.LogischCircuit;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        CircuitController controller = new CircuitController();

        controller.buildCircuitFromFile("/Users/daanvanberkel/Desktop/Circuits/Circuit2_Decoder.txt");
    }
}
