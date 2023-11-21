package teste;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Teste extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o layout da interface do usuário a partir do arquivo FXML.
            Parent root = FXMLLoader.load(getClass().getResource("FXMLGrafo.fxml"));
            
            // Define o título da janela (Stage).
            primaryStage.setTitle("Aplicação de Grafos Acíclicos");
            
            // Cria a cena com o painel raiz carregado do FXML.
            Scene scene = new Scene(root, 1280, 720); // Defina o tamanho desejado da janela aqui.
            
            // Define a cena no palco principal e exibe-a.
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
