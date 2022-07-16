public class Node {

    double weight; //długość do bieżącego wierzchołka
    int destination; //numer wierzchołka do którego możemy trafić z bieżącego

    public Node() { //konstruktor 
        weight = -1; //domyślnie krawędź nie istnieje
        destination = -1; 
    }

    public Node(int destination, double weight) { //konstruktor z argumentami
        this.destination = destination;
        this.weight = weight;
    }
}
