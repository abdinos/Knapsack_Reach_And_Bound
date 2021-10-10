public class Item {
    //classe qui crée l'objet Item

    //poids de l'élément
    public  float weight;
    //valeur de l'élément
    public  float value;
    //l'indexe  de l'élément dans la liste.
    public int index;

    public Item(float weight, float value,int index) {
        this.weight = weight;
        this.value = value;
        this.index = index;
    }
    public Item(float weight, float value) {
        this.weight = weight;
        this.value = value;
        this.index = 0;
    }
    public float getWeight() {
        return weight;
    }



    public float getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}

