import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Graph { //graf
    int rows; //ilość wierszy
    int cols; //ilość wierzchołków
    ArrayList<Node>[] field; //graf

    public void print() { //funkcja dla wyświetlenia grafu wterminalu
        for (int i = 0; i < rows * cols; i++) { //dla wszystkich wierzchołków
            System.out.print(i + ": "); // wyświetlamy bieżący wierzchołek 
            for (Node node : field[i]) { // wyświetlamy wszystkie krawędzi dla bieżącego wierzchołka
                System.out.print("{" + node.destination + ";" + node.weight + "}, "); 
            }
            System.out.println();
        }
    }

    public Graph(int rows, int cols) { //konstruktor  dla grafu, gdy "pole" dla niego jeszcze nie zostało zrobione
        this.rows = rows;
        this.cols = cols;
        this.field = new ArrayList[rows * cols]; //tworzymy graf
        for (int i = 0; i < rows * cols; i++) {
            field[i] = new ArrayList<>();  //inicjujemy go
        }
    }

    public Graph(int rows, int cols, ArrayList<Node>[] field) { //pełnoprawny konstruktor 
        this.rows = rows;
        this.cols = cols;
        this.field = field;
    }

    public void printGraphToFile(String filename) throws IOException { //zapisujemy graf do pliku
        FileWriter file = new FileWriter(filename); //otwieramy plik
        file.write(rows + " " + cols + "\n"); //zapisujemy ilość wierszy i kolumn
        for (int i = 0; i < rows * cols; i++) { //iterujemy cały graf
            for (Node node : field[i]) { //zapisujemy wszystkie wierzchołki 
                file.write(node.destination + " :" + node.weight + " ");             }
            file.write("\n"); //nowy wiersz
        }
        file.close(); //zamykamu plik
    }
}
