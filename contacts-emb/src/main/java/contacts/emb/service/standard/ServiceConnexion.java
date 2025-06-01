package contacts.emb.service.standard;

import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceConnexion;
import contacts.emb.dao.IDaoCompte;
import contacts.emb.data.mapper.IMapper;
import contacts.emb.service.util.IManagerSecurity;
import contacts.emb.service.util.UtilServices;
import jakarta.inject.Inject;


@Component
public class ServiceConnexion implements IServiceConnexion {
	
	//-------
	// Champs 
	//-------

	@Inject
	private IManagerSecurity	managerSecurity;
	@Inject
	private IMapper			mapper;
	@Inject
	private IDaoCompte			daoCompte;
	
	//-------
	// Actions
	//-------

	@Override
	public DtoCompte sessionUtilisateurOuvrir( String pseudo, String motDePasse ) {
		try {
			DtoCompte compte = mapper.map( daoCompte.validerAuthentification(pseudo, motDePasse) );
			managerSecurity.setCompteActif( compte );
			return compte;
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
