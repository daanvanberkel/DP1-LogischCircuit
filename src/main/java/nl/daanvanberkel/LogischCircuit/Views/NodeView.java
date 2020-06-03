package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;
import nl.daanvanberkel.LogischCircuit.Models.ICircuitComponent;

import javax.swing.*;
import java.awt.*;

public class NodeView extends JComponent {
    private static final int NODE_PADDING = 10;

    private final ICircuitComponent node;
    private final CircuitController controller;

    public NodeView(ICircuitComponent node, CircuitController controller) {
        super();
        this.node = node;
        this.controller = controller;
    }

    public ICircuitComponent getNode() {
        return node;
    }

    public void init() {
        String nodeString = node.getName() + " " + node.getType();

        int stringHeight = getFontMetrics(getFont()).getHeight();
        int stringWidth = getFontMetrics(getFont()).stringWidth(nodeString);

        int recWidth = stringWidth + (NODE_PADDING * 2);
        int recHeight = stringHeight + (NODE_PADDING * 2);

        setPreferredSize(new Dimension(recWidth, recHeight));
        setMaximumSize(getPreferredSize());
        addMouseListener(controller);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int recWidth = getWidth();
        int recHeight = getHeight();

        // Draw rectangle around the node TODO: Change to image of node type
        g2.drawRect(getX(), getY(), recWidth, recHeight);

        // Draw name and type of the node in the rectangle
        g2.drawString(node.getName() + " " + node.getType(), getX() + NODE_PADDING, getY() + (recHeight - NODE_PADDING));
    }
}
