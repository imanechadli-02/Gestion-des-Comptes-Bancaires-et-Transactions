package Controller;

import Model.Client;
import Model.Compte;
import Model.Enums.TypeCompte;
import Service.CompteService;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CompteController {
    private CompteService compteService;
    private Scanner scanner;

    public CompteController() {
        this.compteService = new CompteService();
        this.scanner = new Scanner(System.in);
    }

    public void menuComptes(Client client) {
        while (true) {
            System.out.println("\n===== MENU COMPTES =====");
            System.out.println("1 - Créer un compte");
            System.out.println("2 - Dépôt");
            System.out.println("3 - Retrait");
            System.out.println("4 - Virement");
            System.out.println("5 - Afficher comptes");
            System.out.println("0 - Retour");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creerCompte(client);
                case 2 -> faireDepot(client);
                case 3 -> faireRetrait(client);
                case 4 -> faireVirement(client);
                case 5 -> compteService.afficherComptes(client);
                case 0 -> { return; }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void creerCompte(Client client) {
        System.out.println("Type de compte : 1 = COURANT, 2 = EPARGNE, 3 = DEPOT_A_TERME");
        int t = scanner.nextInt();
        scanner.nextLine();
        TypeCompte type = switch (t) {
            case 1 -> TypeCompte.COURANT;
            case 2 -> TypeCompte.EPARGNE;
            case 3 -> TypeCompte.DEPOT_A_TERME;
            default -> TypeCompte.COURANT;
        };
        System.out.print("Solde initial : ");
        double solde = scanner.nextDouble();
        scanner.nextLine();
        Compte compte = compteService.creerCompte(client, type, solde);
        System.out.println("✅ Compte créé : " + compte);
    }

    private void faireDepot(Client client) {
        Optional<Compte> compte = selectionnerCompte(client);
        if (compte.isPresent()) {
            System.out.print("Montant à déposer : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            compteService.depot(compte.get(), montant);
            System.out.println("✅ Dépôt effectué !");
        }
    }

    private void faireRetrait(Client client) {
        Optional<Compte> compte = selectionnerCompte(client);
        if (compte.isPresent()) {
            System.out.print("Montant à retirer : ");
            double montant = scanner.nextDouble();
            scanner.nextLine();
            try {
                compteService.retrait(compte.get(), montant);
                System.out.println("✅ Retrait effectué !");
            } catch (IllegalStateException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private void faireVirement(Client client) {
        System.out.println("Compte source :");
        Optional<Compte> source = selectionnerCompte(client);
        if (source.isEmpty()) return;

        System.out.println("Compte destination :");
        Optional<Compte> dest = selectionnerCompte(client);
        if (dest.isEmpty()) return;

        System.out.print("Montant à virer : ");
        double montant = scanner.nextDouble();
        scanner.nextLine();

        try {
            compteService.virement(source.get(), dest.get(), montant);
            System.out.println("✅ Virement effectué !");
        } catch (IllegalStateException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private Optional<Compte> selectionnerCompte(Client client) {
        compteService.afficherComptes(client);
        System.out.print("Entrez l'UUID du compte : ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            Optional<Compte> compte = compteService.chercherCompteParId(client, id);
            if (compte.isEmpty()) {
                System.out.println("❌ Compte introuvable !");
            }
            return compte;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ UUID invalide !");
            return Optional.empty();
        }
    }
}
