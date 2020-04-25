package nl.daanvanberkel.LogischCircuit.Views;

import nl.daanvanberkel.LogischCircuit.Models.Circuit;
import nl.daanvanberkel.LogischCircuit.Models.InputNode;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class CircuitView extends JPanel {
    private Circuit circuit;
    private NodeView[] nodeViews;
    private JFrame frame;

    public CircuitView(Circuit circuit) {
        this.circuit = circuit;

        setPreferredSize(new Dimension(1000, 1000));
        setLayout(new GridLayout());

        frame = new JFrame();
        frame.setTitle("LogischCircuit");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void drawCircuit() {
        Graph graph = new SingleGraph("circuit");

        int i = 1;
        for (InputNode node : circuit.getInputNodes()) {
            Node gNode = graph.addNode(node.getName());
            gNode.addAttribute("node", node);
            gNode.setAttribute("xy", 1, i);
            gNode.addAttribute("ui.label", node.getName());
            addOutputNodes(node, graph, 2);
            i++;
        }

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel view = viewer.addDefaultView(false);
        add(view);

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addOutputNodes(nl.daanvanberkel.LogischCircuit.Models.Node node, Graph graph, int x) {
        int i = 1;
        for(nl.daanvanberkel.LogischCircuit.Models.Node child : node.getOutputNodes()) {
            System.out.println("Adding node " + child.getName() + ", x: " + x + ", y: " + i);

            if (graph.getNode(child.getName()) == null) {
                Node gNode = graph.addNode(child.getName());
                gNode.addAttribute("node", child);
                gNode.setAttribute("xy", x, i);
                gNode.addAttribute("ui.label", child.getName());
            } else {
                System.out.println("Node " + child.getName() + " already exists");
            }

            if (graph.getEdge(node.getName() + child.getName()) == null) {
                graph.addEdge(node.getName() + child.getName(), node.getName(), child.getName());
            }

            addOutputNodes(child, graph, x + 1);

            i++;
        }
    }
}
