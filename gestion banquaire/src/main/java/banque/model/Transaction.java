package banque.model;

import banque.enums.TypeTransaction;
import java.time.LocalDateTime;

public class Transaction {
    private int idTransaction;
    private TypeTransaction typeTransaction;
    private double montant;
    private LocalDateTime date;
    private String motif;
    private Compte compteSource;
    private Compte compteDestination; // optionnel pour les virements

    public Transaction(int idTransaction, TypeTransaction typeTransaction, double montant,
                       String motif, Compte compteSource, Compte compteDestination) {

        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        if (typeTransaction == TypeTransaction.VIREMENT && compteDestination == null) {
            throw new IllegalStateException("Un virement doit avoir un compte destination.");
        }

        this.idTransaction = idTransaction;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.date = LocalDateTime.now(); // par défaut, date de création
        this.motif = motif;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }

    // --- Getters & Setters ---
    public int getIdTransaction() { return idTransaction; }

    public TypeTransaction getTypeTransaction() { return typeTransaction; }

    public double getMontant() { return montant; }

    public LocalDateTime getDate() { return date; }

    public String getMotif() { return motif; }

    public Compte getCompteSource() { return compteSource; }

    public Compte getCompteDestination() { return compteDestination; }

    @Override
    public String toString() {
        String info = "Transaction #" + idTransaction +
                " [" + typeTransaction + "] " +
                "Montant: " + montant +
                " | Date: " + date +
                " | Motif: " + motif +
                " | Compte Source: " + (compteSource != null ? compteSource.getIdCompte() : "N/A");

        if (typeTransaction == TypeTransaction.VIREMENT) {
            info += " | Compte Destination: " +
                    (compteDestination != null ? compteDestination.getIdCompte() : "N/A");
        }
        return info;
    }
}
