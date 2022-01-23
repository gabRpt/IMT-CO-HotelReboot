import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
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
import hotelManagment.impl.ChambreImpl;
import hotelManagment.impl.ClientImpl;
import hotelManagment.impl.PersonnelImpl;
import hotelManagment.impl.PresidentielleImpl;

public class ManageHotel {
	protected Hotel hotel;
	protected HotelManagmentFactory hotelFactory;
	private ManageReservation manageReservation = new ManageReservation(this);
	private ManageChambre manageChambre = new ManageChambre(this);
	private ManagePersonnel managePersonnel = new ManagePersonnel(this);
	
	
	public ManageHotel(Hotel hotel, HotelManagmentFactory hotelFactory) {
		// TODO Auto-generated constructor stub
		this.hotel = hotel;
		this.hotelFactory = hotelFactory;
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
	
	//Méthode générique, pour chaque attribut, demande la valeur à l'utilisateur
	//Retourne un map "nomAttribut":"valeur entrée par l'user"
	protected Map<String, String> getFieldsValues(Field[] classFields) {
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
	
	
	public void reservation(char userInputSecondChar) {		
		switch (userInputSecondChar) {
			case '1':
				manageReservation.createReservation();				
				break;
				
			case '2':
				manageReservation.updateReservation();				
				break;
				
			case '3':
				manageReservation.deleteReservation();
				break;
				
			case '4':
				manageReservation.showReservation();
				break;
				
			case '5':				
				manageReservation.showAllReservation();
				break;
	
			default:
				break;
		}
	}
	
	public void chambre(char userInputSecondChar) {
		switch (userInputSecondChar) {
			case '1':
				manageChambre.createChambre();
				break;
				
			case '2':
				manageChambre.updateChambre();
				break;
				
			case '3':
				manageChambre.deleteChambre();
				break;
				
			case '4':
				manageChambre.showChambre();
				break;
				
			case '5':
				manageChambre.showAllChambre();
				break;
	
			default:
				break;
		}
	}
	
	public void personnel(char userInputSecondChar) {		
		switch (userInputSecondChar) {
			case '1':
				managePersonnel.createPersonnel();
				break;
				
			case '2':
				managePersonnel.updatePersonnel();
				break;
				
			case '3':
				managePersonnel.deletePersonnel();
				break;
				
			case '4':
				managePersonnel.showPersonnel();
				break;
				
			case '5':
				managePersonnel.showAllPersonnel();
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
