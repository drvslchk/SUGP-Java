import java.util.LinkedList;

public class Algorithms {

    public boolean BFS(Graph graph, int start) { //algorytm bfs dla sprawdzenia spójności 
        boolean[] visited = new boolean[graph.rows * graph.cols]; //tablica odwiedzonych wierzchołków, początkowo wypełniony false
        LinkedList<Integer> q = new LinkedList<>(); //lista z adresami = kolejka prioretytowa
        visited[start] = true; //oznaczamy wierzchołek startowy jako odwiedzony
        q.add(start); //dodajemy do kolejki wierzchołek startowy
        while (!q.isEmpty()) { //dopóli koleja nie jest pusta
            int v = q.poll(); //wyjmujemy z kolejki wierzchołek i wstawiamy go w zmienną v
            for (Node node : graph.field[v]) { //sprawdzamy wszystkie wierzchołki, do których możemy trafić z v
                int next = (int) node.destination; //tworzymy wskaźnik
                if (!visited[next]) { //jeżeli wierzchołek jeszcze nie został odwiedzony 
                    q.add(next); //dodajemy go kolejki 
                    visited[next] = true; //oznaczamy go jako odwiedzony
                }
            }
        }
        for (int i = 0; i < graph.rows * graph.cols; i++) { //sprawdzamy wszystkie wierzchołki grafu 
            if (!visited[i]) return false; //jeżeli któryś wierzchołek nie został odwiedzony to znaczy że gra jest nie spójny i zwracamy false
        }
        return true; //jeżeli wszystkie zostały odwiedzone zwracamy true
    }

    public int[] dijkstra(Graph graph, int start, int finish) throws Exception { //Algorytm Dijkstry
        int numVertices = graph.rows * graph.cols; //ilość wierzchołków
        if (!BFS(graph, start)) { //puszczamy bfs i jeżeli on zwraca false, to garf jest nie spójny 
            throw new Exception("Inconsistent graph"); //zwracamy komunikat o błędzie
        }
        double[] dist = new double[numVertices]; //tworzymy tablicę w której będziemy przechowywać długość drogi do poszczególnych wierzchołków
        int[] p = new int[numVertices]; //tworzymy tablicę gdzie będziemy przechowywać przodków wierzcholków
        boolean[] visited = new boolean[numVertices]; //tworzymy tablicę odwiedzonych wierzchołków
        int INF = (int) 1e9; // zmienna przechowująca infinity - nieskonczoność
        double min = 0; //zmienna przechowująca długość minimalną
        int minindex = 0; //zmienna przechowująca indeks wierzchołka minimalnego
        for (int i = 0; i < numVertices; i++) { //dla wszystkich wierzchołków
            visited[i] = false; //oznaczamy jako nieodwiedzone
            dist[i] = INF; //długości drogi do wierzchołków zmieniamy na infinity
            p[i] = -1; //kasujemy przodków dla wszystkich wierzchołków
        }
        dist[start] = 0; //długośc do wierzchołka startowego równa się 0
        do { //wykonujemy dopóki nie znajdziemy żadnego wierzchołka z krótszą drogą
            minindex = INF; //"zerujemy" indeks
            min = INF; //"zerujemy" długość minimalną
            for (int i = 0; i < numVertices; i++) { //idla wszystkich wierzchołków
                if (!visited[i] && dist[i] < min) { //jeżeli bieżący wierzchołek nie został jeszcze odwiedzony oraz ma najkrótszą drogę to przepisujemy długośc i indeks wierzchołka
                    min = dist[i]; 
                    minindex = i; 
                }
            }
            if (minindex != INF) { //jeżeli znaleźliśmy jakiś wierzchołek
                for (Node node : graph.field[minindex]) { //sprawdzamy wszystkie połączone z nim krawędzie
                    if (min + node.weight < dist[node.destination]) { //jeżeli droga do któregoś wierczhołka jest krótsza z bieżącego wierzchołka to aktualizujemy wartości
                        dist[node.destination] = min + node.weight;
                        p[node.destination] = minindex;
                    }
                }
                visited[minindex] = true; //oznaczamy wierzchołek jako odwiedzony
            }
        } while (minindex != INF); //wykonujemy dopóki nie znajdziemy żadnego wierzchołka z krótszą drogą
        for (int i = 0; i < numVertices; i++) { //wyświetlamy długość do wszystkich wierzchołków !!!!usunąć później
            System.out.print(dist[i] + ", ");
        }
        System.out.println();
        for (int i = 0; i < numVertices; i++) { //wyświetlamy wszystkich przodków !!! usunąć później
            System.out.print(p[i] + ", ");
        }
        System.out.println("\n\n");
        start = finish;
        while (start != -1) { //wyświetlamy najkrótszą drogę od start do finish
            System.out.print(start + " ");
            start = p[start];
        }
        System.out.println();
        return p;
    }
}
