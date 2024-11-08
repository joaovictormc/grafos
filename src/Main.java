
import estrutura.Grafo;
import estrutura.In;
import algoritmo.AlgoritmoCiclo;
import java.util.List;
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
        In in = new In("C:/Users/jvict/Documents/GitHub/grafos/_dados/Grafo3.txt");
        Grafo G = new Grafo(in);
        System.out.println(G);

        AlgoritmoCiclo finder = new AlgoritmoCiclo(G);
        if (finder.temCiclo()) {
            System.out.print("Ciclo:");
            for (int v : finder.ciclo()) {
                System.out.print(" " + v);
            }
            System.out.println("");
        } else {
            System.out.println("Grafo é acíclico");
        }

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

        Scene cena = new Scene(componentes, 600, 550);
        palco.setTitle("IFES - SI - TPA - Trabalho: Ciclo em Grafos");
        palco.setScene(cena);
        palco.show();
    }

    public void desenharGrafo(Grafo G) {
        for (int v = 0; v < G.V(); v++) {
            int[] pos = G.getCoordenadas(v);
            Circle circle = new Circle(pos[0], pos[1], 15);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            componentes.getChildren().add(circle);

            // Adicione o número da aresta no círculo
            Text text = new Text(pos[0] - 5, pos[1] + 5, String.valueOf(v));
            componentes.getChildren().add(text);

            for (int w : G.getVizinhos(v)) {
                int[] posW = G.getCoordenadas(w);
                Line line = new Line(pos[0], pos[1], posW[0], posW[1]);
                line.setStroke(Color.BLACK);
                componentes.getChildren().add(line);
            }
        }
    }

    public void desenharCiclo(Grafo G, List<Integer> cycle) {
        if (!cycle.isEmpty()) {
            for (int i = 0; i < cycle.size(); i++) {
                int v = cycle.get(i);
                int w = cycle.get((i + 1) % cycle.size());
                int[] posV = G.getCoordenadas(v);
                int[] posW = G.getCoordenadas(w);

                Circle circleV = new Circle(posV[0], posV[1], 15, Color.RED);
                componentes.getChildren().add(circleV);

                // Adicione o número da aresta no círculo
                Text text = new Text(posV[0] - 5, posV[1] + 5, String.valueOf(i));
                componentes.getChildren().add(text);

                Line line = new Line(posV[0], posV[1], posW[0], posW[1]);
                line.setStrokeWidth(2.0f);
                line.setStroke(Color.RED);
                componentes.getChildren().add(line);
            }
        }
    }

}
