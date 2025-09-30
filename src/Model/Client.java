package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client extends Personne {
    private UUID idClient;
    private List<Compte> comptes;

    public Client(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.idClient = UUID.randomUUID();
        this.comptes = new ArrayList<>();
    }

    public UUID getIdClient() {
        return idClient;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    // Gestion des comptes
    public void addCompte(Compte compte) {
        comptes.add(compte);
    }

    public void removeCompte(Compte compte) {
        comptes.remove(compte);
    }

    public double getSoldeTotal() {
        return comptes.stream().mapToDouble(Compte::getSolde).sum();
    }

    @Override
    public String toString() {
        return "Client [id=" + idClient + ", " + super.toString() + ", nbComptes=" + comptes.size() + "]";
    }
}
