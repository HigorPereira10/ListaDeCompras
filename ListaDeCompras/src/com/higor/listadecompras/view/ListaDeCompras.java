package com.higor.listadecompras.view;

import com.higor.listadecompras.model.ListaDeComprasModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;

/**
 * Classe principal da aplicação, responsável pela interface do usuário e interação.
 */
public class ListaDeCompras extends Application {

    private ListaDeComprasModel model = new ListaDeComprasModel();
    private ListView<String> listaVisualizavel = new ListView<>();
    private TextField textFieldDescricaoItem = new TextField();

    @Override
    public void start(Stage palco) {
        inicializarInterface(palco);
        model.carregarLista(listaVisualizavel); // Carrega a lista ao iniciar
    }

    /**
     * Configura e inicializa a interface do usuário.
     * 
     * @param palco O palco principal da aplicação
     */
    private void inicializarInterface(Stage palco) {
        palco.setTitle("Aplicativo de Lista de Compras");
        BorderPane root = new BorderPane();
        VBox vBox = criarLayout();
        root.setCenter(vBox);
        Scene scene = new Scene(root, 400, 300);
        configurarEstilo(scene);
        palco.setScene(scene);
        palco.show();
    }

    /**
     * Cria e configura o layout da interface do usuário.
     * 
     * @return O VBox contendo os componentes da interface
     */
    private VBox criarLayout() {
        Button botaoAdicionar = new Button("Adicionar");
        Button botaoExportar = new Button("Exportar Lista");

        Label labelAdicionar = new Label("Digite o item que deseja adicionar:");
        Label labelListaDeCompras = new Label("Lista de Compras");

        // Estilizando Labels
        labelAdicionar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelAdicionar.setTextFill(Color.DARKBLUE);
        labelListaDeCompras.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelListaDeCompras.setTextFill(Color.DARKBLUE);

        // Estilizando TextField
        textFieldDescricaoItem.setPromptText("Digite o item aqui...");
        textFieldDescricaoItem.setStyle("-fx-border-color: #3498db; -fx-border-width: 2; -fx-padding: 5;");

        // Estilizando Botões
        botaoAdicionar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 10;");
        botaoExportar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 10;");

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(labelAdicionar, textFieldDescricaoItem, botaoAdicionar, labelListaDeCompras, listaVisualizavel, botaoExportar);

        configurarBotoes(botaoAdicionar, botaoExportar);

        return vBox;
    }

    /**
     * Configura os manipuladores de eventos para os botões.
     * 
     * @param botaoAdicionar O botão para adicionar itens
     * @param botaoExportar  O botão para exportar a lista
     */
    private void configurarBotoes(Button botaoAdicionar, Button botaoExportar) {
        botaoAdicionar.setOnAction(e -> adicionarItem());
        botaoExportar.setOnAction(e -> exportarLista());
    }

    /**
     * Adiciona um item à lista e limpa o campo de entrada.
     */
    private void adicionarItem() {
        String item = textFieldDescricaoItem.getText();
        if (!item.isEmpty()) {
            model.adicionarItem(item, listaVisualizavel);
            textFieldDescricaoItem.clear();
        } else {
            mostrarAlerta("Campo Vazio", "Digite um item para adicionar à lista.");
        }
    }

    /**
     * Abre um diálogo para exportar a lista de compras para um arquivo selecionado pelo usuário.
     */
    private void exportarLista() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Lista de Compras");
        File arquivo = fileChooser.showSaveDialog(null);
        if (arquivo != null) {
            model.exportarLista(arquivo, listaVisualizavel);
        }
    }

    /**
     * Configura o estilo da cena principal.
     * 
     * @param scene A cena a ser estilizada
     */
    private void configurarEstilo(Scene scene) {
        scene.setFill(Color.LIGHTGRAY); // Define a cor de fundo da cena
    }

    /**
     * Exibe um alerta para o usuário com uma mensagem informativa.
     * 
     * @param titulo   O título do alerta
     * @param mensagem A mensagem a ser exibida
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
