import java.util.Comparator;

// un comparateur qui compare les noeuds par la valeurs de la borne inférieure
public class SortByLB implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        boolean temp = o1.getLowerBound() > o2.getLowerBound();
        return temp ? 1 : -1;
    }
}
