import java.util.Comparator;

public class SortByRatio implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return Double.compare(o2.getCost(),o1.getCost());
    }
}
