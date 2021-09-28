package de.epshl.e211.tisch1;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Solver {

    private final SolverVisitorNode end;
    private final Map<Node, SolverVisitorNode> visitorNodes;

    public Solver(Graph graph, Node start, Node end) {
        this.visitorNodes = new HashMap<>(graph.getNodeCount());
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Node n = graph.getNode(i);
            visitorNodes.put(n, new SolverVisitorNode(graph.getNode(i)));
        }
        SolverVisitorNode startNode = this.getNode(start);
        startNode.updateParent(startNode, 0);
        if (end == null) {
            this.end = null;
        } else {
            this.end = this.getNode(end);
        }
    }

    public SolverVisitorNode getLowestNode() {
        SolverVisitorNode lowestVisitorNode = null;
        for (SolverVisitorNode visitorNode : visitorNodes.values()) {
            if (!visitorNode.canBeVisited()) continue;
            if (lowestVisitorNode == null || lowestVisitorNode.getWeight() > visitorNode.getWeight()) {
                lowestVisitorNode = visitorNode;
            }
        }
        if (lowestVisitorNode == null) {
            throw new NoSuchElementException();
        } else {
            return lowestVisitorNode;
        }
    }

    private SolverVisitorNode getNode(final Node node) {
        return visitorNodes.get(node);
    }

    public void step() {
        SolverVisitorNode lowest = this.getLowestNode();
        if (lowest != this.end) {
            for (Edge edge : lowest.getNode().getEdges()) {
                int newWeight = lowest.getWeight() + edge.getWeight();
                SolverVisitorNode targetNode = this.getNode(edge.getNode());
                targetNode.updateParent(lowest, newWeight);
            }
        }
        lowest.setAsVisited();
    }

    public boolean isFinished() {
        if (this.end != null && this.end.isVisited()) {
            return true;
        }
        for (VisitorNode visitorNode : visitorNodes.values()) {
            if (visitorNode.canBeVisited()) {
                return false;
            }
        }
        return true;
    }

    public VisitorNode getVisitorNode(Node node) {
        return this.getNode(node);
    }

    public VisitorNode getVisitorNode() {
        return end;
    }

    private static class SolverVisitorNode implements VisitorNode {
        private int weight;
        private boolean visited = false;
        private VisitorNode parent = null;
        private final Node node;

        public SolverVisitorNode(Node node) {
            this.node = node;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }

        @Override
        public boolean hasWeight() {
            return parent != null;
        }

        public void setAsVisited() {
            this.visited = true;
        }

        @Override
        public boolean isVisited() {
            return this.visited;
        }

        @Override
        public VisitorNode getParent() {
            return parent;
        }

        public void updateParent(VisitorNode parent, int weight) {
            if (!hasWeight() || weight < getWeight()) {
                this.weight = weight;
                this.parent = parent;
            }
        }

        @Override
        public Node getNode() {
            return node;
        }
    }
}
