package nl.daanvanberkel.LogischCircuit.Models;

public class XorGate extends Node {
    @Override
    public void setInputValueFor(Node node, Boolean value) {
        values.put(node, value);

        if (hasValueForAllInputs()) {
            sendOutput();
        }
    }

    private boolean calculateResult() {
        Boolean lastValue = null;

        for (boolean value : values.values()) {
            if (lastValue != null && lastValue == value) {
                return false;
            }

            lastValue = value;
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
