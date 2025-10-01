package Controller;

import Service.AuthService;
import Model.Personne;
import Model.Client;
import Model.Gestionnaire;

import java.util.Optional;
import java.util.Scanner;

public class AuthController {
    private AuthService authService;
    private Scanner scanner;

    public AuthController(AuthService authService) {
        this.authService = authService;
        this.scanner = new Scanner(System.in);
    }

    // Menu principal Authentification
    public Optional<Personne> menuAuth() {
        while (true) {
            System.out.println("\n===== MENU AUTHENTIFICATION =====");
            System.out.println("1 - S'inscrire (Client)"); 
            System.out.println("2 - S'inscrire (Gestionnaire)");
            System.out.println("3 - Se connecter");
            System.out.println("0 - Quitter");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 0 -> {
                    System.out.println("👋 Au revoir !");
                    return Optional.empty();
                }
                case 1 -> registerClient();
                case 2 -> registerGestionnaire();
                case 3 -> {
                    Optional<Personne> user = login();
                    if (user.isPresent()) return user;
                }
                default -> System.out.println("❌ Choix invalide !");
            }
        }
    }

    // Inscription Client
    private void registerClient() {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        Client client = authService.registerClient(nom, prenom, email, motDePasse);
        System.out.println("✅ Client enregistré : " + client);
    }

    // Inscription Gestionnaire
    private void registerGestionnaire() {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();
        System.out.print("Département : ");
        String departement = scanner.nextLine();

        Gestionnaire gestionnaire = authService.registerGestionnaire(nom, prenom, email, motDePasse, departement);
        System.out.println("✅ Gestionnaire enregistré : " + gestionnaire);
    }

    // Login
    private Optional<Personne> login() {
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        Optional<Personne> user = authService.login(email, motDePasse);
        if (user.isPresent()) {
            Personne personne = user.get();
            System.out.println("✅ Connexion réussie : " + personne);

            if (personne instanceof Client client) {
                ClientController clientController = new ClientController(client, authService);
                boolean deconnecte = clientController.menuClient();
                if (deconnecte) {
                    return Optional.empty(); // 🔥 retour au menu auth
                }
            } else if (personne instanceof Gestionnaire gestionnaire) {
                GestionnaireController gestionnaireController = new GestionnaireController(gestionnaire, authService);
                boolean deconnecte = gestionnaireController.menuGestionnaire();
                if (deconnecte) {
                    return Optional.empty();
                }
            }
        }
        return user;
    }


}
