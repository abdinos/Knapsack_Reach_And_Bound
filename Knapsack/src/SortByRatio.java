import java.util.Comparator;

//un comparateur qui compare les objets Items par le ratio Value/Weight

public class SortByRatio implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return Double.compare((o2.getValue()/o2.getWeight()),(o1.getValue()/o1.getWeight()));
    }
}
