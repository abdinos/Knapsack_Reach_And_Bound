import java.util.ArrayList;
import java.util.PriorityQueue;

public class Knapsack {
    // capacité de sac à dos
    private double capacity;
    // la liste de tous les éléments
    public ArrayList<Item> items;

    public Knapsack(double capacity) {
        this.capacity = capacity;
        items = new ArrayList<>();
    }

    // une méthode qui trie la liste des éléments par le ratio valeur/poids
    public void sortByMaxItemValue() {
        items.sort(new SortByRatio());
    }
    // on calcule la valeurs de tous les noeuds qui appartient au sous-arbre tant qu'on a pas dépasser la capacité du sac à dos
    // la valeur donnée en retour n'est pas fractionnaire, (soit on prend l'objet en entier , soit non)
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
    // on calcule la valeurs de tous les noeuds qui appartient au sous-arbre tant qu'on a pas dépasser la capacité du sac à dos
    // si le poids de dernier élément dépasse la capacité de sac à dos , alors on ajoute une fraction de la valeur
    // selon la capacité restante
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
    // cette méthode à le fonctionnement d'un setteur, on prend un noeud qui existe et on lui affecte les valeurs mets en paramètre
    public void buildNode(Node node, double upperBound, double lowerBound, int level, boolean flag,
                          float totalValue, float weight) {

        node.totalValue = totalValue;
        node.upperBound = upperBound;
        node.lowerBound = lowerBound;
        node.level = level;
        node.flag = flag;
        node.weight = weight;

    }
//le solveur de problème sac à dos, en utilisant Least Cost Branch and Bound, qui consiste à ordonner les objets par ratio
// d´ecroissant, et explorez l’arbre en allant prioritairement à gauche
    public void branchAndBoundonKnapsack() {

        sortByMaxItemValue();

        //le noeud courant
        Node currentNode = new Node();

        // le noeud à gauche du noeud courant
        Node leftNode= new Node();

        // le noeud à droite du noeud courant
        Node rightNode = new Node();

        // minLowerBound : Borne inférieure minimale de tous les nœuds explorés
        double minLowerBound = 0;
        // MaxLowerBound : Borne inférieure minimale de tous les chemins qui ont atteint le dernier niveau
        double maxLowerBound = Integer.MAX_VALUE;

        //on crée un noeud factice pour commencer le programme
        currentNode.totalValue = 0;
        currentNode.weight =0;
        currentNode.upperBound =0;
        currentNode.lowerBound = 0;
        currentNode.level = 0;
        currentNode.flag = false;

        // PriorityQueue pour stocker les éléments basé sur leur borne inférieure
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(new SortByLB());

        //insertion du neoud factice
        nodePriorityQueue.add(currentNode);

        // currentPath : un Tableau de booléen pour stocker si les élements sur le chemin courant sont prise ou non
        boolean[] currentPath = new boolean[items.size()];
        // finalPath : un Tableau de booléen pour stocker le résultat , c'est à dire les drapeau des noeuds
        // qui appartiennent au chemin optimal
        boolean[] finalPath = new boolean[items.size()];


        while (!nodePriorityQueue.isEmpty()) {


            currentNode = nodePriorityQueue.poll();

            // si la valeur du meilleur cas du nœud actuel n'est pas meilleur que minimumLowerBound,
            // alors il n'y a aucune raison d'explorer ce nœud.
            // L'inclusion de deuxième critère élimine tous les chemins dont les meilleures valeurs sont égales à MaxLowerBound
            if (currentNode.upperBound > minLowerBound
                    || currentNode.upperBound >= maxLowerBound) {

                continue;
            }

            if (currentNode.level != 0)
                currentPath[currentNode.level - 1]
                        = currentNode.flag;

            if (currentNode.level == items.size()) {

                //si on a atteint le dernier niveau.
                if (currentNode.lowerBound < maxLowerBound) {
                    for (int i = 0; i < items.size(); i++)
                        finalPath[items.get(i).getIndex()]
                                = currentPath[i];
                    maxLowerBound = currentNode.lowerBound;
                }
                continue;
            }

            int level = currentNode.level;

            // puisque le noeud droit exlut la valeur de l'item courant ,
            //alors il prends le meme poids et valeur totale que le noeud père
            buildNode(rightNode, getUpperBoundValue(level + 1,currentNode.totalValue,
                            currentNode.weight),
                    getLowerBoundValue(level + 1, currentNode.weight,currentNode.totalValue),
                    level + 1, false,
                    currentNode.totalValue, currentNode.weight);

            if (currentNode.weight + items.get(currentNode.getLevel()).getWeight()
                    <= capacity) {

                // puisque le noeud gacuhe inclut l'élément actuel, la borne inférieures et supérieurs , plus le poids et la valeur totale,
                //doivent être calculées en incluant l'élément actuel.

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

            // Si le nœsgauche ne peut pas être inséré
            else {

                // on n'ajoute pas le nœud gauche  à la PriorityQueue
                leftNode.upperBound = leftNode.lowerBound = 1;
            }

            //mise à jour de la valeur de minLowerBpund
            minLowerBound = Math.min(minLowerBound, leftNode.lowerBound);
            minLowerBound = Math.min(minLowerBound, rightNode.lowerBound);

            if (minLowerBound >= leftNode.upperBound)
                nodePriorityQueue.add(new Node(leftNode));
            if (minLowerBound >= rightNode.upperBound)
                nodePriorityQueue.add(new Node(rightNode));
        }
        // on affiche le résultat obtenu, avec la liste des élements qui appartient au chemin optimale,
        // et s'ils sont inclus dans le sac à dos ou non
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
        StringBuilder message  = new StringBuilder("les élements sont:");
        for (Item item : items){
            message.append("(").append((int) item.getWeight()).append(", ").append((int) item.getValue()).append(", ").append(item.getIndex()).append(" )").append(";").append("\n");
        }
        message.append("\n");
        return message + "la taille de la liste des éléments est : " +items.size();
    }


}
