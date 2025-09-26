package banque.service;

import banque.model.Compte;
import banque.model.Transaction;
import banque.enums.TypeTransaction;
import banque.repository.CompteRepository;
import banque.repository.TransactionRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class CompteService {
    private final CompteRepository compteRepo;
    private final TransactionRepository transactionRepo;
    private int nextTransactionId = 1;

    public CompteService(CompteRepository compteRepo, TransactionRepository transactionRepo) {
        this.compteRepo = compteRepo;
        this.transactionRepo = transactionRepo;
    }

    // --- Dépôt ---
    public void deposer(Compte compte, double montant, String motif) {
        if (montant <= 0) throw new IllegalArgumentException("Montant du dépôt doit être positif.");
        compte.setSolde(compte.getSolde() + montant);

        Transaction t = new Transaction(nextTransactionId++, TypeTransaction.DEPOT,
                montant, motif, compte, null);
        compte.ajouterTransaction(t);
        transactionRepo.ajouterTransaction(t);
    }

    // --- Retrait ---
    public void retirer(Compte compte, double montant, String motif) {
        if (montant <= 0) throw new IllegalArgumentException("Montant du retrait doit être positif.");
        if (compte.getSolde() < montant) throw new ArithmeticException("Solde insuffisant !");
        compte.setSolde(compte.getSolde() - montant);

        Transaction t = new Transaction(nextTransactionId++, TypeTransaction.RETRAIT,
                montant, motif, compte, null);
        compte.ajouterTransaction(t);
        transactionRepo.ajouterTransaction(t);
    }

    // --- Virement ---
    public void virer(Compte source, Compte destination, double montant, String motif) {
        if (montant <= 0) throw new IllegalArgumentException("Montant du virement doit être positif.");
        if (source.getSolde() < montant) throw new ArithmeticException("Solde insuffisant pour le virement !");
        if (destination == null) throw new NoSuchElementException("Compte destination introuvable !");

        // Débiter le compte source
        source.setSolde(source.getSolde() - montant);
        // Créditer le compte destination
        destination.setSolde(destination.getSolde() + montant);

        Transaction t = new Transaction(nextTransactionId++, TypeTransaction.VIREMENT,
                montant, motif, source, destination);
        source.ajouterTransaction(t);
        destination.ajouterTransaction(t);
        transactionRepo.ajouterTransaction(t);
    }

    // --- Calculs ---
    public double calculerSoldeTotal(List<Compte> comptes) {
        return comptes.stream().mapToDouble(Compte::getSolde).sum();
    }

    public double calculerTotalParType(List<Transaction> transactions, TypeTransaction type) {
        return transactions.stream()
                .filter(t -> t.getTypeTransaction() == type)
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    // --- Transactions suspectes ---
    public List<Transaction> transactionsSuspectes(List<Transaction> transactions, double seuil) {
        return transactions.stream()
                .filter(t -> t.getMontant() >= seuil)
                .toList();
    }
}
