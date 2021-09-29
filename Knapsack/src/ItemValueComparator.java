import java.util.Comparator;

public class ItemValueComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return Double.compare(o1.getCost(),o2.getCost());
    }
}
