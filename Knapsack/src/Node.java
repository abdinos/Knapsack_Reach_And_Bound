public class Node {

    //Stocke la somme des poids des éléments inclus
     float weight;
    //stocke la somme des valeurs des éléments inclus
     float totalValue;
    // le niveau de ce noeud dans l'arbre décisionnel
     int level;
    // la borne supérieure ( par la méthode gluton)
     double upperBound;
    // la borne inférieure (0/1) soit on prend l'objet soit non;
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

    public float getWeight() {
        return weight;
    }

    public float getTotalValue() {
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

