package de.epshl.e211.tisch1;

import java.util.Comparator;

public interface VisitorNode {
    int getWeight();

    boolean hasWeight();

    boolean isVisited();

    default boolean canBeVisited() {
        return !this.isVisited() && this.getParent() != null;
    }

    VisitorNode getParent();

    Node getNode();

    public static class WeightComparator implements Comparator<VisitorNode> {

        @Override
        public int compare(VisitorNode o1, VisitorNode o2) {
            return Integer.compare(o1.getWeight(), o2.getWeight());
        }
    }
}
