package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NandGateTests {

    @Test
    public void nandGateShouldReturnTrueWhenAllInputsAreFalse() {
        NandGate input1 = new NandGate();
        NandGate input2 = new NandGate();

        NandGate gate = new NandGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, false);
        gate.setInputValueFor(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void nandGateShouldReturnTrueWhenOneInputIsTrue() {
        NandGate input1 = new NandGate();
        NandGate input2 = new NandGate();

        NandGate gate = new NandGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void nandGateShouldReturnFalseWhenAllInputsAreTrue() {
        NandGate input1 = new NandGate();
        NandGate input2 = new NandGate();

        NandGate gate = new NandGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void nandGateShouldReturnRightType() {
        NandGate gate = new NandGate();

        assertEquals("NAND", gate.getNodeType());
    }
}
