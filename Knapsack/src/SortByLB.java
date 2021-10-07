import java.util.Comparator;

public class SortByLB implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        boolean temp = o1.getLowerBound() > o2.getLowerBound();
        return temp ? 1 : -1;
    }
}
