package nl.daanvanberkel.LogischCircuit.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NandGateTests {

    @Test
    public void nandGateShouldReturnTrueWhenAllInputsAreFalse() {
        NandGate input1 = new NandGate();
        NandGate input2 = new NandGate();

        NandGate gate = new NandGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, false);
        gate.setValue(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void nandGateShouldReturnTrueWhenOneInputIsTrue() {
        NandGate input1 = new NandGate();
        NandGate input2 = new NandGate();

        NandGate gate = new NandGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, false);

        assertTrue(gate.computeResult());
    }

    @Test
    public void nandGateShouldReturnFalseWhenAllInputsAreTrue() {
        NandGate input1 = new NandGate();
        NandGate input2 = new NandGate();

        NandGate gate = new NandGate();
        gate.addChild(input1);
        gate.addChild(input2);
        gate.setValue(input1, true);
        gate.setValue(input2, true);

        assertFalse(gate.computeResult());
    }

    @Test
    public void nandGateShouldReturnRightType() {
        NandGate gate = new NandGate();

        assertEquals("NAND", gate.getType());
    }
}
