package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XorGateTests {

    @Test
    public void xorGateShouldReturnFalseWhenAllInputsAreFalse() {
        XorGate input1 = new XorGate();
        XorGate input2 = new XorGate();

        XorGate gate = new XorGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, false);
        gate.setInputValueFor(input2, false);

        assertFalse(gate.computeResult());
    }

    @Test
    public void xorGateShouldReturnTrueWhenOneInputIsTrue() {
        XorGate input1 = new XorGate();
        XorGate input2 = new XorGate();

        XorGate gate = new XorGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void xorGateShouldReturnFalseWhenAllInputsAreTrue() {
        XorGate input1 = new XorGate();
        XorGate input2 = new XorGate();

        XorGate gate = new XorGate();
        gate.addInputNode(input1);
        gate.addInputNode(input2);
        gate.setInputValueFor(input1, true);
        gate.setInputValueFor(input2, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void xorShouldReturnRightType() {
        XorGate gate = new XorGate();

        assertEquals("XOR", gate.getNodeType());
    }

}
