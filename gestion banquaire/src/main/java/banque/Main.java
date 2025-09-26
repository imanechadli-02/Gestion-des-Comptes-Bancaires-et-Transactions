package banque;

import banque.controller.CompteController;
import banque.enums.TypeCompte;
import banque.model.Client;
import banque.model.Compte;
import banque.repository.ClientRepository;
import banque.repository.CompteRepository;
import banque.repository.TransactionRepository;
import banque.service.CompteService;

public class Main {
    public static void main(String[] args) {

        // --- Initialisation des repositories ---
        ClientRepository clientRepo = new ClientRepository();
        CompteRepository compteRepo = new CompteRepository();
        TransactionRepository transactionRepo = new TransactionRepository();

        // --- Initialisation du service ---
        CompteService compteService = new CompteService(compteRepo, transactionRepo);

        // --- Initialisation du controller ---
        CompteController compteController = new CompteController(compteService);

        // --- Création de clients et comptes fictifs ---
        Client client1 = new Client(1, "Chadli", "Imane", "imane@example.com", "pass123");
        Client client2 = new Client(2, "Hammaoui", "Anas", "anas@example.com", "pass123");

        clientRepo.ajouterClient(client1);
        clientRepo.ajouterClient(client2);

        Compte compte1 = new Compte(1, TypeCompte.COURANT, 5000, client1);
        Compte compte2 = new Compte(2, TypeCompte.EPARGNE, 10000, client1);
        Compte compte3 = new Compte(3, TypeCompte.COURANT, 7000, client2);

        client1.ajouterCompte(compte1);
        client1.ajouterCompte(compte2);
        client2.ajouterCompte(compte3);

        compteRepo.ajouterCompte(compte1);
        compteRepo.ajouterCompte(compte2);
        compteRepo.ajouterCompte(compte3);

        // --- Lancement du menu client pour le premier client ---
        compteController.menuClient(client1);
    }
}
