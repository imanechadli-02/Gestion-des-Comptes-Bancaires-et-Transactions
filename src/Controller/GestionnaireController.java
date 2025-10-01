package Controller;

import Model.Gestionnaire;
import Model.Client;
import Service.AuthService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class GestionnaireController {
    private Gestionnaire gestionnaire;
    private AuthService authService;
    private Scanner scanner = new Scanner(System.in);

    public GestionnaireController(Gestionnaire gestionnaire, AuthService authService) {
        this.gestionnaire = gestionnaire;
        this.authService = authService;
    }


    public boolean menuGestionnaire() {
        while (true) {
            System.out.println("\n===== MENU GESTIONNAIRE =====");
            System.out.println("1 - Voir mes infos");
            System.out.println("2 - Voir mes clients assignés");
            System.out.println("3 - Ajouter un client à moi (depuis la liste globale)");
            System.out.println("0 - Déconnexion");
            System.out.print("Votre choix : ");
            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 0:
                    System.out.println("👋 Déconnexion...");
                    return true;
                case 1:
                    System.out.println(gestionnaire);
                    break;
                case 2:
                    List<Client> assigned = gestionnaire.getListeClients();
                    System.out.println("DEBUG: nombre clients assignés = " + assigned.size());
                    if (assigned.isEmpty()) {
                        System.out.println("Aucun client assigné.");
                    } else {
                        assigned.forEach(c -> System.out.println(c.getIdClient() + " - " + c));
                    }
                    break;
                case 3:
                    ajouterClientDepuisGlobale();
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        }
    }

    private void ajouterClientDepuisGlobale() {
        List<Client> all = authService.getClients();
        if (all.isEmpty()) {
            System.out.println("Aucun client global disponible.");
            return;
        }
        all.forEach(c -> System.out.println(c.getIdClient() + " - " + c));
        System.out.print("Entrez l'UUID du client à ajouter : ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            Optional<Client> cOpt = all.stream().filter(c -> c.getIdClient().equals(id)).findFirst();
            if (cOpt.isPresent()) {
                gestionnaire.addClient(cOpt.get());
                System.out.println("Client ajouté au gestionnaire.");
            } else {
                System.out.println("Client introuvable.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("UUID invalide.");
        }
    }
}
