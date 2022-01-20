import hotelManagment.HotelManagmentFactory;

public class Main {

	public static void main(String[] args) {		
		HotelManagmentFactory myHotelFactory = HotelManagmentFactory.eINSTANCE;
		ResourceTools myResourceTools = new ResourceTools();
		
		System.out.println("eeeee");
		
		User user = new User(myResourceTools, myHotelFactory);
		user.debutProgramme();
	}
}
