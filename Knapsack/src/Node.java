public class Node {

    //Stocke la somme des poids des éléments inclus
     double weight;
    //stocke la somme des valeurs des éléments inclus
     double totalValue;
    // le niveau de ce noeud dans l'arbre décisionnel
     int level;
    // la borne supérieur ( par la méthode gluton)
     double upperBound;
    // la borne inférieur (0/1) soit on prend l'objet soit non;
     double lowerBound;
    //un pointeur sur le noeud sélectionné
     boolean flag;

    public Node() {
    }

    public Node(Node noeud) {
        this.weight = noeud.weight;
        this.totalValue = noeud.totalValue;
        this.level = noeud.level;
        this.upperBound = noeud.upperBound;
        this.lowerBound = noeud.lowerBound;
        this.flag = noeud.flag;
    }

    public double getWeight() {
        return weight;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public int getLevel() {
        return level;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public boolean isSelected() {
        return flag;
    }


}

