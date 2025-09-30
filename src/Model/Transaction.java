package Model;

import Model.Enums.TypeTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String idTransaction;
    private TypeTransaction type; // utilise l'enum
    private double montant;
    private LocalDateTime date;

    public Transaction(TypeTransaction type, double montant) {
        this.idTransaction = UUID.randomUUID().toString();
        this.type = type;
        this.montant = montant;
        this.date = LocalDateTime.now();
    }

    // Getters
    public String getIdTransaction() { return idTransaction; }
    public TypeTransaction getType() { return type; }
    public double getMontant() { return montant; }
    public LocalDateTime getDate() { return date; }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + idTransaction + '\'' +
                ", type=" + type +
                ", montant=" + montant +
                ", date=" + date +
                '}';
    }
}
