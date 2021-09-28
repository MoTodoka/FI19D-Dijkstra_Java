package de.epshl.e211.tisch1;

public class Graph {

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
    }
}
