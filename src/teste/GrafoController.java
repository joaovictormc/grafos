package teste;
import teste.algoritmo.AlgoritmoCiclo;
import teste.estruturas.Aresta;
import teste.estruturas.Grafo;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;

public class GrafoController {
    @FXML
    private Pane graphPane;

    private Grafo grafo;
    private Map<Integer, Circle> vertices = new HashMap<>();
    private Map<Pair<Integer, Integer>, Line> arestasMap = new HashMap<>();

    @FXML
    public void initialize() {
        try {
            grafo = carregarGrafo("grafo1.txt");
            desenhaGrafo();

            AlgoritmoCiclo algoritmo = new AlgoritmoCiclo(grafo);
            if (algoritmo.temCiclo()) {
                colorirCiclo(algoritmo.ciclo());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Lidar com o erro de acordo com a necessidade do seu programa
        }
    }

    
    private Grafo carregarGrafo(String caminhoArquivo) throws IOException {
    Path caminho = Paths.get(caminhoArquivo);
    Scanner scanner = null;

    try {
        scanner = new Scanner(caminho);

        int numVertices = scanner.nextInt();
        Grafo grafo = new Grafo(numVertices);

        int numArestas = scanner.nextInt();

        for (int i = 0; i < numArestas; i++) {
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();
            grafo.existeArestaEntre(v1, v2);
        }

        return grafo;
    } catch (IOException e) {
        e.printStackTrace();
        throw e; // Re-lança a exceção para que ela seja tratada no método que chama carregarGrafo
    } finally {
        if (scanner != null) {
            scanner.close();
        }
    }
}


    private void desenhaGrafo() {
        for (int v = 0; v < grafo.V(); v++) {
            Circle circle = new Circle();
            circle.setCenterX(50);
            circle.setCenterY(50);
            circle.setRadius(20);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);

            Text text = new Text(Integer.toString(v));
            text.setX(circle.getCenterX());
            text.setY(circle.getCenterY());

            vertices.put(v, circle);
            graphPane.getChildren().addAll(circle, text);
        }

        for (int v = 0; v < grafo.V(); v++) {
            for (Aresta a : grafo.adj(v)) {
                int w = a.getV2();
                if (v < w) {
                    Line line = new Line();
                    line.setStartX(vertices.get(v).getCenterX());
                    line.setStartY(vertices.get(v).getCenterY());
                    line.setEndX(vertices.get(w).getCenterX());
                    line.setEndY(vertices.get(w).getCenterY());
                    line.setStroke(Color.BLACK);

                    Pair<Integer, Integer> edgeKey = new Pair<>(v, w);
                    arestasMap.put(edgeKey, line);

                    graphPane.getChildren().add(line);
                }
            }
        }
    }

    private void colorirCiclo(Iterable<Integer> ciclo) {
        int prevVertex = -1;
        for (int vertex : ciclo) {
            if (prevVertex != -1) {
                Line edge = encontrarAresta(prevVertex, vertex);
                if (edge != null) {
                    edge.setStroke(Color.RED);
                }
            }
            prevVertex = vertex;
        }

        if (prevVertex != -1) {
            Line edge = encontrarAresta(prevVertex, ciclo.iterator().next());
            if (edge != null) {
                edge.setStroke(Color.RED);
            }
        }
    }

    private Line encontrarAresta(int v1, int v2) {
        Pair<Integer, Integer> edgeKey = new Pair<>(v1, v2);
        if (arestasMap.containsKey(edgeKey)) {
            return arestasMap.get(edgeKey);
        }
        
        edgeKey = new Pair<>(v2, v1);
        return arestasMap.getOrDefault(edgeKey, null);
    }
}
