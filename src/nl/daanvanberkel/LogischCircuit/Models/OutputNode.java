package nl.daanvanberkel.LogischCircuit.Models;

public class OutputNode extends Node {
    @Override
    public void setInputValueFor(Node node, Boolean value) {
        System.out.println("OUTPUT NODE '" + getName() + "' RECEIVED: " + value);
    }
}
