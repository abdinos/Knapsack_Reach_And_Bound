import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File sac0 = new File("C:\\Users\\abdes\\Downloads\\Documents\\Master\\ARO\\TP\\sac2.txt");
        BufferedReader knapsackReader = new BufferedReader(new FileReader(sac0));
        Knapsack knapsack0 = new Knapsack(Double.parseDouble(knapsackReader.readLine()));
        String str;
        int index =0;
        while ((str = knapsackReader.readLine())!= null){
            String[] eachLine = str.split(" ");
            knapsack0.items.add(new Item(Double.parseDouble(eachLine[0]), Double.parseDouble(eachLine[1]),index));
            index++;

        }
        System.out.println(knapsack0.toString());
        knapsack0.branchAndBoundonKnapsack();
    }
}
