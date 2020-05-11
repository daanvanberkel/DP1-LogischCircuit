package nl.daanvanberkel.LogischCircuit.Models;

public class OutputNode extends Node {
    private OutputHandler outputHandler;

    public void setOutputHandler(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    @Override
    public void setInputValueFor(Node node, boolean value) {
        super.setInputValueFor(node, value);

        if (hasValueForAllInputs() && outputHandler != null) {
            outputHandler.handleOutput(this);
        }
    }

    @Override
    public String getNodeType() { return "OUT"; }

    public boolean getResult() {
        return values.values().iterator().next();
    }
}
