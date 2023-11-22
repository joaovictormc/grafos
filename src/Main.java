
import estrutura.Grafo;
import estrutura.In;
import algoritmo.AlgoritmoCiclo;
import estrutura.Aresta;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    public static Grafo G;
    public static AlgoritmoCiclo cycle;
    Group componentes = new Group();
    Button button = new Button("Executar");

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage palco) throws Exception {

        //Adicionando a ação ao button
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cycle = new AlgoritmoCiclo(G);
                List<Integer> cycleVertices = (List<Integer>) cycle.ciclo(); // Extract the list of vertices from the cycle object
                desenharCiclo(G, cycleVertices);
            }
        });
        componentes.getChildren().add(button);
        desenharGrafo(G);

        Button botaoAbrirArquivo = new Button("Abrir arquivo");

        // Vincula o botão à ação de abrir o arquivo
        botaoAbrirArquivo.setOnAction(e -> {
            // Abre o arquivo
            FileChooser fileChooser = new FileChooser();
            File arquivo = fileChooser.showOpenDialog(null);
            if (arquivo == null) {
                System.out.println("O usuário cancelou a caixa de diálogo.");
                return;
            }

            // Faz alguma coisa com o arquivo
            // ...
        });

        // Adiciona o botão à interface gráfica
        componentes.getChildren().add(botaoAbrirArquivo);

        Scene cena = new Scene(componentes, 600, 550);
        palco.setTitle("IFES - SI - TPA - Trabalho: Ciclo em Grafos");
        palco.setScene(cena);
        palco.show();
    }

    public void desenharGrafo(Grafo G) {
        // Cria o grupo de componentes
        Group componentes = new Group();

        // Adiciona os vértices ao grupo
        for (int v = 0; v < G.V(); v++) {
            Circle circle = new Circle();
            circle.setCenterX(G.getVizinhos(v).get(0) * 50);
            circle.setCenterY(G.getVizinhos(v).get(1) * 50);
            circle.setRadius(15.0f);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITE);
            Text text = new Text(circle.getCenterX() - 4, circle.getCenterY() + 4, Integer.toString(v));
            componentes.getChildren().addAll(circle, text);
        }

        // Adiciona as arestas ao grupo
        for (Aresta aresta : G.arestas()) {
            Line line = new Line();
            line.setStartX(G.getVizinhos(aresta.getV1()).get(0) * 50);
            line.setStartY(G.getVizinhos(aresta.getV1()).get(1) * 50);
            line.setEndX(G.getVizinhos(aresta.getV2()).get(0) * 50);
            line.setEndY(G.getVizinhos(aresta.getV2()).get(1) * 50);
            line.setStrokeWidth(2.0f);
            line.setStroke(Color.BLACK);
            componentes.getChildren().add(line);
        }
    }

    public void desenharCiclo(Grafo G, List<Integer> cycle) {

        AlgoritmoCiclo finder = new AlgoritmoCiclo(G);
        boolean temCiclo = AlgoritmoCiclo.temCiclo(G);
        if (temCiclo) {
            // O grafo tem um ciclo

            // Obtém a lista dos vértices do ciclo
            Iterable<Integer> cicloList = finder.ciclo();

            for (int v : cicloList) {
                Circle circle = new Circle();
                circle.setCenterX(G.getVizinhos(v).get(0) * 100);
                circle.setCenterY(G.getVizinhos(v).get(1) * 100);
                circle.setRadius(15.0f);
                circle.setStroke(Color.BLACK);
                circle.setFill(Color.WHITE);
                componentes.getChildren().add(circle);
            }
        } else {
            // O grafo não tem um ciclo

            // Desenha o grafo sem o ciclo
            desenharGrafo(G);
        }

    }

    /*
        for (int v : cycle) {
            Circle circle = new Circle();
            circle.setCenterX(G.getVizinhos(v).get(0) * 100);
            circle.setCenterY(G.getVizinhos(v).get(1) * 100);
            circle.setRadius(15.0f);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITE);
            componentes.getChildren().add(circle);
        }

        // Adiciona as linhas ao Group
        for (int i = 0; i < cycle.size() - 1; i++) {
            Line line = new Line();
            line.setStartX(G.getVizinhos(cycle.get(i)).get(0) * 100);
            line.setStartY(G.getVizinhos(cycle.get(i)).get(1) * 100);
            line.setEndX(G.getVizinhos(cycle.get(i + 1)).get(0) * 100);
            line.setEndY(G.getVizinhos(cycle.get(i + 1)).get(1) * 100);
            line.setStrokeWidth(5);
            line.setStroke(Color.RED);
            componentes.getChildren().add(line); }*/
}
