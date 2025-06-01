package contacts.emb.service.mock;

import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.emb.service.util.IManagerSecurity;
import contacts.emb.service.util.UtilServices;
import jakarta.inject.Inject;


@Component
public class ServiceConnexion implements IServiceConnexion {
	
	//-------
	// Champs 
	//-------

	@Inject
	private Donnees donnees;
	@Inject
	private IManagerSecurity			managerSecurity;
	
	//-------
	// Actions
	//-------

	@Override
	public DtoCompte sessionUtilisateurOuvrir( String pseudo, String motDePasse ) {
		try {
			DtoCompte	compteConnecté = null;
	        for (DtoCompte compte : donnees.getMapComptes().values()) {
	            if (compte.getPseudo().equals(pseudo)) {
	                if (compte.getMotDePasse().equals(motDePasse)) {
	                	compteConnecté = compte;
	                }
	                break;
	            }
	        }
			managerSecurity.setCompteActif( compteConnecté );
			return compteConnecté;
		} catch ( Exception e ) {
			throw UtilServices.exceptionAnomaly(e);
		}
	}

	@Override
	public void sessionUtilisateurFermer() {
		try {
			managerSecurity.setCompteActif( null );
		} catch ( Exception e ) {
			throw UtilServices.exceptionAnomaly(e);
		}
	}

}
