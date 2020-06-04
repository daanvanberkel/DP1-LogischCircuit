package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;
import nl.daanvanberkel.LogischCircuit.Models.ICircuitComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

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

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int recWidth = getWidth();
        int recHeight = getHeight();

        URL imageUrl = getClass().getResource("/images/" + node.getType() + ".png");

        if (imageUrl == null) {
            // Draw rectangle around the node
            g2.drawRect(getX(), getY(), recWidth, recHeight);

            // Draw name and type of the node in the rectangle
            g2.drawString(node.getName() + " " + node.getType(), getX() + NODE_PADDING, getY() + (recHeight - NODE_PADDING));
        } else {
            try {
                Image image = ImageIO.read(imageUrl);
                Image scaledImg = image.getScaledInstance(recWidth, recHeight, Image.SCALE_AREA_AVERAGING);
                g2.drawImage(scaledImg, getX(), getY(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Draw name of the node above the image
            g2.drawString(node.getName(), getX() + NODE_PADDING, getY() - NODE_PADDING);
        }
    }
}
