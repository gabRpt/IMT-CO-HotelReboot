import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

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
		boolean edited = false;
		Optional<Reservation> reservation = this.getReservation();
		if(reservation.isPresent()) {
			myMap = this.manageHotel.getFieldsValues(classFields);						
			reservation.get().setIdClient(Integer.parseInt(myMap.get("idClient")));
			reservation.get().setNumChambre(Integer.parseInt(myMap.get("numChambre")));
			reservation.get().setDate(null); //TODO Date
			
			edited = true;
		}
		
		if (edited) {					
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
	}
	
	public void deleteReservation() {
		boolean deleted = false;
		Optional<Reservation> reservation = this.getReservation();
		if(reservation.isPresent()) {
			this.manageHotel.hotel.getReserveration().remove(reservation.get());
			deleted = true;
		}
		
		if (deleted) {					
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
	}
	
	public void showReservation() {
		Optional<Reservation> reservation = this.getReservation();
		if(reservation.isPresent()) {
			System.out.println(reservation.get().toString());			
		}
	}
	
	public void showAllReservation() {
		this.manageHotel.hotel.getReserveration().stream().forEach(System.out::println);
	}
	
	private Optional<Reservation> getReservation() {
		System.out.println(Console.ASK_CLIENT_ID);
		int idClient = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numChambre = Integer.parseInt(Console.recupererUneEntree());
		
		for (Reservation reservation : this.manageHotel.hotel.getReserveration()) {
			if (reservation.getIdClient() == idClient && reservation.getNumChambre() == numChambre) {
				return Optional.of(reservation);
			}
		}
		return Optional.empty();
	}
}
