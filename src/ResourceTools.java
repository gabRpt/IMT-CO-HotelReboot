import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import hotelManagment.Hotel;
import hotelManagment.HotelManagmentPackage;

public class ResourceTools {
	//G�n�ration d'un fichier XMI
	// Cr�ation d'un ResourceSet
	ResourceSet rs = new ResourceSetImpl();
	
	public ResourceTools() {
		// TODO Auto-generated constructor stub
		// Mapping entre l'extension du fichier du mod�le et le format de s�rialisation (xmi)
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
		    "xmi",
		    new XMIResourceFactoryImpl()
		);
		// Enregistrement de notre m�tamod�le dans la liste des m�tamod�les connus
		rs.getPackageRegistry().put(
				HotelManagmentPackage.eNS_URI,
				HotelManagmentPackage.eINSTANCE
		);
	}
	
	public void saveResource(String filename, Hotel racineModele) {
		// Cr�ation de la ressource avec un chemin de fichier o� sauvegarder le mod�le
		Resource resource = rs.createResource(URI.createFileURI(filename));
		// attention, URI -> org.eclipse.emf.common.util.URI

		// On ajoute notre mod�le dans la ressource
		resource.getContents().add(racineModele);
		
		// la m�thode save de la Resource accepte une map contenant les options de s�rialisation
		try {
		    resource.save(Collections.EMPTY_MAP);
		}
		catch (IOException e) {
		    System.out.println("erreur durant la sauvegarde du mod�le : " + e.getMessage());
		}
	}
	
	public Resource getResource(String filename) {
		return rs.getResource(URI.createFileURI(filename),true);
	}
}
