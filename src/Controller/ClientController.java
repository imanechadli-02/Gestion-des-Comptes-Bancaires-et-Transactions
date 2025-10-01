package Controller;

import Model.Client;
import Model.Compte;
import Model.Transaction;
import Service.AuthService;
import Service.ClientService;

import java.util.List;
import java.util.Scanner;

public class ClientController {
    private Client client;
    private ClientService clientService;
    private Scanner scanner;

    public ClientController(Client client, AuthService authService) {
        this.client = client;
        this.clientService = new ClientService();
        this.scanner = new Scanner(System.in);
    }

    public boolean menuClient() {
        while (true) {
            System.out.println("\n===== MENU CLIENT =====");
            System.out.println("1 - Ajouter un compte");
            System.out.println("2 - Voir mes informations et mes comptes");
            System.out.println("3 - Déposer de l'argent");
            System.out.println("4 - Retirer de l'argent");
            System.out.println("5 - Faire un virement");
            System.out.println("6 - Voir l’historique des transactions");
            System.out.println("7 - Filtrer mes transactions");
            System.out.println("8 - Trier mes transactions");
            System.out.println("9 - Calculer mes soldes et totaux");
            System.out.println("0 - Déconnexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 0 -> {
                    System.out.println("👋 Déconnexion réussie.");
                    return true; // 🔥 renvoie un signal de déconnexion
                }
                case 1 -> clientService.ajouterCompte(client, scanner);
                case 2 -> clientService.afficherInfosClient(client);
                case 3 -> depotArgent();
                case 4 -> retraitArgent();
                case 5 -> virementEntreComptes();
                case 6 -> clientService.afficherHistorique(client);
                case 7 -> filtrerTransactions();
                case 8 -> trierTransactions();
                case 9 -> afficherTotaux();
                default -> System.out.println("❌ Choix invalide !");
            }
        }
    }



    private void depotArgent() {
        Compte compte = choisirCompte();
        if (compte != null) {
            System.out.print("Montant à déposer : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            try {
                compte.depot(montant);
                System.out.println("✅ Dépôt effectué ! Nouveau solde : " + compte.getSolde());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private void retraitArgent() {
        Compte compte = choisirCompte();
        if (compte != null) {
            System.out.print("Montant à retirer : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            try {
                compte.retrait(montant);
                System.out.println("✅ Retrait effectué ! Nouveau solde : " + compte.getSolde());
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private void virementEntreComptes() {
        System.out.println("Compte source :");
        Compte source = choisirCompte();
        System.out.println("Compte destinataire :");
        Compte destinataire = choisirCompte();
        if (source != null && destinataire != null && !source.equals(destinataire)) {
            System.out.print("Montant à virer : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            try {
                source.virement(montant, destinataire);
                System.out.println("✅ Virement effectué !");
                System.out.println("Solde source : " + source.getSolde() + ", Solde destinataire : " + destinataire.getSolde());
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("❌ " + e.getMessage());
            }
        } else {
            System.out.println("❌ Comptes invalides !");
        }
    }

    private Compte choisirCompte() {
        List<Compte> comptes = client.getComptes();
        if (comptes.isEmpty()) {
            System.out.println("❌ Vous n'avez aucun compte !");
            return null;
        }
        System.out.println("Sélectionnez le compte :");
        for (int i = 0; i < comptes.size(); i++) {
            System.out.println((i + 1) + " - " + comptes.get(i));
        }
        System.out.print("Votre choix : ");
        int choix = scanner.nextInt() - 1;
        scanner.nextLine();
        if (choix >= 0 && choix < comptes.size()) {
            return comptes.get(choix);
        }
        System.out.println("❌ Choix invalide !");
        return null;
    }

    private void filtrerTransactions() {
        System.out.print("Type (DEPOT, RETRAIT, VIREMENT) : ");
        String typeStr = scanner.nextLine();
        List<Transaction> resultat = clientService.filtrerParType(client, typeStr);
        resultat.forEach(System.out::println);
    }

    private void trierTransactions() {
        System.out.println("1 - Par montant");
        System.out.println("2 - Par date");
        int choix = scanner.nextInt();
        scanner.nextLine();

        List<Transaction> resultat = (choix == 1)
                ? clientService.trierParMontant(client)
                : clientService.trierParDate(client);

        resultat.forEach(System.out::println);
    }

    private void afficherTotaux() {
        double soldeTotal = clientService.calculerSoldeTotal(client);
        double totalDepots = clientService.totalDepots(client);
        double totalRetraits = clientService.totalRetraits(client);

        System.out.println("\n💰 Solde total : " + soldeTotal);
        System.out.println("➕ Total dépôts : " + totalDepots);
        System.out.println("➖ Total retraits : " + totalRetraits);
    }
}
