package Service;

import Model.Client;
import Model.Compte;
import Model.Enums.TypeCompte;
import Model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientService {

    public void ajouterCompte(Client client, Scanner scanner) {
        System.out.println("=== Création d'un nouveau compte ===");
        System.out.print("Type de compte (COURANT, EPARGNE, DEPOTATERME) : ");
        String typeStr = scanner.nextLine();
        TypeCompte type;
        try {
            type = TypeCompte.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Type de compte invalide !");
            return;
        }

        System.out.print("Solde initial : ");
        double soldeInitial = scanner.nextDouble();
        scanner.nextLine();

        // Création du compte et ajout au client
        Compte nouveauCompte = new Compte(type, soldeInitial, client);
        client.addCompte(nouveauCompte);

        System.out.println("✅ Compte créé avec succès : " + nouveauCompte);
    }

    // Consulter infos + comptes
    public void afficherInfosClient(Client client) {
        System.out.println("👤 Informations Client :");
        System.out.println("Nom : " + client.getNom());
        System.out.println("Prénom : " + client.getPrenom());
        System.out.println("Email : " + client.getEmail());
        System.out.println("Comptes : ");
        client.getComptes().forEach(System.out::println);
    }

    // Historique des transactions
    public void afficherHistorique(Client client) {
        System.out.println("\n📜 Historique complet des transactions :");
        client.getComptes().forEach(compte -> {
            System.out.println("👉 Compte : " + compte.getNumeroCompte());
            compte.getTransactions().forEach(System.out::println);
        });
    }

    // Filtrer par type
    public List<Transaction> filtrerParType(Client client, String type) {
        return client.getComptes().stream()
                .flatMap(c -> c.getTransactions().stream())
                .filter(t -> t.getType().name().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // Trier par montant
    public List<Transaction> trierParMontant(Client client) {
        return client.getComptes().stream()
                .flatMap(c -> c.getTransactions().stream())
                .sorted(Comparator.comparingDouble(Transaction::getMontant))
                .collect(Collectors.toList());
    }

    // Trier par date
    public List<Transaction> trierParDate(Client client) {
        return client.getComptes().stream()
                .flatMap(c -> c.getTransactions().stream())
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());
    }

    // Calculer solde total
    public double calculerSoldeTotal(Client client) {
        return client.getComptes().stream()
                .mapToDouble(Compte::getSolde)
                .sum();
    }

    // Total dépôts
    public double totalDepots(Client client) {
        return client.getComptes().stream()
                .flatMap(c -> c.getTransactions().stream())
                .filter(t -> t.getType().name().equalsIgnoreCase("DEPOT"))
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    // Total retraits
    public double totalRetraits(Client client) {
        return client.getComptes().stream()
                .flatMap(c -> c.getTransactions().stream())
                .filter(t -> t.getType().name().equalsIgnoreCase("RETRAIT"))
                .mapToDouble(Transaction::getMontant)
                .sum();
    }
}
