package banque.controller;

import banque.model.Client;
import banque.model.Compte;
import banque.model.Transaction;
import banque.enums.TypeTransaction;
import banque.service.CompteService;

import java.util.List;
import java.util.Scanner;

public class CompteController {
    private final CompteService compteService;
    private final Scanner scanner = new Scanner(System.in);

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    public void menuClient(Client client) {
        int choix;
        do {
            System.out.println("\n=== Menu Client: " + client.getPrenom() + " ===");
            System.out.println("1. Voir mes comptes");
            System.out.println("2. Déposer de l'argent");
            System.out.println("3. Retirer de l'argent");
            System.out.println("4. Virement vers un autre compte");
            System.out.println("5. Voir transactions");
            System.out.println("6. Voir transactions suspectes (>10000)");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine(); // consommer la fin de ligne

            switch (choix) {
                case 1 -> afficherComptes(client);
                case 2 -> effectuerDepot(client);
                case 3 -> effectuerRetrait(client);
                case 4 -> effectuerVirement(client);
                case 5 -> afficherTransactions(client);
                case 6 -> afficherTransactionsSuspectes(client);
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }

    private void afficherComptes(Client client) {
        client.getComptes().forEach(System.out::println);
    }

    private void effectuerDepot(Client client) {
        System.out.print("ID du compte: ");
        int idCompte = scanner.nextInt();
        scanner.nextLine();
        Compte compte = client.getComptes().stream()
                .filter(c -> c.getIdCompte() == idCompte)
                .findFirst()
                .orElse(null);
        if (compte == null) {
            System.out.println("Compte introuvable !");
            return;
        }
        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();
        try {
            compteService.deposer(compte, montant, motif);
            System.out.println("Dépôt effectué !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void effectuerRetrait(Client client) {
        System.out.print("ID du compte: ");
        int idCompte = scanner.nextInt();
        scanner.nextLine();
        Compte compte = client.getComptes().stream()
                .filter(c -> c.getIdCompte() == idCompte)
                .findFirst()
                .orElse(null);
        if (compte == null) {
            System.out.println("Compte introuvable !");
            return;
        }
        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();
        try {
            compteService.retirer(compte, montant, motif);
            System.out.println("Retrait effectué !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void effectuerVirement(Client client) {
        System.out.print("ID du compte source: ");
        int idSource = scanner.nextInt();
        scanner.nextLine();
        Compte compteSource = client.getComptes().stream()
                .filter(c -> c.getIdCompte() == idSource)
                .findFirst()
                .orElse(null);
        if (compteSource == null) {
            System.out.println("Compte source introuvable !");
            return;
        }
        System.out.print("ID du compte destination: ");
        int idDest = scanner.nextInt();
        scanner.nextLine();
        Compte compteDest = client.getComptes().stream()
                .filter(c -> c.getIdCompte() == idDest)
                .findFirst()
                .orElse(null);
        if (compteDest == null) {
            System.out.println("Compte destination introuvable !");
            return;
        }
        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();
        try {
            compteService.virer(compteSource, compteDest, montant, motif);
            System.out.println("Virement effectué !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void afficherTransactions(Client client) {
        client.getComptes().forEach(compte -> {
            System.out.println("\nTransactions du compte #" + compte.getIdCompte());
            compte.getTransactions().forEach(System.out::println);
        });
    }

    private void afficherTransactionsSuspectes(Client client) {
        double seuil = 10000; // seuil pour transactions suspectes
        client.getComptes().forEach(compte -> {
            List<Transaction> suspectes = compteService.transactionsSuspectes(compte.getTransactions(), seuil);
            if (!suspectes.isEmpty()) {
                System.out.println("\nTransactions suspectes du compte #" + compte.getIdCompte());
                suspectes.forEach(System.out::println);
            }
        });
    }
}
