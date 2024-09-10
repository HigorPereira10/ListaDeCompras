package com.higor.listadecompras.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Modelo para a lista de compras, responsável por manipular e persistir dados.
 */
public class ListaDeComprasModel {

    // Caminho do arquivo onde a lista de compras é salva
    private static final String ARQUIVO_SALVO = "listaDeComprasSalva.txt";

    /**
     * Adiciona um item à lista visualizável e salva a lista atualizada em disco.
     * 
     * @param item             O item a ser adicionado à lista
     * @param listaVisualizavel A ListView onde os itens são exibidos
     */
    public void adicionarItem(String item, ListView<String> listaVisualizavel) {
        ObservableList<String> items = listaVisualizavel.getItems();
        items.add(item);
        salvarLista(items); // Salva a lista atualizada
    }

    /**
     * Exporta a lista de compras para um arquivo especificado pelo usuário.
     * 
     * @param arquivo           O arquivo para onde a lista será exportada
     * @param listaVisualizavel A ListView contendo os itens a serem exportados
     */
    public void exportarLista(File arquivo, ListView<String> listaVisualizavel) {
        try (PrintWriter writer = new PrintWriter(arquivo)) {
            for (String item : listaVisualizavel.getItems()) {
                writer.println(item);
            }
            mostrarAlerta("Exportação Concluída", "Lista exportada com sucesso.");
        } catch (IOException ex) {
            mostrarAlerta("Erro", "Erro ao exportar a lista: " + ex.getMessage());
        }
    }

    /**
     * Carrega a lista de compras de um arquivo salvo anteriormente.
     * 
     * @param listaVisualizavel A ListView onde os itens carregados serão exibidos
     */
    public void carregarLista(ListView<String> listaVisualizavel) {
        try {
            File arquivo = new File(ARQUIVO_SALVO);
            if (arquivo.exists()) {
                try (Scanner scanner = new Scanner(arquivo)) {
                    while (scanner.hasNextLine()) {
                        String item = scanner.nextLine();
                        listaVisualizavel.getItems().add(item);
                    }
                }
            }
        } catch (IOException ex) {
            mostrarAlerta("Erro", "Erro ao carregar a lista: " + ex.getMessage());
        }
    }

    /**
     * Salva a lista de compras em um arquivo definido como ARQUIVO_SALVO.
     * 
     * @param items A lista de itens a serem salvos
     */
    private void salvarLista(ObservableList<String> items) {
        try (PrintWriter writer = new PrintWriter(new File(ARQUIVO_SALVO))) {
            for (String item : items) {
                writer.println(item);
            }
        } catch (IOException ex) {
            mostrarAlerta("Erro", "Erro ao salvar a lista: " + ex.getMessage());
        }
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
}
