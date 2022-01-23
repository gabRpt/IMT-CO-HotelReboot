import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import hotelManagment.Chambre;
import hotelManagment.Personnel;
import hotelManagment.impl.PersonnelImpl;

public class ManagePersonnel {
	private Field[] classFields = PersonnelImpl.class.getDeclaredFields();
	private Map<String, String> myMap;
	private ManageHotel manageHotel;

	public ManagePersonnel(ManageHotel manageHotel) {
		this.manageHotel = manageHotel;
		// TODO Auto-generated constructor stub
	}
	
	public void createPersonnel() {
		myMap = this.manageHotel.getFieldsValues(classFields);
		System.out.println(Console.ASK_NUMCHAMBRES_PERSONNEL);
		String userInputString = Console.recupererUneEntree();
		Personnel newPersonnel = this.manageHotel.hotelFactory.createPersonnel();
		
		//Récupération des chambres affectées au personnel
		while(!userInputString.equals("0")) {
			int numero = Integer.parseInt(userInputString);
			for (Chambre chambre : this.manageHotel.hotel.getChambre()) {
				if (chambre.getNumero() == numero) {
					newPersonnel.getChambre().add(chambre);
					chambre.getPersonnel().add(newPersonnel);
					break;
				}
			}
			
			userInputString = Console.recupererUneEntree();
		}
		
		newPersonnel.setNom(myMap.get("nom"));
		newPersonnel.setPrenom(myMap.get("prenom"));
		
		this.manageHotel.hotel.getPersonnel().add(newPersonnel);				
	}
	
	public void updatePersonnel() {		
		boolean edited = false;
		Optional<Personnel> personnel = this.getPersonnel();
		
		if(personnel.isPresent()) {
			this.removePersonnelChambre(personnel.get()); //On clear les chambres actuelles
			
			myMap = this.manageHotel.getFieldsValues(classFields);			
			personnel.get().setNom(myMap.get("nom"));
			personnel.get().setPrenom(myMap.get("prenom"));
			
			System.out.println(Console.ASK_NUMCHAMBRES_PERSONNEL);
			String userInputString = Console.recupererUneEntree();
			while(!userInputString.equals("0")) {
				int numero = Integer.parseInt(userInputString);
				for (Chambre chambre : this.manageHotel.hotel.getChambre()) {
					if (chambre.getNumero() == numero) {
						personnel.get().getChambre().add(chambre);
						chambre.getPersonnel().add(personnel.get());
						break;
					}
				}
				userInputString = Console.recupererUneEntree();
			}
			edited = true;
		}
		
		if (edited) {					
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
	}
	
	public void deletePersonnel() {
		boolean deleted = false;
		
		Optional<Personnel> personnel = this.getPersonnel();
		
		if(personnel.isPresent()) {
			this.removePersonnelChambre(personnel.get());
			this.manageHotel.hotel.getPersonnel().remove(personnel.get());
			deleted = true;
		}
		
		if (deleted) {
			System.out.println(Console.EDIT_SUCCESS);
		} else {
			System.out.println(Console.EDIT_FAIL);
		}
	}
	
	public void showPersonnel() {
		Optional<Personnel> personnel = this.getPersonnel();
		
		if (personnel.isPresent()) {
			System.out.println(
				"nom: "+personnel.get().getNom()+
				"\nprenom: "+personnel.get().getPrenom()
			);
			
			String chambreString = "chambres: ";
			for (Chambre chambre : personnel.get().getChambre()) {
				chambreString += chambre.getNumero() + " ";
			}
			System.out.println(chambreString + "\n");
		}
	}
	
	public void showAllPersonnel() {
		for (Personnel personnel : this.manageHotel.hotel.getPersonnel()) {
			System.out.println("nom: "+ personnel.getNom());
			System.out.println("prenom: "+ personnel.getPrenom());
			
			String chambreString = "chambres: ";
			for (Chambre chambre : personnel.getChambre()) {
				chambreString += chambre.getNumero() + " ";
			}
			System.out.println(chambreString + "\n");
		}
	}
	
	private Optional<Personnel> getPersonnel() {
		System.out.println(Console.ASK_NOM_PERSONNEL);
		String nom = Console.recupererUneEntree();
		
		System.out.println(Console.ASK_PRENOM_PERSONNEL);
		String prenom = Console.recupererUneEntree();
		for (Personnel personnel : this.manageHotel.hotel.getPersonnel()) {
			if (personnel.getNom().equals(nom) && personnel.getPrenom().equals(prenom)) {
				return Optional.of(personnel);
			}
		}
		return Optional.empty();
	}
	
	private void removePersonnelChambre(Personnel personnel) {
		//CURRENTLY NOT WORKING
		//Error while trying to remove Personnel from Chambre
		/*
		for (Chambre chambre : personnel.getChambre()) {
			if(chambre.getPersonnel().contains(personnel)) {
				chambre.getPersonnel().remove(personnel);
			}
		}
		personnel.getChambre().clear();*/
	}
}
