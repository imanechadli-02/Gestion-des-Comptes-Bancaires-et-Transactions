package banque.repository;

import banque.model.Compte;
import java.util.*;

public class CompteRepository {
    private Map<Integer, Compte> comptes = new HashMap<>();

    // Ajouter un compte
    public void ajouterCompte(Compte compte) {
        comptes.put(compte.getIdCompte(), compte);
    }

    // Trouver un compte par ID
    public Optional<Compte> trouverCompteParId(int idCompte) {
        return Optional.ofNullable(comptes.get(idCompte));
    }

    // Supprimer un compte
    public void supprimerCompte(int idCompte) {
        if (!comptes.containsKey(idCompte)) {
            throw new NoSuchElementException("Compte introuvable !");
        }
        comptes.remove(idCompte);
    }

    // Modifier un compte
    public void modifierCompte(Compte compte) {
        if (!comptes.containsKey(compte.getIdCompte())) {
            throw new NoSuchElementException("Compte introuvable !");
        }
        comptes.put(compte.getIdCompte(), compte);
    }

    // Retourner tous les comptes
    public List<Compte> listerComptes() {
        return new ArrayList<>(comptes.values());
    }
}
