package banque.repository;

import banque.model.Transaction;
import java.util.*;

public class TransactionRepository {
    private Map<Integer, Transaction> transactions = new HashMap<>();

    // Ajouter une transaction
    public void ajouterTransaction(Transaction transaction) {
        transactions.put(transaction.getIdTransaction(), transaction);
    }

    // Trouver une transaction par ID
    public Optional<Transaction> trouverTransactionParId(int idTransaction) {
        return Optional.ofNullable(transactions.get(idTransaction));
    }

    // Supprimer une transaction
    public void supprimerTransaction(int idTransaction) {
        if (!transactions.containsKey(idTransaction)) {
            throw new NoSuchElementException("Transaction introuvable !");
        }
        transactions.remove(idTransaction);
    }

    // Modifier une transaction
    public void modifierTransaction(Transaction transaction) {
        if (!transactions.containsKey(transaction.getIdTransaction())) {
            throw new NoSuchElementException("Transaction introuvable !");
        }
        transactions.put(transaction.getIdTransaction(), transaction);
    }

    // Retourner toutes les transactions
    public List<Transaction> listerTransactions() {
        return new ArrayList<>(transactions.values());
    }

    // Filtrer les transactions par type (ex : DEPOT, RETRAIT, VIREMENT)
    public List<Transaction> filtrerParType(String type) {
        return transactions.values().stream()
                .filter(t -> t.getTypeTransaction().name().equalsIgnoreCase(type))
                .toList();
    }

    // Trier les transactions par montant (ascendant ou descendant)
    public List<Transaction> trierParMontant(boolean asc) {
        return transactions.values().stream()
                .sorted((t1, t2) -> asc ?
                        Double.compare(t1.getMontant(), t2.getMontant()) :
                        Double.compare(t2.getMontant(), t1.getMontant()))
                .toList();
    }

    // Trier les transactions par date
    public List<Transaction> trierParDate(boolean asc) {
        return transactions.values().stream()
                .sorted((t1, t2) -> asc ?
                        t1.getDate().compareTo(t2.getDate()) :
                        t2.getDate().compareTo(t1.getDate()))
                .toList();
    }
}
