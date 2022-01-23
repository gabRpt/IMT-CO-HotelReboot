import java.lang.reflect.Field;
import java.util.Map;

import hotelManagment.Reservation;
import hotelManagment.impl.ReservationImpl;

public class ManageReservation {
	private Field[] classFields = ReservationImpl.class.getDeclaredFields();
	private Map<String, String> myMap;
	private ManageHotel manageHotel;

	public ManageReservation(ManageHotel manageHotel) {
		this.manageHotel = manageHotel;
		// TODO Auto-generated constructor stub
	}
	
	public void createReservation() {
		myMap = this.manageHotel.getFieldsValues(classFields);
		
		Reservation newReservation = this.manageHotel.hotelFactory.createReservation();
		newReservation.setIdClient(Integer.parseInt(this.myMap.get("idClient")));
		newReservation.setNumChambre(Integer.parseInt(this.myMap.get("numChambre")));
		newReservation.setDate(null); //TODO Date
		this.manageHotel.hotel.getReserveration().add(newReservation);
	}
	
	public void updateReservation() {
		System.out.println(Console.ASK_CLIENT_ID);
		int idClient = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numChambre = Integer.parseInt(Console.recupererUneEntree());
		
		boolean edited = false;
		
		for (Reservation reservation : this.manageHotel.hotel.getReserveration()) {
			if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
				myMap = this.manageHotel.getFieldsValues(classFields);						
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
	}
	
	public void deleteReservation() {
		System.out.println(Console.ASK_CLIENT_ID);
		int idClient = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numChambre = Integer.parseInt(Console.recupererUneEntree());
		
		boolean deleted = false;
						
		for (Reservation reservation : this.manageHotel.hotel.getReserveration()) {
			if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
				this.manageHotel.hotel.getReserveration().remove(reservation);
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
	
	public void showReservation() {
		System.out.println(Console.ASK_CLIENT_ID);
		int idClient = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numChambre = Integer.parseInt(Console.recupererUneEntree());
		
		for (Reservation reservation : this.manageHotel.hotel.getReserveration()) {
			if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
				System.out.println(reservation.toString());
				break;
			}
		}
	}
	
	public void showAllReservation() {
		this.manageHotel.hotel.getReserveration().stream().forEach(System.out::println);
	}
}
