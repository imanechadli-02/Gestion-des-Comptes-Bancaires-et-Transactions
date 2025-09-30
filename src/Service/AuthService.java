package Service;

import Model.Client;
import Model.Gestionnaire;
import Model.Personne;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthService {
    private List<Client> clients = new ArrayList<>();
    private List<Gestionnaire> gestionnaires = new ArrayList<>();

    public Client registerClient(String nom, String prenom, String email, String motDePasse) {
        Client c = new Client(nom, prenom, email, motDePasse);
        clients.add(c);
        return c;
    }

    public Gestionnaire registerGestionnaire(String nom, String prenom, String email, String motDePasse, String departement) {
        Gestionnaire g = new Gestionnaire(nom, prenom, email, motDePasse, departement);
        gestionnaires.add(g);
        return g;
    }

    public Optional<Personne> login(String email, String motDePasse) {
        for (Client c : clients) {
            if (c.getEmail().equals(email) && c.getMotDePasse().equals(motDePasse)) {
                return Optional.of(c);
            }
        }
        for (Gestionnaire g : gestionnaires) {
            if (g.getEmail().equals(email) && g.getMotDePasse().equals(motDePasse)) {
                return Optional.of(g);
            }
        }
        return Optional.empty();
    }

    // ---- méthodes utilitaires ----
    public List<Client> getClients() { return clients; }
    public List<Gestionnaire> getGestionnaires() { return gestionnaires; }

    public Optional<Client> findClientById(UUID id) {
        return clients.stream().filter(c -> c.getIdClient().equals(id)).findFirst();
    }

    public Optional<Gestionnaire> findGestionnaireById(UUID id) {
        return gestionnaires.stream().filter(g -> g.getIdGestionnaire().equals(id)).findFirst();
    }

    public boolean assignClientToGestionnaire(UUID clientId, UUID gestionnaireId) {
        Optional<Client> cOpt = findClientById(clientId);
        Optional<Gestionnaire> gOpt = findGestionnaireById(gestionnaireId);
        if (cOpt.isPresent() && gOpt.isPresent()) {
            gOpt.get().addClient(cOpt.get());
            return true;
        }
        return false;
    }
}
