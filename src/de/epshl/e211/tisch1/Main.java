package de.epshl.e211.tisch1;

public class Main {

    public static void main(String[] args) throws MalformedGraphException {
        System.out.println("Hello World");

        int[][] adjacentMatrix = new int[][] {
                new int[] {0, 2, 3, 2},
                new int[] {0, 0, 1, 1},
                new int[] {2, 10, 0, 1},
                new int[] {1, 3, 1, 0}
        };

        Graph graph = new Graph(adjacentMatrix);
        Node start = graph.getNode(1);
        Node end = graph.getNode(0);
        Solver solver = new Solver(graph, start, end);
        while (!solver.isFinished()) {
            solver.step();
        }
        VisitorNode n = solver.getVisitorNode();
        printRoute(n);

        System.out.printf("All other routes from node '%d'%n", start.getIndex());
        for (int i = 0; i < graph.getNodeCount(); i++) {
            if (i != n.getNode().getIndex() && i != start.getIndex()) {
                Node node = graph.getNode(i);
                printRoute(solver.getVisitorNode(node));
            }
        }
    }

    private static void printRoute(VisitorNode end) {
        if (end.isVisited()) {
            System.out.printf("Route to '%d'%n", end.getNode().getIndex());
            while (end != null) {
                System.out.printf("\t% 4d (Weight: % 3d)%n", end.getNode().getIndex(), end.getWeight());
                if (end == end.getParent()) {
                    break;
                }
                end = end.getParent();
            }
        } else {
            System.out.printf("Could not find a route to '%d'%n", end.getNode().getIndex());
        }
    }
}