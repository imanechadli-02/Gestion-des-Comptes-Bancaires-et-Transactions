package banque.model;

import banque.enums.TypeCompte;
import banque.model.Transaction;
import banque.model.Client;
import java.util.ArrayList;
import java.util.List;

public class Compte {
    private int idCompte;
    private TypeCompte typeCompte;
    private double solde;
    private List<Transaction> transactions;
    private Client client; // association : un compte appartient à un client

    public Compte(int idCompte, TypeCompte typeCompte, double solde, Client client) {
        if (solde < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif.");
        }
        this.idCompte = idCompte;
        this.typeCompte = typeCompte;
        this.solde = solde;
        this.client = client;
        this.transactions = new ArrayList<>();
    }

    // --- Getters & Setters ---
    public int getIdCompte() { return idCompte; }
    public void setIdCompte(int idCompte) { this.idCompte = idCompte; }

    public TypeCompte getTypeCompte() { return typeCompte; }
    public void setTypeCompte(TypeCompte typeCompte) { this.typeCompte = typeCompte; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public List<Transaction> getTransactions() { return transactions; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    // --- Méthodes métier ---
    public void ajouterTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Compte #" + idCompte +
                " [" + typeCompte + "] - Solde: " + solde +
                " | Client: " + client.getPrenom() + " " + client.getNom();
    }
}
