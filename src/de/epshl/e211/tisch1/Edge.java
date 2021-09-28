package de.epshl.e211.tisch1;

public class Edge {
    private final Node node;
    private final int weight;

    public Edge(Node node, int weight) {
        this.node = node;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Node getNode() {
        return node;
    }
}
