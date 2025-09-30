package Model;

import Model.Enums.TypeCompte;
import Model.Enums.TypeTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Compte {
    private UUID idCompte;
    private TypeCompte type;
    private double solde;
    private Client proprietaire;
    private List<Transaction> transactions;

    public Compte(TypeCompte type, double soldeInitial, Client proprietaire) {
        this.idCompte = UUID.randomUUID();
        this.type = type;
        this.solde = soldeInitial;
        this.proprietaire = proprietaire;
        this.transactions = new ArrayList<>();
    }

    // Getters
    public UUID getNumeroCompte() {
        return idCompte;
    }
    public UUID getIdCompte() { return idCompte; }
    public TypeCompte getType() { return type; }
    public double getSolde() { return solde; }
    public Client getProprietaire() { return proprietaire; }
    public List<Transaction> getTransactions() { return transactions; }

    // Dépôt
    public void depot(double montant) {
        if (montant <= 0) throw new IllegalArgumentException("Montant de dépôt invalide !");
        solde += montant;
        transactions.add(new Transaction(TypeTransaction.DEPOT, montant));
    }

    // Retrait
    public boolean retrait(double montant) {
        if (montant <= 0) throw new IllegalArgumentException("Montant de retrait invalide !");
        if (solde < montant) throw new ArithmeticException("Solde insuffisant pour le retrait !");
        solde -= montant;
        transactions.add(new Transaction(TypeTransaction.RETRAIT, montant));
        return false;
    }

    // Virement vers un autre compte
    public void virement(double montant, Compte destinataire) {
        if (destinataire == null) throw new IllegalArgumentException("Compte destinataire invalide !");
        if (montant <= 0) throw new IllegalArgumentException("Montant de virement invalide !");
        if (solde < montant) throw new ArithmeticException("Solde insuffisant pour le virement !");
        this.solde -= montant;
        destinataire.solde += montant;

        // Ajouter les transactions pour les deux comptes
        this.transactions.add(new Transaction(TypeTransaction.VIREMENT, montant));
        destinataire.getTransactions().add(new Transaction(TypeTransaction.VIREMENT, montant));
    }

    @Override
    public String toString() {
        return "Compte [id=" + idCompte + ", type=" + type + ", solde=" + solde +
                ", proprietaire=" + proprietaire.getNom() + " " + proprietaire.getPrenom() + "]";
    }
}
