# Gestion-des-Comptes-Bancaires-et-Transactions

Contexte du projet
Contexte du projet

Une banque marocaine souhaite digitaliser la gestion des comptes et des transactions de ses clients. L’objectif est de fournir aux clients et aux gestionnaires un outil simple, fiable et sécurisé pour gérer les comptes bancaires (courant, épargne, dépôt à terme) et suivre toutes les transactions (dépôts, retraits, virements), tout en assurant un suivi précis et des statistiques détaillées.

Vous êtes chargé de concevoir et développer une application console Java permettant de simuler la gestion des comptes bancaires et des transactions, en respectant les règles de gestion et les bonnes pratiques de programmation orientée objet, tout en exploitant les collections, l’API Java Time, les Streams

Objectifs / Fonctionnalités principales

Pour les clients :

• Consulter leurs informations personnelles et leurs comptes bancaires.

• Afficher l’historique de leurs transactions (dépôt, retrait, virement).

• Filtrer et trier les transactions par type, montant ou date.

• Calculer le solde total et le montant total des dépôts ou retraits.

Pour les gestionnaires de comptes :

• Créer, modifier ou supprimer un client et ses comptes bancaires.

• Ajouter, modifier ou supprimer une transaction pour un compte :

o Dépôts et retraits : ajoutables pour tous les comptes.

o Virements : uniquement si le compte dispose d’un solde suffisant.

• Consulter et filtrer les transactions d’un compte ou de tous les comptes d’un client.

• Calcul automatique du solde et des totaux par client et par type de transaction.

• Identifier les transactions inhabituelles ou suspectes (montants élevés, opérations répétitives).

Règles de gestion

• Chaque client peut posséder plusieurs comptes bancaires.

• Chaque compte appartient à un seul client.

• Un compte peut avoir plusieurs transactions.

• Les virements ne peuvent être effectués que si le solde du compte est suffisant.

• Gestion des exceptions obligatoire :

o Montant négatif → IllegalArgumentException

o Client ou compte introuvable → NoSuchElementException

o Transaction non valide → IllegalStateException

o Solde insuffisant pour un virement → ArithmeticException

Modélisation des entités (POO / UML-ready)

• Personne (classe abstraite) : nom, prénom, email, motDePasse.

• Client : idClient, comptes (ArrayList<Compte>).

• Compte : idCompte, typeCompte (Enum), solde, transactions (ArrayList<Transaction>), client (association).

• Transaction : idTransaction, typeTransaction (DÉPÔT, RETRAIT, VIREMENT), montant, date, motif, compteSource (association), compteDestination (optionnel pour virement).

• TypeCompte (Enum) : COURANT, ÉPARGNE, DEPOTATERME.

• TypeTransaction (Enum) : DEPOT, RETRAIT, VIREMENT.

Architecture technique et technologies utilisées (MVC)

• Model : classes Client, Compte, Transaction avec attributs et méthodes métier.

• View : interface console pour afficher menus et informations.

• Controller : gestion des interactions, validation et exécution des règles de gestion.

• Collections : List, Map pour stocker clients, comptes et transactions.

• Streams et Lambda : filtrage, tri, agrégation.

• Functional Interfaces : Predicate, Function, Consumer, Supplier.

• Optional pour gérer les valeurs absentes.

• Java Time API pour les dates de transactions.

• JDBC pour la persistance (sauvegarde et récupération des données).


User Stories

Pour les clients :

• En tant que client, je veux consulter mes informations personnelles et mes comptes bancaires.

• En tant que client, je veux voir l’historique complet de mes transactions.

• En tant que client, je veux filtrer et trier mes transactions par type, montant ou date.

• En tant que client, je veux calculer le solde total et les montants totaux déposés ou retirés.

Pour les gestionnaires :

• En tant que gestionnaire, je veux créer, modifier ou supprimer un client et ses comptes.

• En tant que gestionnaire, je veux ajouter, modifier ou supprimer une transaction pour un compte.

• En tant que gestionnaire, je veux consulter et filtrer les transactions d’un client.

• En tant que gestionnaire, je veux identifier les transactions suspectes.