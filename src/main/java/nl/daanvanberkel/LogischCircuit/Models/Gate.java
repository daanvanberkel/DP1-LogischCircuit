package nl.daanvanberkel.LogischCircuit.Models;

public abstract class Gate extends Node {

    protected abstract boolean computeResult();

    protected void pushOutputToNextNodes() {
        boolean result = computeResult();

        for(Node node : outputNodes) {
            node.setInputValueFor(this, result);
        }
    }

    @Override
    public void setInputValueFor(Node node, boolean value) {
        super.setInputValueFor(node, value);

        if (hasValueForAllInputs()) {
            pushOutputToNextNodes();
        }
    }
}
