package nl.daanvanberkel.LogischCircuit.Models;

public class NorGate extends Node {
    @Override
    public void setInputValueFor(Node node, Boolean value) {
        values.put(node, value);

        if (hasValueForAllInputs()) {
            sendOutput();
        }
    }

    private boolean calculateResult() {
        boolean lastValue = true;

        for (boolean value : values.values()) {
            if (!value && lastValue) {
                lastValue = false;
            } else {
                return true;
            }
        }

        return false;
    }

    private void sendOutput() {
        boolean result = calculateResult();

        for(Node node : outputNodes) {
            node.setInputValueFor(this, result);
        }
    }
}
