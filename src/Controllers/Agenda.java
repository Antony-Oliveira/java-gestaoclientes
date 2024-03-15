package Controllers;

import java.util.Collection;
import java.util.Hashtable;

import Models.Client;

public class Agenda {
    private Hashtable<String, Client> tabelaHash = new Hashtable<>();
    private static Agenda instance;

    private Agenda() {

    }
    public static Agenda getInstance() {
        if (instance == null) {
            instance = new Agenda();
        }
        return instance;
    }
    public Client pesquisarClient(String cpf) {
        Client client = tabelaHash.get(cpf);

        if (client == null) {
            return null;
        }

        return client;
    }

    public Boolean addclient(Client client) {
        if(tabelaHash.containsKey(client.getCpf().get())){
            System.out.println("ja tem mn");
            return false;
        }

        this.tabelaHash.put(client.getCpf().get(), client);
        return true;
    }

    public boolean deleteClient(String nome) {
        if (nome == null || !this.tabelaHash.containsKey(nome)) {
            return false;
        }

        tabelaHash.remove(nome);
        return true;
    }

    public Collection<Client> allClients() {
        if (tabelaHash.isEmpty() || tabelaHash.size() == 0) {
            return null;
        }

        return tabelaHash.values();
    }
}
