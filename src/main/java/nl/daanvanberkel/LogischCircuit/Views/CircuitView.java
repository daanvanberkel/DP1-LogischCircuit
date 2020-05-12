package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Models.Circuit;
import nl.daanvanberkel.LogischCircuit.Models.InputNode;
import nl.daanvanberkel.LogischCircuit.Models.Node;
import nl.daanvanberkel.LogischCircuit.Models.OutputNode;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CircuitView extends JPanel {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 500;

    private Circuit circuit;
    private HashMap<Integer, ArrayList<Node>> levels;

    public CircuitView(Circuit circuit) {
        setLayout(new BorderLayout());
        this.circuit = circuit;
    }

    public JPanel drawCircuit() {
        convertCircuitToLevels(circuit);

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph("circuit");
        graph.addAttribute("ui.stylesheet", "node { shape: freeplane; fill-color: white; stroke-mode: plain; size-mode: fit; } edge { shape: freeplane; }");

        for(Map.Entry<Integer, ArrayList<Node>> level : levels.entrySet()) {
            int x = level.getKey() * (WINDOW_WIDTH / levels.size());
            int y = 0;

            for(Node node : level.getValue()) {
                org.graphstream.graph.Node gNode = graph.addNode(node.getName());
                gNode.addAttribute("xy", x, y);
                gNode.addAttribute("ui.label", node.getName() + " " + node.getNodeType());

                y += (WINDOW_HEIGHT / level.getValue().size());
            }
        }

        addEdges(graph);

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.disableAutoLayout();
        ViewPanel view = viewer.addDefaultView(false);
        add(view);

        return this;
    }

    private void addEdges(Graph graph) {
        for(Map.Entry<Integer, ArrayList<Node>> level : levels.entrySet()) {
            for(Node node : level.getValue()) {
                for(Node child : node.getOutputNodes()) {
                    if (graph.getEdge(node.getName() + child.getName()) == null) {
                        graph.addEdge(node.getName() + child.getName(), node.getName(), child.getName());
                    }
                }
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
