import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import hotelManagment.Hotel;
import hotelManagment.HotelManagmentFactory;

public class ManageHotel {
	protected Hotel hotel;
	protected HotelManagmentFactory hotelFactory;
	private ManageReservation manageReservation = new ManageReservation(this);
	private ManageChambre manageChambre = new ManageChambre(this);
	private ManagePersonnel managePersonnel = new ManagePersonnel(this);
	private ManageClient manageClient = new ManageClient(this);
	
	
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
		String currentAttributeType = "";
				
		for (int i = 1; i < classFields.length; i+=2) {
			currentAttributeName = classFields[i].getName();
			currentAttributeType = classFields[i].getGenericType().getTypeName();
			System.out.println(Console.ASK_USER_FIELD_VALUE + currentAttributeName + " (type: "+ currentAttributeType +")");
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
		switch (userInputSecondChar) {
			case '1':
				manageClient.createClient();
				break;
				
			case '2':
				manageClient.updateClient();
				break;
				
			case '3':
				manageClient.deleteClient();
				break;
				
			case '4':
				manageClient.showClient();
				break;
				
			case '5':				
				manageClient.showAllClient();
				break;
	
			default:
				break;
		}
	}
}
