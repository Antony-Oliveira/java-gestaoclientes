package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class Client {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty cpf;

    public Client(String name, String phone, String email, String cpf) {
        this.id = new SimpleStringProperty(UUID.randomUUID().toString());
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.cpf = new SimpleStringProperty(cpf);
    }

    public Client(Client client) {
        this.id = new SimpleStringProperty(UUID.randomUUID().toString());
        this.name = client.getName();
        this.email = client.getEmail();
        this.cpf = client.getCpf();
        this.phone = client.getPhone();
    }

    public StringProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getPhone() {
        return phone;
    }

    public StringProperty getEmail() {
        return email;
    }

    public StringProperty getCpf() {
        return cpf;
    }
}
