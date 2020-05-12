package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;
import nl.daanvanberkel.LogischCircuit.Models.Circuit;
import nl.daanvanberkel.LogischCircuit.Models.InputNode;
import nl.daanvanberkel.LogischCircuit.Models.Node;
import nl.daanvanberkel.LogischCircuit.Models.OutputNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CircuitView extends JPanel {

    private Circuit circuit;
    private CircuitController controller;
    private HashMap<Integer, ArrayList<Node>> levels;
    private HashMap<Node, NodeView> nodeViews;

    public CircuitView(Circuit circuit, CircuitController controller) {
        setLayout(null);
        this.circuit = circuit;
        this.controller = controller;
    }

    public JPanel drawCircuit() {
        nodeViews = new HashMap<>();
        convertCircuitToLevels(circuit);

        // Loop through all the node levels and create nodeViews
        for(Map.Entry<Integer, ArrayList<Node>> level : levels.entrySet()) {
            for(Node node : level.getValue()) {
                NodeView nodeView = new NodeView(node, controller);
                nodeView.setFont(getFont());
                nodeView.init();
                nodeViews.put(node, nodeView);

                add(nodeView);
            }
        }

        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get window sizes
        int width = getSize().width;
        int height = getSize().height;
        int spaceBetweenNodeLevels = (width / levels.size());

        // Loop through all the node levels and set nodeView in the right place
        for(Map.Entry<Integer, ArrayList<Node>> level : levels.entrySet()) {
            int x = (level.getKey() * spaceBetweenNodeLevels) - spaceBetweenNodeLevels;
            int y = ((height / level.getValue().size()) / 2) - (getFontMetrics(getFont()).getHeight() / 2);

            for(Node node : level.getValue()) {
                NodeView nodeView = nodeViews.get(node);
                nodeView.setBounds(x, y, nodeView.getPreferredSize().width, nodeView.getPreferredSize().height);
                nodeView.paintComponent(g);

                y += height / level.getValue().size();
            }
        }

        // Draw edges between the nodes
        for(Map.Entry<Node, NodeView> nodePoint : nodeViews.entrySet()) {
            Node node = nodePoint.getKey();
            NodeView nodeView = nodePoint.getValue();

            for(Node oNode : node.getOutputNodes()) {
                NodeView oNodeView = nodeViews.get(oNode);

                if (node.getLastResult()) {
                    g2.setColor(Color.GREEN);
                } else {
                    g2.setColor(Color.RED);
                }

                g2.drawLine(nodeView.getX() + nodeView.getWidth(), nodeView.getY() + (nodeView.getHeight() / 2), oNodeView.getX(), oNodeView.getY() + (oNodeView.getHeight() / 2));

                g2.setColor(Color.BLACK);
            }
        }
    }

    private void convertCircuitToLevels(Circuit circuit) {
        levels = new HashMap<>();

        levels.put(1, new ArrayList<>());

        for(InputNode node : circuit.getInputNodes()) {
            levels.get(1).add(node);

            addCircuitLevel(node, 2);
        }

        Integer highestLevel = levels.size();

        // Add all output nodes to the highest level
        for(OutputNode node : circuit.getOutputNodes()) {
            Integer currentLevel = getNodeLevel(node);

            if (currentLevel != null && currentLevel < highestLevel) {
                levels.get(currentLevel).remove(node);
                levels.get(highestLevel).add(node);
            }
        }
    }

    private void addCircuitLevel(Node node, int level) {
        if (node.getOutputNodes().size() < 1) {
            return;
        }

        if (levels.get(level) == null) {
            levels.put(level, new ArrayList<>());
        }
        
        for(Node n : node.getOutputNodes()) {
            // Check if node is already in a level
            Integer nodeLevel = getNodeLevel(n);
            if (nodeLevel != null) {
                // If node is already in the list, but at a lower level -> move the node up to the right level
                if (nodeLevel < level) {
                    levels.get(nodeLevel).remove(n);
                    levels.get(level).add(n);
                }
            } else {
                levels.get(level).add(n);
            }

            addCircuitLevel(n, level + 1);
        }
    }

    private Integer getNodeLevel(Node node) {
        Iterator<Integer> iterator = levels.keySet().iterator();

        while(iterator.hasNext()) {
            Integer level = iterator.next();

            if (levels.get(level).contains(node)) {
                return level;
            }
        }

        return null;
    }
}
