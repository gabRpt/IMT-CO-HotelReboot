import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import hotelManagment.Client;
import hotelManagment.impl.ClientImpl;

public class ManageClient {
	private Field[] classFields = ClientImpl.class.getDeclaredFields();
	private Map<String, String> myMap;
	private ManageHotel manageHotel;

	public ManageClient(ManageHotel manageHotel) {
		this.manageHotel = manageHotel;
		// TODO Auto-generated constructor stub
	}
	
	public void createClient() {
		myMap = this.manageHotel.getFieldsValues(classFields);
		
		Client newClient = this.manageHotel.hotelFactory.createClient();
		newClient.setId(Integer.parseInt(myMap.get("id")));
		newClient.setNom(myMap.get("nom"));
		newClient.setPrenom(myMap.get("prenom"));
		this.manageHotel.hotel.getClient().add(newClient);
	}
	
	public void updateClient() {
		boolean edited = false;
		Optional<Client> client = this.getClient();
		
		if (client.isPresent()) {
			myMap = this.manageHotel.getFieldsValues(classFields);						
			client.get().setId(Integer.parseInt(myMap.get("id")));
			client.get().setNom(myMap.get("nom"));
			client.get().setPrenom(myMap.get("prenom"));
			
			edited = true;
		}
		
		if (edited) {					
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
	}
	
	public void deleteClient() {		
		boolean deleted = false;
		Optional<Client> client = this.getClient();
		
		if (client.isPresent()) {
			this.manageHotel.hotel.getClient().remove(client.get());
			deleted = true;
		}
		
		if (deleted) {					
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
	}
	
	public void showClient() {
		Optional<Client> client = this.getClient();
		
		if (client.isPresent()) {
			System.out.println(client.get().toString());
		}
	}
	
	public void showAllClient() {
		this.manageHotel.hotel.getClient().stream().forEach(System.out::println);
	}
	
	private Optional<Client> getClient(){
		System.out.println(Console.ASK_CLIENT_ID);
		int idClient = Integer.parseInt(Console.recupererUneEntree());
		
		for (Client client : this.manageHotel.hotel.getClient()) {
			if (client.getId() == idClient) {
				return Optional.of(client);
			}
		}
		
		return Optional.empty();
	}
}
