package de.epshl.e211.tisch1;

import java.util.Arrays;

public class Graph {

    private final GraphNode[] nodes;

    public Graph(int[][] weights) throws MalformedGraphException {
        // Verify weights
        int nodeCount = weights.length;
        for (int[] row : weights) {
            if (row.length != nodeCount) {
                throw new MalformedGraphException("Nodes have to few or to many edges");
            }
        }

        for (int row = 0; row < nodeCount; row++) {
            if (weights[row][row] != 0) {
                throw new MalformedGraphException("Distance to itself must be 0");
            }
        }

        this.nodes = new GraphNode[nodeCount];
        // First initialize nodes
        for (int i = 0; i < nodeCount; i++) {
            this.nodes[i] = new GraphNode(i, weights[i]);
        }
        // Then setup every node with the other nodes
        for (int i = 0; i < nodeCount; i++) {
            this.nodes[i].setup();
        }
    }

    public int getNodeCount() {
        return nodes.length;
    }

    public Node getNode(int i) {
        return nodes[i];
    }

    private class GraphNode implements Node {

        private final Edge[] edges;
        private final int[] weights;
        private final int index;

        private GraphNode(int index, int[] weights) {
            this.index = index;
            this.weights = weights;
            int edgeCount = (int) Arrays.stream(weights).filter(weight -> weight > 0).count();
            this.edges = new Edge[edgeCount];
        }

        private void setup() throws MalformedGraphException {
            int edgeIndex = 0;
            for (int i = 0; i < this.weights.length; i++) {
                if (this.weights[i] > 0) {
                    edges[edgeIndex] = new Edge(Graph.this.getNode(i), this.weights[i]);
                    edgeIndex++;
                }
            }
            if (edgeIndex != edges.length) {
                throw new MalformedGraphException("Not all edges are assigned to node");
            }
        }

        @Override
        public Edge[] getEdges() {
            return edges.clone();
        }

        @Override
        public int getIndex() {
            return index;
        }
    }
}
