package nl.daanvanberkel.LogischCircuit.Models;

public class NandGate extends Node {
    @Override
    public void setInputValueFor(Node node, Boolean value) {
        values.put(node, value);

        if (hasValueForAllInputs()) {
            sendOutput();
        }
    }

    private boolean calculateResult() {
        boolean lastValue = false;

        for (boolean value : values.values()) {
            if (value && !lastValue) {
                lastValue = true;
            } else {
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
