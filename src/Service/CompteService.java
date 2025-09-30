package Service;

import Model.Client;
import Model.Compte;
import Model.Enums.TypeCompte;

import java.util.List;
import java.util.Optional;

public class CompteService {

    // Créer un compte pour un client
    public Compte creerCompte(Client client, TypeCompte type, double soldeInitial) {
        Compte compte = new Compte(type, soldeInitial, client);
        client.addCompte(compte);
        return compte;
    }

    // Dépôt
    public void depot(Compte compte, double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif !");
        }
        compte.depot(montant);
    }

    // Retrait
    public void retrait(Compte compte, double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif !");
        }
        if (!compte.retrait(montant)) {
            throw new IllegalStateException("Solde insuffisant !");
        }
    }

    // Virement entre deux comptes
    public void virement(Compte source, Compte destination, double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif !");
        }
        if (!source.retrait(montant)) {
            throw new IllegalStateException("Solde insuffisant pour le virement !");
        }
        destination.depot(montant);
    }

    // Afficher les comptes d'un client
    public void afficherComptes(Client client) {
        List<Compte> comptes = client.getComptes();
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé pour ce client.");
        } else {
            comptes.forEach(System.out::println);
        }
    }

    // Rechercher un compte par UUID
    public Optional<Compte> chercherCompteParId(Client client, java.util.UUID compteId) {
        return client.getComptes().stream()
                .filter(c -> c.getIdCompte().equals(compteId))
                .findFirst();
    }
}
