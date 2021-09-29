public class Item {
    public final int weight;
    public final int value;
    public double cost;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
        this.cost =   (double)value /(double) weight;
    }

    public int getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }
}

