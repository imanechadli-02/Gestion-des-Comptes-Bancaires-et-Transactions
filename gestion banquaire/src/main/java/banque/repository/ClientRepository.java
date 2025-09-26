package banque.repository;

import banque.model.Client;
import java.util.*;

public class ClientRepository {
    private Map<Integer, Client> clients = new HashMap<>();

    // Ajouter un client
    public void ajouterClient(Client client) {
        clients.put(client.getIdClient(), client);
    }

    // Trouver un client par ID
    public Optional<Client> trouverClientParId(int idClient) {
        return Optional.ofNullable(clients.get(idClient));
    }

    // Supprimer un client
    public void supprimerClient(int idClient) {
        if (!clients.containsKey(idClient)) {
            throw new NoSuchElementException("Client introuvable !");
        }
        clients.remove(idClient);
    }

    // Modifier un client (remplace l'objet existant)
    public void modifierClient(Client client) {
        if (!clients.containsKey(client.getIdClient())) {
            throw new NoSuchElementException("Client introuvable !");
        }
        clients.put(client.getIdClient(), client);
    }

    // Retourner tous les clients
    public List<Client> listerClients() {
        return new ArrayList<>(clients.values());
    }
}
