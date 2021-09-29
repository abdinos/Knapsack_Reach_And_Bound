import java.util.ArrayList;

public class Knapsack {
    public static ArrayList<Item> items;
    public static int capacity;

    public Knapsack(int capacity) {
        Knapsack.capacity = capacity;
        items = new ArrayList<>();
    }

    public static void sortByMaxItemValue() {
        items.sort(new ItemValueComparator());
    }

    public static double getMaxKnapsackValue() {
       sortByMaxItemValue();
        double totalValue = 0d;
        for (Item item : items) {
            int currentVal = item.getValue();
            int currentWeight = item.getWeight();
            if (capacity >= currentWeight) {
                capacity -= currentWeight;
                totalValue += currentVal;
            }
            else {double piece = (double) capacity/(double) currentWeight;
            totalValue+= (currentVal*piece);
                capacity = (int)(capacity - (currentWeight * piece));
            break;}
        }
        return totalValue;
    }
}
