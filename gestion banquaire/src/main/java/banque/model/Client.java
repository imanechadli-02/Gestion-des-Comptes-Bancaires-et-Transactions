package banque.model;

import java.util.ArrayList;
import java.util.List;

public class Client extends Personne {
    private int idClient;
    private List<Compte> comptes;

    public Client(int idClient, String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.idClient = idClient;
        this.comptes = new ArrayList<>();
    }

    // --- Getters & Setters ---
    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public List<Compte> getComptes() { return comptes; }

    // --- Méthodes métier ---
    public void ajouterCompte(Compte compte) {
        comptes.add(compte);
    }

    public void supprimerCompte(Compte compte) {
        comptes.remove(compte);
    }

    @Override
    public String toString() {
        return "Client #" + idClient + " - " + super.toString();
    }
}
