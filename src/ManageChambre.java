import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import hotelManagment.Chambre;
import hotelManagment.Classique;
import hotelManagment.Presidentielle;
import hotelManagment.impl.ChambreImpl;
import hotelManagment.impl.PresidentielleImpl;

public class ManageChambre {
	private Field[] classFieldsClassique;
	private Field[] classFieldsPresidentielle;
	private Map<String, String> myMap;
	private ManageHotel manageHotel;
	
	public ManageChambre(ManageHotel manageHotel) {
		this.manageHotel = manageHotel;
		this.classFieldsClassique = ChambreImpl.class.getDeclaredFields();
		this.classFieldsClassique = Arrays.copyOf(this.classFieldsClassique, this.classFieldsClassique.length-1); //On enleve le dernier elem
		this.classFieldsPresidentielle = Stream.concat(Arrays.stream(this.classFieldsClassique), Arrays.stream(PresidentielleImpl.class.getDeclaredFields())).toArray(Field[]::new);

		// TODO Auto-generated constructor stub
	}
	
	public void createChambre() {		
		Console.afficherChoixClassiqueOuPresidentielle();
		String entree = Console.recupererUneEntree();
		if(entree.equals("1")) {
			Classique chambre = this.manageHotel.hotelFactory.createClassique();
			myMap = this.manageHotel.getFieldsValues(classFieldsClassique);
			chambre.setNumero(Integer.parseInt(myMap.get("numero")));
			chambre.setNbLits(Integer.parseInt(myMap.get("nbLits")));
			chambre.setPrix(Integer.parseInt(myMap.get("prix")));
			this.manageHotel.hotel.getChambre().add(chambre);
			
		}else if (entree.equals("2")) {
			Presidentielle chambre = this.manageHotel.hotelFactory.createPresidentielle();
			myMap = this.manageHotel.getFieldsValues(classFieldsPresidentielle);
			chambre.setNumero(Integer.parseInt(myMap.get("numero")));
			chambre.setNbLits(Integer.parseInt(myMap.get("nbLits")));
			chambre.setPrix(Integer.parseInt(myMap.get("prix")));
			chambre.setBalcon(Boolean.parseBoolean(myMap.get("balcon")));
			chambre.setNbTV(Integer.parseInt(myMap.get("nbTV")));
			chambre.setNbSdB(Integer.parseInt(myMap.get("nbSdB")));
			this.manageHotel.hotel.getChambre().add(chambre);
			
		}else {
			throw new IllegalArgumentException(Console.EDIT_FAIL);
		}
		System.out.println(Console.EDIT_SUCCESS);		
	}
	
	public void updateChambre() {
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		System.out.println(Console.ASK_CHAMBRE_PRESIDENTIELLE);
		String estPres = Console.recupererUneEntree();
		
		Optional<Chambre> chambreAmodifier = this.manageHotel.hotel.getChambre().stream().filter( chambre -> chambre.getNumero() == numero).findFirst();
				
		if(!chambreAmodifier.isEmpty()) {
			if(estPres.equals("y")) {
				Presidentielle pre = (Presidentielle) chambreAmodifier.get();
				myMap = this.manageHotel.getFieldsValues(classFieldsPresidentielle);
				pre.setNumero(Integer.parseInt(myMap.get("numero")));
				pre.setNbLits(Integer.parseInt(myMap.get("nbLits")));
				pre.setPrix(Float.parseFloat(myMap.get("prix")));
				pre.setBalcon(Boolean.parseBoolean(myMap.get("balcon")));
				pre.setNbTV(Integer.parseInt(myMap.get("nbTV")));
				pre.setNbSdB(Integer.parseInt(myMap.get("nbSdB")));
			}else {
				Classique classi = (Classique) chambreAmodifier.get();
				myMap = this.manageHotel.getFieldsValues(classFieldsClassique);
				classi.setNumero(Integer.parseInt(myMap.get("numero")));
				classi.setNbLits(Integer.parseInt(myMap.get("nbLits")));
				classi.setPrix(Float.parseFloat(myMap.get("prix")));
			}
		} else {
			throw new IllegalArgumentException(Console.EDIT_FAIL);
		}
		
		System.out.println(Console.EDIT_SUCCESS);
	}
	
	public void deleteChambre() {
		System.out.println(Console.ASK_NUMCHAMBRE);
		int numero = Integer.parseInt(Console.recupererUneEntree());
		
		boolean deleted = false;
		
		for (Chambre chambre : this.manageHotel.hotel.getChambre()) {
			if (chambre.getNumero() == numero) {
				this.manageHotel.hotel.getChambre().remove(chambre);
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
	
	public void showChambre() {
		System.out.println(Console.ASK_NUMCHAMBRE);
		int num = Integer.parseInt(Console.recupererUneEntree());
		
		for (Chambre chambre : this.manageHotel.hotel.getChambre()) {
			if (chambre.getNumero() == num) {
				System.out.println(chambre.toString());
				break;
			}
		}
	}
	
	public void showAllChambre() {
		this.manageHotel.hotel.getChambre().stream().forEach(System.out::println);
	}
}
