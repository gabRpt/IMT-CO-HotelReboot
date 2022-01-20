import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import hotelManagment.Chambre;
import hotelManagment.Classique;
import hotelManagment.Hotel;
import hotelManagment.HotelManagmentFactory;
import hotelManagment.Presidentielle;
import hotelManagment.Reservation;
import hotelManagment.impl.ChambreImpl;
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
	
	//Pour chaque attribut, demande la valeur � l'utilisateur
	//Retourne un map "nomAttribut":"valeur entr�e par l'user"
	private Map<String, String> getFieldsValues(Field[] classFields) {
		Map<String, String> myMap = new HashMap<String, String>();
		String fieldValue = "";
		String currentAttributeName = "";
				
		for (int i = 1; i < classFields.length; i+=2) {
			currentAttributeName = classFields[i].getName();
			System.out.println("Entrez la valeur pour le champ suivant: " + currentAttributeName);
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
				//TODO G�rer les exceptions
				myMap = this.getFieldsValues(classFields);
				
				Reservation newReservation = this.hotelFactory.createReservation();
				newReservation.setIdClient(Integer.parseInt(myMap.get("idClient")));
				newReservation.setNumChambre(Integer.parseInt(myMap.get("numChambre")));
				newReservation.setDate(null); //TODO Date
				this.hotel.getReserveration().add(newReservation);
				
				break;
				
			case '2':{
				System.out.println("Entrez l'identifiant client");
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				System.out.println("Entrez le num�ro de chambre");
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
					System.out.println("Edit� avec succ�s");
				} else {
					System.out.println("Probl�me lors de l'�dition");
				}
				
				break;
			}
				
			case '3':{
				System.out.println("Entrez l'identifiant client");
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				System.out.println("Entrez le num�ro de chambre");
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
					System.out.println("Edit� avec succ�s");
				} else {
					System.out.println("Probl�me lors de l'�dition");
				}
				break;
			}
				
			case '4':
				System.out.println("Entrez l'identifiant client");
				int idClient = Integer.parseInt(Console.recupererUneEntree());
				
				System.out.println("Entrez le num�ro de chambre");
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
		System.out.println("Entrez le num�ro de la chambre a afficher");
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
		System.out.println("Quel est le num�ro de la chambre a supprimer ?");
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
			System.out.println("Edit� avec succ�s");
		} else {
			System.out.println("Probl�me lors de l'�dition");
		}
		
	}

	private void modificationChambre() {
		System.out.println("Quel est le num�ro de la chambre a modifier ?");
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println("Est-ce une chambre pr�sidentielle (y/n) ?");
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
			throw new IllegalArgumentException("Erreur dans le choix !");
		}
		
		System.out.println("Modification de chambre effectu� !");
	}

	public void creationChambre() {

		System.out.println("Quel est le num�ro de la chambre ?");
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println("Quel est le nombre de lits ?");
		int nbrLits = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println("Quel est le prix ?");
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
			
			System.out.println("Quel est le nombre de television ?");
			int nbTv = Integer.parseInt(Console.recupererUneEntree());
			
			System.out.println("Quel est le nombre de salle de bain ?");
			int nbSdb = Integer.parseInt(Console.recupererUneEntree());
			
			System.out.println("Ya-t'il un balcon ?");
			boolean balcon = Boolean.parseBoolean(Console.recupererUneEntree());
			
			chambre.setNbTV(nbTv);
			chambre.setNbSdB(nbSdb);
			chambre.setBalcon(balcon);
			this.hotel.getChambre().add(chambre);
			
		}else {
			throw new IllegalArgumentException("Erreur dans le choix !");
		}
		
		
		
		System.out.println("Creation de chambre effectu� !");
		
		
	}
	
	public void personnel(char userInputSecondChar) {
		switch (userInputSecondChar) {
			case '1':
				
				break;
				
			case '2':
				
				break;
				
			case '3':
				
				break;
				
			case '4':
				
				break;
				
			case '5':
				this.hotel.getPersonnel().stream().forEach(System.out::println);
				break;
	
			default:
				break;
		}
	}
	
	public void client(char userInputSecondChar) {
		switch (userInputSecondChar) {
			case '1':
				
				break;
				
			case '2':
				
				break;
				
			case '3':
				
				break;
				
			case '4':
				
				break;
				
			case '5':
				this.hotel.getClient().stream().forEach(System.out::println);
				break;
	
			default:
				break;
		}
	}
}
