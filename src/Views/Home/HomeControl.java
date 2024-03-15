package Views.Home;

import java.util.Collection;

import Controllers.Agenda;
import Models.Client;
import Views.NewClientForm.FormControl;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.scene.control.TableCell;
import javafx.util.Callback;
import utils.AlertUtils;
import utils.FormatUtils;

public class HomeControl extends Application {

    private Agenda agenda;

    public HomeControl(Agenda agenda) {
        this.agenda = agenda;
    }

    private static ObservableList<Client> dataTable = FXCollections.observableArrayList();

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TableColumn<Client, SimpleStringProperty> actionColumn;
    @FXML
    private TextField cpf;

    @FXML
    private TableColumn<Client, String> idColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> cpfColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        cpfColumn.setCellValueFactory(cellData -> cellData.getValue().getCpf());
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhone());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmail());
        actionColumn.setCellFactory(createButtonCellFactory());
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        Collection<Client> clients = agenda.allClients();

        if (clients == null || clients.size() == 0 || clients.isEmpty()) {
            return;
        }
        for (Client client : clients) {
            dataTable.add(new Client(client));
        }

        tableView.setItems(dataTable);
    }

    public void addToTable(Client client) {
        tableView.getItems().add(client);
    }

    @FXML
    private void buscarPorCPF(ActionEvent event) {
        String cpfSearch = cpf.getText();
        if (!cpfSearch.isEmpty()) {
            for (Client cliente : tableView.getItems()) {
                if (cliente.getCpf().get().equals(cpfSearch)) {
                    tableView.getSelectionModel().select(cliente);

                    TableView.TableViewSelectionModel<Client> selectionModel = tableView.getSelectionModel();
                    @SuppressWarnings("unchecked")
                    TablePosition<Client, ?> pos = selectionModel.getSelectedCells().get(0);
                    tableView.getFocusModel().focus(pos.getRow(), pos.getTableColumn());
                    tableView.scrollTo(pos.getRow());

                    return;
                }
            }
            AlertUtils.showAlert("Não encontrado :(", "O CPF " + cpfSearch + " não foi encontrado no sistema.", AlertType.WARNING);
        }
    }

    @FXML
    private void handleShowForm(ActionEvent event) throws Exception {
        FormControl formControl = new FormControl(agenda, tableView);
        formControl.start(new Stage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Clientes");
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @FXML
    public static void updateTable(Client newClient) {
        dataTable.add(newClient);
    }

    private Callback<TableColumn<Client, SimpleStringProperty>, TableCell<Client, SimpleStringProperty>> createButtonCellFactory() {
        return new Callback<TableColumn<Client, SimpleStringProperty>, TableCell<Client, SimpleStringProperty>>() {
            @Override
            public TableCell<Client, SimpleStringProperty> call(final TableColumn<Client, SimpleStringProperty> param) {
                return new TableCell<Client, SimpleStringProperty>() {
                    private final Button btn = new Button("Excluir");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Client client = getTableView().getItems().get(getIndex());
                            deleteClient(client.getCpf().get(), getIndex());
                        });
                    }

                    @Override
                    protected void updateItem(SimpleStringProperty item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                    private void deleteClient(String clientCpf, int clientIndex) {
                        System.out.println("CLIENTE DELETADO: " + clientCpf + ", índice: " + clientIndex);
                        Boolean hasDeleted = agenda.deleteClient(clientCpf);
                        if (hasDeleted == false) {
                            AlertUtils.showAlert("Erro :(", "O cliente com o cpf " + clientCpf + " não pôde ser deletado", AlertType.WARNING);
                        } else {
                            tableView.getItems().remove(getIndex());
                            AlertUtils.showAlert("Operação bem sucedida", "O cliente com o cpf " + clientCpf + " foi deletado do sistema.", AlertType.INFORMATION);
                        }
                    }
                };
            }
        };
    }

    @FXML
    private void handleCpfTyped() {
        if(cpf.getText().length() == 11 && !cpf.getText().matches(FormatUtils.CPF_REGEX)){
            cpf.setText(FormatUtils.cpfFormat(cpf.getText()));
        }
    }
}
