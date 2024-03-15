package Views.NewClientForm;
import Controllers.Agenda;
import Models.Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.AlertUtils;
import utils.FormatUtils;

public class FormControl extends Application {
    
    private Agenda agenda;
    private TableView<Client> tableView;

    public FormControl(Agenda agenda, TableView<Client> tableView) {
        this.agenda = agenda;
        this.tableView = tableView;
    }

    @FXML
    private TextField name;

    @FXML
    private TextField cpf;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private Text errorName;

    @FXML
    private Text errorCpf;

    @FXML
    private Text errorPhone;

    @FXML
    private Text errorEmail;

    @FXML
    private void handleSalvar(ActionEvent event) {
        String nameText = name.getText();
        String cpfText = FormatUtils.cpfFormat(cpf.getText());
        cpf.setText(cpfText);
        String phoneText = FormatUtils.formatPhoneNumber(phone.getText());
        phone.setText(phoneText);
        String emailText = email.getText();


        if (nameText == null || nameText.isEmpty()) {
            errorName.setText("Preencha o nome do cliente");
        } else {
            errorName.setText("");
        }

        if (cpfText == null || cpfText.isEmpty()) {
            errorCpf.setText("Preencha o CPF do cliente");
        } else if (!cpfText.matches(FormatUtils.CPF_REGEX)) {
            errorCpf.setText("Formato de CPF inválido. Por favor, verifique. (000.000.000-00)");
        } else {
            errorCpf.setText("");
        }

        if (emailText == null || emailText.isEmpty()) {
            errorEmail.setText("Preencha o email do client");
        } else if (!emailText.matches(FormatUtils.EMAIL_REGEX)) {
            errorEmail.setText("Formato de e-mail inválido. Por favor, verifique.");
        } else {
            errorEmail.setText("");
        }

        if (phoneText == null || phoneText.isEmpty()) {
            errorPhone.setText("Digite o celular do cliente");
        } else if (phoneText.length() < 11 || phoneText.length() > 15) {
            errorPhone.setText("Digite o número com DDD e 9 na frente (859000000)");
        } else {
            errorPhone.setText("");
        }

        if (!nameText.isEmpty() && !phoneText.isEmpty() && (!emailText.isEmpty() && emailText.matches(FormatUtils.EMAIL_REGEX)) && (!cpfText.isEmpty() && cpfText.matches(FormatUtils.CPF_REGEX))) {
            Client client = new Client(nameText, phoneText, emailText, cpfText);

            Boolean isAdded = agenda.addclient(client);

            if (isAdded == false) {
                AlertUtils.showAlert("Erro!", "O cliente não foi adicionado ao sistema, pois já existe esse CPF no sistema.", AlertType.WARNING);
            } else {
                tableView.getItems().add(client);
                AlertUtils.showAlert("Excelente!", "O cliente " + nameText + "foi adicionado ao sistema :D", AlertType.INFORMATION);
            }
        }

    }

    @FXML
    private void handleCpfTyped() {
        if(cpf.getText().length() == 11 && !cpf.getText().matches(FormatUtils.CPF_REGEX)){
            cpf.setText(FormatUtils.cpfFormat(cpf.getText()));
        }
    }

    @FXML 
    private void handlePhoneTyped(){
        if(phone.getText().length() == 11 && !phone.getText().matches(FormatUtils.EMAIL_REGEX)){
            phone.setText(FormatUtils.formatPhoneNumber(phone.getText()));
        }
     }

    @FXML
    private void handleClear() {
        name.setText("");
        cpf.setText("");
        phone.setText("");
        email.setText("");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Adicionar novo cliente");
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewClientForm.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
}
