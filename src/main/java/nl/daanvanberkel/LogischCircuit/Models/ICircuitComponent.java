package nl.daanvanberkel.LogischCircuit.Models;

import java.util.Map;

public interface ICircuitComponent {
    Map<ICircuitComponent, Boolean> getResult();

    void setValue(ICircuitComponent sender, Boolean value);

    String getName();

    void setName(String name);

    String getType();
}
