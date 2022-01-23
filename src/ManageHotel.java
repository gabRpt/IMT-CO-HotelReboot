import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


import hotelManagment.Chambre;
import hotelManagment.Classique;
import hotelManagment.Client;
import hotelManagment.Hotel;
import hotelManagment.HotelManagmentFactory;
import hotelManagment.Personnel;
import hotelManagment.Presidentielle;
import hotelManagment.Reservation;
import hotelManagment.impl.ChambreImpl;
import hotelManagment.impl.ClientImpl;
import hotelManagment.impl.PersonnelImpl;
import hotelManagment.impl.PresidentielleImpl;
import hotelManagment.impl.ReservationImpl;

public class ManageHotel {
	private Hotel hotel;
	private HotelManagmentFactory hotelFactory;
	
	public ManageHotel(Hotel hotel, HotelManagmentFactory hotelFactory) {
		// TODO Auto-generated constructor stub
		this.hotel = hotel;
		this.hotelFactory = hotelFactory;
	}
	
	//Méthode générique, pour chaque attribut, demande la valeur à l'utilisateur
	//Retourne un map "nomAttribut":"valeur entrée par l'user"
	private Map<String, String> getFieldsValues(Field[] classFields) {
		Map<String, String> myMap = new HashMap<String, String>();
		String fieldValue = "";
		String currentAttributeName = "";
				
		for (int i = 1; i < classFields.length; i+=2) {
			currentAttributeName = classFields[i].getName();
			System.out.println(Console.ASK_USER_FIELD_VALUE + currentAttributeName);
			fieldValue = Console.recupererUneEntree();
			myMap.put(currentAttributeName, fieldValue);
		}
		
		return myMap;
	}
	
	public boolean manageUserInput(String userInput) {
		boolean continueHotelManagement = true;

		switch (userInput.charAt(0)) {
			case '1':
				this.reservation(userInput.charAt(1));
				break;
				
			case '2':
				this.chambre(userInput.charAt(1));
				break;
				
			case '3':
				this.personnel(userInput.charAt(1));
				break;
				
			case '4':
				this.client(userInput.charAt(1));
				break;
	
			case '5':
				continueHotelManagement = false;
				break;
			default:
				continueHotelManagement = false;
				break;
		}
		return continueHotelManagement;
	}
	
	
	public void reservation(char userInputSecondChar) {
		Field[] classFields = ReservationImpl.class.getDeclaredFields();
		Map<String, String> myMap;
		
		switch (userInputSecondChar) {
			case '1':
				//TODO Gï¿½rer les exceptions
				myMap = this.getFieldsValues(classFields);
				
				Reservation newReservation = this.hotelFactory.createReservation();
				newReservation.setIdClient(Integer.parseInt(myMap.get("idClient")));
				newReservation.setNumChambre(Integer.parseInt(myMap.get("numChambre")));
				newReservation.setDate(null); //TODO Date
				this.hotel.getReserveration().add(newReservation);
				
				break;
				
			case '2':{
				System.out.println(Console.ASK_CLIENT_ID);
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				System.out.println(Console.ASK_NUMCHAMBRE);
				int numChambre = Integer.parseInt(Console.recupererUneEntree());
				
				boolean edited = false;
				
				for (Reservation reservation : this.hotel.getReserveration()) {
					if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
						myMap = this.getFieldsValues(classFields);						
						reservation.setIdClient(Integer.parseInt(myMap.get("idClient")));
						reservation.setNumChambre(Integer.parseInt(myMap.get("numChambre")));
						reservation.setDate(null); //TODO Date
						
						edited = true;
						break;
					}
				}
				
				if (edited) {					
					System.out.println(Console.EDIT_SUCCESS);
				} else {
					System.out.println(Console.EDIT_FAIL);
				}
				
				break;
			}
				
			case '3':{
				System.out.println(Console.ASK_CLIENT_ID);
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				System.out.println(Console.ASK_NUMCHAMBRE);
				int numChambre = Integer.parseInt(Console.recupererUneEntree());
				
				boolean deleted = false;
								
				for (Reservation reservation : this.hotel.getReserveration()) {
					if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
						this.hotel.getReserveration().remove(reservation);
						deleted = true;
						break;
					}
				}
				
				if (deleted) {					
					System.out.println(Console.EDIT_SUCCESS);
				} else {
					System.out.println(Console.EDIT_FAIL);
				}
				break;
			}
				
			case '4':
				System.out.println(Console.ASK_CLIENT_ID);
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				System.out.println(Console.ASK_NUMCHAMBRE);
				int numChambre = Integer.parseInt(Console.recupererUneEntree());
				
				for (Reservation reservation : this.hotel.getReserveration()) {
					if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
						System.out.println(
							"idClient: "+reservation.getIdClient()+
							"\nnumChambre: "+reservation.getNumChambre()+
							"\ndate: "+reservation.getDate()
						);
						break;
					}
				}
				break;
				
			case '5':				
				this.hotel.getReserveration().stream().forEach(System.out::println);
				break;
	
			default:
				break;
		}
	}
	
	public void chambre(char userInputSecondChar) {
		switch (userInputSecondChar) {
			case '1':
				creationChambre();
				break;
				
			case '2':
				modificationChambre();
				break;
				
			case '3':
				supprimerChambre();
				break;
				
			case '4':
				afficherChambre();
				break;
				
			case '5':
				this.hotel.getChambre().stream().forEach(System.out::println);
				break;
	
			default:
				break;
		}
	}
	
	private void afficherChambre() {
		System.out.println(Console.ASK_NUMCHAMBRE);
		int num = Integer.parseInt(Console.recupererUneEntree());
		
		for (Chambre chambre : this.hotel.getChambre()) {
			if (chambre.getNumero() == num) {
				System.out.println(
					"NumChmabre: "+chambre.getNumero()+
					"\nnombre de lits: "+chambre.getNbLits()+
					"\nprix: "+chambre.getPrix()
				);
				if(chambre instanceof Presidentielle) {
					Presidentielle chambrep = (Presidentielle) chambre;
					System.out.println(
							"Salle de bain: "+chambrep.getNbSdB()+
							"\nnombre de TV: "+chambrep.getNbTV()
					);
				}
				break;
			}
		}
	}

	private void supprimerChambre() {
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		boolean deleted = false;
		
		for (Chambre chambre : this.hotel.getChambre()) {
			if (chambre.getNumero() == numero) {
				this.hotel.getChambre().remove(chambre);
				deleted = true;
				break;
			}
		}
		
		if (deleted) {					
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
		
	}

	private void modificationChambre() {
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_CHAMBRE_PRESIDENTIELLE);
		String estPres = Console.recupererUneEntree();
		
		Optional<Chambre> chambreAmodifier = this.hotel.getChambre().stream().filter( chambre -> chambre.getNumero() == numero).findFirst();
		Map<String, String> myMap; 
		
		Field[] fieldChambre = ChambreImpl.class.getDeclaredFields();
		Field[] fieldPresidentielle = PresidentielleImpl.class.getDeclaredFields();
		
		Field[] chambreXPresidentielle = Stream.concat(Arrays.stream(fieldChambre), Arrays.stream(fieldPresidentielle)).toArray(Field[]::new);
		
		if(!chambreAmodifier.isEmpty()) {
			
			if(estPres.equals("y")) {
				Presidentielle pre = (Presidentielle) chambreAmodifier.get();
				myMap = this.getFieldsValues(chambreXPresidentielle);
				pre.setNumero(Integer.parseInt(myMap.get("numero")));
				pre.setNbLits(Integer.parseInt(myMap.get("nbLits")));
				pre.setPrix(Float.parseFloat(myMap.get("prix")));
				pre.setBalcon(Boolean.parseBoolean(myMap.get("balcon")));
				pre.setNbTV(Integer.parseInt(myMap.get("nbTV")));
				pre.setNbSdB(Integer.parseInt(myMap.get("nbSdB")));
			}else {
				Classique classi = (Classique) chambreAmodifier.get();
				myMap = this.getFieldsValues(fieldChambre);
				for(int i =0; i< fieldChambre.length; i++) {
					System.out.println(fieldChambre[i]);
				}
				classi.setNumero(Integer.parseInt(myMap.get("numero")));
				classi.setNbLits(Integer.parseInt(myMap.get("nbLits")));
				classi.setPrix(Float.parseFloat(myMap.get("prix")));
			}
				
			
		}else {
			throw new IllegalArgumentException(Console.EDIT_FAIL);
		}
		
		System.out.println(Console.EDIT_SUCCESS);
	}

	public void creationChambre() {

		System.out.println(Console.ASK_NUMCHAMBRE);
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_NB_LITS);
		int nbrLits = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_PRIX_CHAMBRE);
		Float prix = Float.parseFloat(Console.recupererUneEntree());
		
		Console.afficherChoixClassiqueOuPresidentielle();
		String entree = Console.recupererUneEntree();
		if(entree.equals("1")) {
			
			Classique chambre = hotelFactory.createClassique();
			chambre.setNumero(numero);
			chambre.setNbLits(nbrLits);
			chambre.setPrix(prix);
			this.hotel.getChambre().add(chambre);
			
		}else if (entree.equals("2")) {
			
			Presidentielle chambre = hotelFactory.createPresidentielle();
			chambre.setNumero(numero);
			chambre.setNbLits(nbrLits);
			chambre.setPrix(prix);
			
			System.out.println(Console.ASK_NB_TV);
			int nbTv = Integer.parseInt(Console.recupererUneEntree());
			
			System.out.println(Console.ASK_NB_SDB);
			int nbSdb = Integer.parseInt(Console.recupererUneEntree());
			
			System.out.println(Console.ASK_BALCON);
			boolean balcon = Boolean.parseBoolean(Console.recupererUneEntree());
			
			chambre.setNbTV(nbTv);
			chambre.setNbSdB(nbSdb);
			chambre.setBalcon(balcon);
			this.hotel.getChambre().add(chambre);
			
		}else {
			throw new IllegalArgumentException(Console.EDIT_FAIL);
		}
		
		
		
		System.out.println(Console.EDIT_SUCCESS);
		
		
	}
	
	public void personnel(char userInputSecondChar) {
		Field[] classFields = PersonnelImpl.class.getDeclaredFields();
		Map<String, String> myMap;
		
		switch (userInputSecondChar) {
			case '1':{
				//TODO Gï¿½rer les exceptions
				myMap = this.getFieldsValues(classFields);
				System.out.println(Console.ASK_NUMCHAMBRES_PERSONNEL);
				String userInputString = Console.recupererUneEntree();
				Personnel newPersonnel = this.hotelFactory.createPersonnel();
				
				while(!userInputString.equals("0")) {
					int numero = Integer.parseInt(userInputString);
					for (Chambre chambre : this.hotel.getChambre()) {
						if (chambre.getNumero() == numero) {
							newPersonnel.getChambre().add(chambre);
							break;
						}
					}
					
					userInputString = Console.recupererUneEntree();
				}
				
				newPersonnel.setNom(myMap.get("nom"));
				newPersonnel.setPrenom(myMap.get("prenom"));
				
				this.hotel.getPersonnel().add(newPersonnel);				
				break;
			}
				
			case '2':{
				//TODO Gï¿½rer les exceptions				
				System.out.println(Console.ASK_NOM_PERSONNEL);
				String nom = Console.recupererUneEntree();
				
				System.out.println(Console.ASK_PRENOM_PERSONNEL);
				String prenom = Console.recupererUneEntree();
				
				boolean edited = false;
				
				for (Personnel personnel : this.hotel.getPersonnel()) {
					if (personnel.getNom().equals(nom) && personnel.getPrenom().equals(prenom)) {
						myMap = this.getFieldsValues(classFields);						
						personnel.setNom(myMap.get("nom"));
						personnel.setPrenom(myMap.get("prenom"));
						
						System.out.println(Console.ASK_NUMCHAMBRES_PERSONNEL);
						String userInputString = Console.recupererUneEntree();
						while(!userInputString.equals("0")) {
							int numero = Integer.parseInt(userInputString);
							for (Chambre chambre : this.hotel.getChambre()) {
								if (chambre.getNumero() == numero) {
									personnel.getChambre().add(chambre);
									break;
								}
							}
							
							userInputString = Console.recupererUneEntree();
						}
						
						edited = true;
						break;
					}
				}
				
				if (edited) {					
					System.out.println(Console.EDIT_SUCCESS);
				} else {
					System.out.println(Console.EDIT_FAIL);
				}
				
				break;
			}
				
			case '3':{
				System.out.println(Console.ASK_NOM_PERSONNEL);
				String nom = Console.recupererUneEntree();
				
				System.out.println(Console.ASK_PRENOM_PERSONNEL);
				String prenom = Console.recupererUneEntree();
				
				boolean deleted = false;
				
				for (Personnel personnel : this.hotel.getPersonnel()) {
					if (personnel.getNom().equals(nom) && personnel.getPrenom().equals(prenom)) {
						for (Chambre chambre : personnel.getChambre()) {
							chambre.getPersonnel().remove(personnel);
						}
						this.hotel.getPersonnel().remove(personnel);
						deleted = true;
						break;
					}
				}
				
				if (deleted) {					
					System.out.println(Console.EDIT_SUCCESS);
				} else {
					System.out.println(Console.EDIT_FAIL);
				}
				break;
			}
				
			case '4':
				System.out.println(Console.ASK_NOM_PERSONNEL);
				String nom = Console.recupererUneEntree();
				
				System.out.println(Console.ASK_PRENOM_PERSONNEL);
				String prenom = Console.recupererUneEntree();
								
				for (Personnel personnel : this.hotel.getPersonnel()) {
					if (personnel.getNom().equals(nom) && personnel.getPrenom().equals(prenom)) {
						System.out.println(
							"nom: "+personnel.getNom()+
							"\nprenom: "+personnel.getPrenom()
						);
						
						String chambreString = "chambres: ";
						for (Chambre chambre : personnel.getChambre()) {
							chambreString += chambre.getNumero() + " ";
						}
						System.out.println(chambreString + "\n");
						break;
					}
				}
				break;
				
			case '5':
				for (Personnel personnel : this.hotel.getPersonnel()) {
					System.out.println("nom: "+ personnel.getNom());
					System.out.println("prenom: "+ personnel.getPrenom());
					
					String chambreString = "chambres: ";
					for (Chambre chambre : personnel.getChambre()) {
						chambreString += chambre.getNumero() + " ";
					}
					System.out.println(chambreString + "\n");
				}
				break;
	
			default:
				break;
		}
	}
	
	public void client(char userInputSecondChar) {
		Field[] classFields = ClientImpl.class.getDeclaredFields();
		Map<String, String> myMap;
		
		switch (userInputSecondChar) {
			case '1':
				//TODO Gï¿½rer les exceptions
				myMap = this.getFieldsValues(classFields);
				
				Client newClient = this.hotelFactory.createClient();
				newClient.setId(Integer.parseInt(myMap.get("id")));
				newClient.setNom(myMap.get("nom"));
				newClient.setPrenom(myMap.get("prenom"));
				this.hotel.getClient().add(newClient);
				
				break;
				
			case '2':{
				System.out.println(Console.ASK_CLIENT_ID);
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				boolean edited = false;
				
				for (Client client : this.hotel.getClient()) {
					if (client.getId() == idClient) {
						myMap = this.getFieldsValues(classFields);						
						client.setId(Integer.parseInt(myMap.get("id")));
						client.setNom(myMap.get("nom"));
						client.setPrenom(myMap.get("prenom"));
						
						edited = true;
						break;
					}
				}
				
				if (edited) {					
					System.out.println(Console.EDIT_SUCCESS);
				} else {
					System.out.println(Console.EDIT_FAIL);
				}
				
				break;
			}
				
			case '3':{
				System.out.println(Console.ASK_CLIENT_ID);
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				boolean deleted = false;
				
				for (Client client : this.hotel.getClient()) {
					if (client.getId() == idClient) {
						this.hotel.getClient().remove(client);
						deleted = true;
						break;
					}
				}
				
				if (deleted) {					
					System.out.println(Console.EDIT_SUCCESS);
				} else {
					System.out.println(Console.EDIT_FAIL);
				}
				break;
			}
				
			case '4':
				System.out.println(Console.ASK_CLIENT_ID);
				int idClient = Integer.parseInt(Console.recupererUneEntree());
								
				for (Client client : this.hotel.getClient()) {
					if (client.getId() == idClient) {
						System.out.println(
							"id: "+client.getId()+
							"\nnom: "+client.getNom()+
							"\nprenom"+client.getPrenom()
						);
						break;
					}
				}
				break;
				
			case '5':				
				this.hotel.getClient().stream().forEach(System.out::println);
				break;
	
			default:
				break;
		}
	}
}
