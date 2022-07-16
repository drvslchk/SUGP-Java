import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Reader {

    private String normaliseStr(String now) { //funkcja dla normalizacji string'a, usuwa wszystko, co nie jest cyfrą, kropką lub spacją
        String allowedValues = "0123456789. "; //dopuszczalne wartości
        StringBuilder result = new StringBuilder(); //ciąg końcowy po usunięciu nie potrzebnych elementów
        boolean dot = false; //flaga wskazująca czy już napotkaliśmy kropkę
        boolean value = false; //flaga wskazująca czy już napotkaliśmy cyfrę
        int i = 0; // bieząca pozycja
        while (i < now.length()) { //dopóki nie przejrzymy caly ciąg znaków
            char x = now.charAt(i); //bieżący element
            if (allowedValues.indexOf(x) != -1) { //jeżeli bieżący element jest jednym z dopuszczalnych
                if (x == '.') { //jeżeli to kropka, to sprawdzamy czy się nie dubluje
                    if (dot) { //jeżeli już napotkaliśmy kropkę to omijamy
                        i++;
                        continue;
                    } else {
                        if (!value) { //jeżeli nie napotkaliśmy ani kropki, ani cyfry, to omijamy
                            i++;
                            continue;
                        } else { //jeżeli nie napotkaliśmy kropki ale napotkaliśmy cyfrę ,to zapisujemy tę kropkę do nowego ciągu znakó
                            dot = true; //zaznaczamy że napotkaliśmy kropkę
                            i++;
                            result.append(x); //dopisujemy kropkę
                            continue;
                        }
                    }
                }
                if (x >= '0' && x <= '9') { //jeżeli to cyfra
                    value = true; //zaznaczamy że napotkaliśmy cyfrę
                }
                if (x == ' ') { //jeżeli to spacja, to kasujemy flagi dot i value
                    dot = false; 
                    value = false; 
                }
                result.append(x); //zapisujemy bieżącą wartość do ciągu
            }
            i++;
        }

        for (i = 0; i < result.length(); i++) { //sprawdzamy cały ciąg znaków czy nie ma "wiszącej kropki" (czyli np 4. )
            if (result.charAt(i) == '.' && (result.charAt(i + 1) < '0' || result.charAt(i + 1) > '9')) {  //jeżeli bieżący element jest kropką, a następna wartośc nie jest cyfrą
                result.insert(i + 1, '0'); //wklejamy 0 po .
            }
        }
        return result.toString(); //zwracamy zmieniony ciag
    }

    public Graph readGraphFromFile(String filename) throws Exception { //funkcja dla odczytu grafu z pliku 
        FileReader reader = new FileReader(filename); //otwieramy reader na biężacy plik
        Scanner scaner = new Scanner(reader); //dołączamy do niego scanner
        int from = 0; //zmienna odpowiadająca za numer bieżącego wiersza - numer wierzchołka z którego wychodzi krawędź
        String now = ""; //aktualny ciąg znaków
        int rows, cols; //ilość wierszy i kolemn
        if (scaner.hasNextLine()) { //jeżeli istnieje następna linijka
            now = scaner.nextLine(); //odczytujemy pierwszą linijkę
            now = normaliseStr(now); //normalizujemy odczytany ciąg znaków
            String[] val = now.split(" "); //rozdzielamy ciąg na liczby z separatorami w postaci spacji
            if (val.length != 2) { //jeżeli w pierwszej linijce ilośc liczb nie jest równa 2, to format pliku jest niepoprawny
                throw new Exception("Inconsistent file"); //błąd
            }
            rows = (int)Double.parseDouble(val[0]); //odczytujemy ilośc wierszy
            cols = (int)Double.parseDouble(val[1]); //odczytujemy ilość kolumn
        } else {
            throw new Exception("Inconsistent file"); //jeżeli plik nie ma nawet pierwszego wiersza to zwracamy błąd
        }

        Graph graph = new Graph(rows, cols); //tworzymy nowy graf o zadanym rozmiarze
        while (scaner.hasNextLine()) { //dopóki istnieje nastepna linijka
            now = scaner.nextLine(); //odczytujemy ciąg znaków
            now = normaliseStr(now); //normalizujemy go
            String[] val = now.split(" "); //rozdzielamy na liczby spacjami
            if (val.length == 0) { //jeżeli ciąg nie jest pusty to idziemy do następnego
                from++; //zwiąkszamy numer linijki
                continue;
            }
            if (val.length % 2 == 1) { //jeżeli ilość elementów w ciągu jest nieparzysta(powinna być parzysta: numer wierzchołka + waga),to zwracamy bład
                throw new Exception("Inconsistent file"); //błąd
            }
            int i = 0; //wskaźnik
            while (i < val.length) { //iterujemy do końca ciągu znaków
                int destination = (int)Double.parseDouble(val[i]); //odczytujemy numer wierzchołka
                double weight = Double.parseDouble(val[i + 1]); //odczytujemy wagę krawędzi
                i += 2; //zwiększamy wskaźnik o 2 (ponieważ odczytano 2 liczby)
                graph.field[from].add(new Node(destination, weight)); //dodajemy nowy element do grafa
            }
            from++; //zwiększamy numer linijki
        }
        return graph; //zwracamy graf
    }
}
