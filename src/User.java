import org.eclipse.emf.ecore.resource.Resource;

import hotelManagment.Hotel;
import hotelManagment.HotelManagmentFactory;

public class User {

	public ResourceTools myResourceTools;
	public HotelManagmentFactory myHotelFactory;
	private Hotel myHotel;
	private String modelFilename;
	
	public User(ResourceTools ressource,HotelManagmentFactory hotelFactory) {
		this.myResourceTools = ressource;
		this.myHotelFactory = hotelFactory;	
	}
	
	void debutProgramme() {
		boolean continueProgram = true;
		
		while(continueProgram) {
			Console.afficherChoixCreationOuImportation();
			switch (Console.recupererUneEntree()) {
		        case "1": {
		        	chargerHotel();
		            break;
		        }
		        case "2": {
		        	creerHotel();
		            break;
		        }
		        case "3": {
		        	continueProgram = false;
		            break;
		        }
		        default:
		            throw new IllegalArgumentException(Console.USER_CHOIX_ERROR);
	       }
		}
		
		System.exit(0);
	}
	
	private void creerHotel() {		

		this.myHotel = this.myHotelFactory.createHotel();
		
		System.out.println(Console.ASK_HOTEL_NAME);
		this.myHotel.setNom(Console.recupererUneEntree());
		System.out.println("Adresse: ");
		this.myHotel.setAdresse(Console.recupererUneEntree());
		System.out.println("Catégorie: ");
		this.myHotel.setCategorie(Console.recupererUneEntree());
		System.out.println("Chiffre d'affaire: ");
		this.myHotel.setChiffreAffaire(Integer.parseInt(Console.recupererUneEntree()));
		
		this.managerHotel();
	}

	void chargerHotel() {
		System.out.println(Console.ASK_CHEMIN_ACCESS);
		try {
			this.modelFilename = Console.recupererUneEntree();
			Resource rr = this.myResourceTools.getResource(this.modelFilename);
			this.myHotel = (Hotel)(rr.getContents().get(0));
			managerHotel();
		}catch (Exception e) {
			throw new IllegalArgumentException(Console.USER_CHOIX_ERROR);
		}
	}

	void managerHotel() {
		ManageHotel mHotelManager = new ManageHotel(this.myHotel,this.myHotelFactory);
		Console.afficherChoixPrincicpaux(this.myHotel);
		String userInput = Console.recupererUneEntree();
		
		while(mHotelManager.manageUserInput(userInput)) {
			Console.afficherChoixPrincicpaux(this.myHotel);
			userInput = Console.recupererUneEntree();
		}
		
		this.sauvegarderHotel();
	}
	
	void sauvegarderHotel() {
		Console.afficherChoixCreationCheminAcces();
		this.myResourceTools.saveResource(Console.recupererUneEntree(), this.myHotel);
	}
}
