import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Knapsack {

    private double capacity;
    public ArrayList<Item> items;

    public Knapsack(double capacity) {
        this.capacity = capacity;
        items = new ArrayList<>();
    }


    public void sortByMaxItemValue() {
        items.sort(new SortByRatio());
    }

    public double getLowerBoundValue(int index, double totalWeight, double totalValue) {
        sortByMaxItemValue();
        double value = totalValue;
        double weight = totalWeight;
        for (int i = index; i < items.size(); i++) {
            double currentVal = items.get(i).getValue();
            double currentWeight = items.get(i).getWeight() + weight;
            if (items.size() >= currentWeight) {
                weight+= items.get(i).getWeight();
                value -= currentVal;
            } else {
                break;
            }

        }
        return value;
    }

    public double getUpperBoundValue(int index, double totalValue, double totalWeight) {
        sortByMaxItemValue();
        double value = totalValue;
        double weight = totalWeight;
        for (int i = index; i < items.size(); i++) {
            double currentVal = items.get(i).getValue();
            double currentWeight = items.get(i).getWeight() + weight;
            if (capacity >= currentWeight) {
                weight += items.get(i).getWeight();
                value -= currentVal;
            } else {
                value -=(capacity - weight) / items.get(i).getWeight() * currentVal;
                break;
            }
        }
        return value;

    }

    public void buildNode(Node node, double upperBound, double lowerBound, int level, boolean flag,
                          double profit, double weight) {

        node.totalValue = profit;
        node.upperBound = upperBound;
        node.lowerBound = lowerBound;
        node.level = level;
        node.flag = flag;
        node.weight = weight;

    }

    public void branchAndBoundonKnapsack() {
        sortByMaxItemValue();
        Node currentNode = new Node();
        Node leftNode= new Node();
        Node rightNode = new Node();

        double minLowerBound = 0;
        double maxLowerBound = Integer.MAX_VALUE;

        currentNode.totalValue = currentNode.weight = currentNode.upperBound = currentNode.lowerBound = 0;
        currentNode.level = 0;
        currentNode.flag = false;


        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(new SortByLB());
        nodePriorityQueue.add(currentNode);
        boolean currPath[] = new boolean[items.size()];
        boolean finalPath[] = new boolean[items.size()];
        while (!nodePriorityQueue.isEmpty()) {
            currentNode = nodePriorityQueue.poll();
            if (currentNode.upperBound > minLowerBound
                    || currentNode.upperBound >= maxLowerBound) {
                // if the current node's best case
                // value is not optimal than minLB,
                // then there is no reason to
                // explore that node. Including
                // finalLB eliminates all those
                // paths whose best values is equal
                // to the finalLB
                continue;
            }

            if (currentNode.level != 0)
                currPath[currentNode.level - 1]
                        = currentNode.flag;

            if (currentNode.level == items.size()) {
                if (currentNode.lowerBound < maxLowerBound) {
                    // Reached last level
                    for (int i = 0; i < items.size(); i++)
                        finalPath[items.get(i).getIndex()]
                                = currPath[i];
                    maxLowerBound = currentNode.lowerBound;
                }
                continue;
            }

            int level = currentNode.level;

            // right node -> Exludes current item
            // Hence, cp, cw will obtain the value
            // of that of parent
            buildNode(rightNode, getUpperBoundValue(level + 1,currentNode.totalValue,
                            currentNode.weight),
                    getLowerBoundValue(level + 1, currentNode.weight,currentNode.totalValue),
                    level + 1, false,
                    currentNode.totalValue, currentNode.weight);

            if (currentNode.weight + items.get(currentNode.getLevel()).getWeight()
                    <= capacity) {

                // left node -> includes current item
                // c and lb should be calculated
                // including the current item.
                leftNode.upperBound = getUpperBoundValue(
                        level + 1,
                        currentNode.totalValue
                                - items.get(level).getValue(),
                        currentNode.weight
                                + items.get(level).getWeight()
                        );
                leftNode.lowerBound = getLowerBoundValue(
                        level + 1,
                        currentNode.weight +items.get(level).getWeight(),
                        currentNode.totalValue - items.get(level).getValue());

                buildNode(leftNode, leftNode.upperBound, leftNode.lowerBound,
                        level + 1, true,
                        currentNode.totalValue - items.get(level).getValue(),
                        currentNode.weight
                                + items.get(level).getWeight());
            }

            // If the left node cannot
            // be inserted
            else {

                // Stop the left node from
                // getting added to the
                // priority queue
                leftNode.upperBound = leftNode.lowerBound = 1;
            }

            // Update minLB
            minLowerBound = Math.min(minLowerBound, leftNode.lowerBound);
            minLowerBound = Math.min(minLowerBound, rightNode.lowerBound);

            if (minLowerBound >= leftNode.upperBound)
                nodePriorityQueue.add(new Node(leftNode));
            if (minLowerBound >= rightNode.upperBound)
                nodePriorityQueue.add(new Node(rightNode));
        }
        System.out.println("\n");
        System.out.println("les élements pris dans le sac à dos sont :");
        System.out.print("{");
        for (int i = 0; i < items.size(); i++) {
            if (finalPath[i])
                System.out.print("1, ");
            else
                System.out.print("0, ");
        }
        System.out.print("}");
        System.out.println("\nle profit maximum est :"
                + " is " + (-maxLowerBound));
    }

    public String toString(){
        String message  =  "les élements sont:";
        for (Item item : items){
            message += "("+ (int)item.getWeight()+", "+ (int) item.getValue() +", "+ item.getIndex()+" )"+";"+"\n" ;
        }
        message += "\n" ;
        return message + "la taille de la liste des éléments est : " +items.size();
    }


}
