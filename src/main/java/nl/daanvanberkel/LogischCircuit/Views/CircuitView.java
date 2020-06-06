package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Controllers.CircuitController;
import nl.daanvanberkel.LogischCircuit.Models.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CircuitView extends JPanel {

    private Circuit circuit;
    private CircuitController controller;
    private HashMap<Integer, ArrayList<ICircuitComponent>> levels;
    private HashMap<ICircuitComponent, NodeView> nodeViews;

    public CircuitView(Circuit circuit, CircuitController controller) {
        setLayout(null);
        this.circuit = circuit;
        this.controller = controller;
    }

    public JPanel drawCircuit() {
        nodeViews = new HashMap<>();
        convertCircuitToLevels(circuit);

        // Loop through all the node levels and create nodeViews
        for(Map.Entry<Integer, ArrayList<ICircuitComponent>> level : levels.entrySet()) {
            for(ICircuitComponent node : level.getValue()) {
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

        drawNodes(g);
        drawNodeConnections(g);
    }

    private void drawNodes(Graphics g){
        // Get window sizes
        int width = getSize().width;
        int height = getSize().height;
        int spaceBetweenNodeLevels = (width / levels.size());

        // Loop through all the node levels and set nodeView in the right place
        for(Map.Entry<Integer, ArrayList<ICircuitComponent>> level : levels.entrySet()) {
            int x = (level.getKey() * spaceBetweenNodeLevels) - spaceBetweenNodeLevels;
            int y = ((height / level.getValue().size()) / 2) - (getFontMetrics(getFont()).getHeight() / 2);

            for(ICircuitComponent node : level.getValue()) {
                drawNode(node, x, y, g);

                y += height / level.getValue().size();
            }
        }
    }

    private void drawNode(ICircuitComponent node, int x, int y, Graphics g){
        NodeView nodeView = nodeViews.get(node);
        nodeView.setBounds(x, y, nodeView.getPreferredSize().width, nodeView.getPreferredSize().height);
        nodeView.paintComponent(g);
    }

    private void drawNodeConnections(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(Map.Entry<ICircuitComponent, NodeView> nodePoint : nodeViews.entrySet()) {
            ICircuitComponent node = nodePoint.getKey();
            NodeView nodeView = nodePoint.getValue();

            if (!(node instanceof CircuitComposite)) {
                continue;
            }

            CircuitComposite composite = (CircuitComposite) node;

            for(ICircuitComponent oNode : composite.getChildren()) {
                NodeView oNodeView = nodeViews.get(oNode);

                if (composite.getLastResult()) {
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

        for(ICircuitComponent node : circuit.getChildren()) {
            levels.get(1).add(node);

            addCircuitLevel(node, 2);
        }
    }

    private void addCircuitLevel(ICircuitComponent node, int level) {
        if (!(node instanceof CircuitComposite)) {
            return;
        }

        CircuitComposite composite = (CircuitComposite) node;

        if (composite.getChildren().size() < 1) {
            return;
        }

        if (levels.get(level) == null) {
            levels.put(level, new ArrayList<>());
        }
        
        for(ICircuitComponent n : composite.getChildren()) {
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

    private Integer getNodeLevel(ICircuitComponent node) {
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
