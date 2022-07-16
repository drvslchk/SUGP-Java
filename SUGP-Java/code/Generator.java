import java.util.ArrayList;
import java.util.Random;

public class Generator {
    public double generateValue(double left, double right) { //funkcja generująca wagi krawędzi
        Random r = new Random(); //tworzymy random
        double value = left + (right - left) * r.nextDouble(); //generujemy losową liczbę w zakresie od left do right
        double scale = Math.pow(10, 4);
        return Math.ceil(value * scale) / scale; //zostawiamy 4 znaki po przecinku 
    }

    public Graph generateGraph(int rows, int cols, double left, double right) { //funkcja dla generacji grafu
        ArrayList<Node>[] field = new ArrayList[rows * cols]; //tworzymy tablicę gdzie przechowujemy wierzchołki
        for (int i = 0; i < rows * cols; i++) { //tworzymy macierz
            field[i] = new ArrayList<>();
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cur = (i * cols) + j; //wyliczamy numer bieżącego wierzchołka
                if ((cur + 1) % cols != 0) { //jeżeli bieżący wierzchołek nie jest ostatni po prawej stronie
                    double weight = generateValue(left, right); //generujemy wagę 
                    field[cur].add(new Node(cur + 1, weight)); //dodajemy krawędź z bieżącego wierzchołka do prawego
                    field[cur + 1].add(new Node(cur, weight)); //dodajemy taką samą krawędź, ale z prawego wierzchołka do bieżącego
                }

                if (cur >= cols) { //jeżeli bieżący wierzchołek nie jest w dolnym rzędzie
                    double weight = generateValue(left, right); //generujemy wagę
                    field[cur].add(new Node(cur - cols, weight)); //dodajemy krawędź z bieżącego wierzchołka do wierzchołka pod nim
                    field[cur - cols].add(new Node(cur, weight)); //i naodwrót
                }

            }
        }
        Graph graph = new Graph(rows, cols, field); //tworzymy graf o zadanej ilości wierszy, kolumn na "polu" dla samego grafu 
        return graph; //zwracamy graf
    }
}
