public class Item {
    public  double weight;
    public  double value;
    public double cost;
    public int index;

    public Item(double weight, double value,int index) {
        this.weight = weight;
        this.value = value;
        this.index = index;

        this.cost =   value / weight;
    }
    public Item(double weight, double value) {
        this.weight = weight;
        this.value = value;
        this.index = 0;

        this.cost = value / weight;
    }
    public double getWeight() {
        return weight;
    }

    public double getCost() {
        return cost;
    }

    public double getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}

