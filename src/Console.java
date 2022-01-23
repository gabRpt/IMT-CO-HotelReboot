import java.util.Scanner;

import hotelManagment.Hotel;

public class Console {
	
	//MANAGE HOTEL
	public static final String EDIT_SUCCESS = "Édité avec success";
	public static final String EDIT_FAIL = "Problème rencontré lors de l'édition";
	public static final String ASK_USER_FIELD_VALUE = "Entrez la valeur pour le champ suivant: ";
	public static final String ASK_CLIENT_ID = "Entrez l'identifiant client";
	public static final String ASK_NUMCHAMBRE = "Entrez le numéro de chambre";
	public static final String ASK_CHAMBRE_PRESIDENTIELLE = "Est-ce une chambre présidentielle (y/n) ?";
	public static final String ASK_NB_LITS = "Entrez le nombre de lits";
	public static final String ASK_PRIX_CHAMBRE = "Entrez le prix par nuit";
	public static final String ASK_NB_TV = "Entrez le nombre de television";
	public static final String ASK_NB_SDB = "Entrez le nombre de salle de bain";
	public static final String ASK_BALCON = "Ya-t'il un balcon (true/false) ?";
	public static final String ASK_NUMCHAMBRES_PERSONNEL = "Entrez les numéros de chambres et terminez l'ajout par 0";
	public static final String ASK_NOM_PERSONNEL = "Entrez le nom du personnel";
	public static final String ASK_PRENOM_PERSONNEL = "Entrez le prenom du personnel";
	
	//USER
	public static final String ASK_HOTEL_NAME = "Quel sera le nom de votre Hotel ?";
	public static final String ASK_CHEMIN_ACCESS = "Quel sera le chemin d'accès du modele ?";
	public static final String USER_CHOIX_ERROR = "Erreur dans le choix !";
	
	public static void afficherChoixCreationOuImportation() {
		System.out.println("Bienvenue dans le système de gestion d'hotel");
		System.out.println("1 - charger un hotel :");
		System.out.println("2 - créer un hotel :");
		System.out.println("3 - Quitter");
	}
	
	public static void afficherChoixCreationCheminAcces() {
		System.out.println("Entrez le nom du fichier dans le format suivant:  exemple.xmi");
		System.out.println("Attention, si le fichier existe déjà il sera écrasé");
	}
	
	public static void afficherChoixClassiqueOuPresidentielle() {
		System.out.println("Voulez vous créer :");
		System.out.println("1 - Une chambre classique");
		System.out.println("2 - Une chambre presidentielle");
	}
	
	public static void afficherChoixPrincicpaux(Hotel hotel) {
		System.out.println("\nBienvenue dans l'hotel : " + hotel.getNom());
		
		System.out.println("Que voulez vous faire ?\n");
		
		System.out.println("---------[ RESERVATION ]----------");
		System.out.println("11 - Créer une RESERVATION :");
		System.out.println("12 - Modifier une RESERVATION :");
		System.out.println("13 - Supprimer une RESERVATION :");
		System.out.println("14 - Afficher une RESERVATION :");
		System.out.println("15 - Afficher toutes les RESERVATION :");
		
		System.out.println("---------[ CHAMBRE ]----------");
		System.out.println("21 - Créer une CHAMBRE :");
		System.out.println("22 - Modifier une CHAMBRE :");
		System.out.println("23 - Supprimer une CHAMBRE :");
		System.out.println("24 - Afficher une CHAMBRE :");
		System.out.println("25 - Afficher toutes les CHAMBRE :");

		System.out.println("---------[ PERSONNEL ]----------");
		System.out.println("31 - Créer un PERSONNEL :");
		System.out.println("32 - Modifier un PERSONNEL :");
		System.out.println("33 - Supprimer un PERSONNEL :");
		System.out.println("34 - Afficher un PERSONNEL :");
		System.out.println("35 - Afficher tout le PERSONNEL :");
		
		System.out.println("---------[ CLIENT ]----------");
		System.out.println("41 - Créer un CLIENT :");
		System.out.println("42 - Modifier un CLIENT :");
		System.out.println("43 - Supprimer un CLIENT :");
		System.out.println("44 - Afficher un CLIENT :");
		System.out.println("45 - Afficher tous les CLIENT :");
		
		System.out.println("\n5 - Quitter et enregistrer");
	}
	
	public static String recupererUneEntree() {
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
	}	
}
