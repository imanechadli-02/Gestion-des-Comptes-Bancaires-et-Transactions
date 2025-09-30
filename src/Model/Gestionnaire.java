package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Gestionnaire extends Personne {
    private UUID idGestionnaire;
    private String departement;
    private List<Client> listeClients;

    public Gestionnaire(String nom, String prenom, String email, String motDePasse, String departement) {
        super(nom, prenom, email, motDePasse);
        this.idGestionnaire = UUID.randomUUID();
        this.departement = departement;
        this.listeClients = new ArrayList<>();
    }

    public UUID getIdGestionnaire() {
        return idGestionnaire;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public List<Client> getListeClients() {
        return listeClients;
    }

    public void addClient(Client client) {
        listeClients.add(client);
    }

    public void removeClient(Client client) {
        listeClients.remove(client);
    }

    public Optional<Client> findClientById(UUID clientId) {
        return listeClients.stream()
                .filter(c -> c.getIdClient().equals(clientId))
                .findFirst();
    }

    @Override
    public String toString() {
        return "Gestionnaire [id=" + idGestionnaire
                + ", nom=" + getNom()
                + ", prenom=" + getPrenom()
                + ", departement=" + departement
                + "]";
    }
}
