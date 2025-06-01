package contacts.ejb.service.standard;
import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.ejb.dao.IDaoCompte;
import contacts.ejb.data.mapper.IMapper;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
//import contacts.emb.service.util.IManagerSecurity;
//import contacts.emb.service.util.UtilServices;
import jakarta.inject.Inject;


@Remote
@Stateless
public class ServiceConnexion implements IServiceConnexion {
	
	//-------
	// Champs 
	//-------

//	@Inject
//	private IManagerSecurity	managerSecurity;
	@Inject
	private IMapper			mapper;
	@Inject
	private IDaoCompte			daoCompte;
	
	//-------
	// Actions
	//-------

	@Override
	public DtoCompte sessionUtilisateurOuvrir( String pseudo, String motDePasse ) {
//		try {
			DtoCompte compte = mapper.map( daoCompte.validerAuthentification(pseudo, motDePasse) );
//			managerSecurity.setCompteActif( compte );
			return compte;
//		} catch ( Exception e ) {
//			throw UtilServices.exceptionAnomaly(e);
//		}
	}


	@Override
	public void sessionUtilisateurFermer() {
//		try {
//			managerSecurity.setCompteActif( null );
//		} catch ( Exception e ) {
//			throw UtilServices.exceptionAnomaly(e);
//		}
	}

}
