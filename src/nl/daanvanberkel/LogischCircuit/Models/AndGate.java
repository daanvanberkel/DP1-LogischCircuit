package nl.daanvanberkel.LogischCircuit.Models;

public class AndGate extends Node {
    @Override
    public void setInputValueFor(Node node, Boolean value) {
        values.put(node, value);

        if (hasValueForAllInputs()) {
            sendOutput();
        }
    }

    private boolean calculateResult() {
        for (boolean value : values.values()) {
            if (!value) {
                return false;
            }
        }

        return true;
    }

    private void sendOutput() {
        boolean result = calculateResult();

        for(Node node : outputNodes) {
            node.setInputValueFor(this, result);
        }
    }
}
