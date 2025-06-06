package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoCompte;
import contacts.commun.exception.ExceptionValidation;
import contacts.commun.service.IServiceCompte;
import contacts.emb.dao.IDaoCompte;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.data.Compte;
import contacts.emb.data.mapper.IMapper;
import contacts.emb.service.util.IManagerSecurity;
import contacts.emb.service.util.UtilServices;
import jakarta.inject.Inject;


@Component
public class ServiceCompte implements IServiceCompte {
	
	//-------
	// Champs 
	//-------

	@Inject
	private IManagerSecurity	managerSecurity;
	@Inject
	private	IManagerTransaction	managerTransaction;
	@Inject
	private IMapper			mapper;
	@Inject
	private IDaoCompte			daoCompte;
	
	//-------
	// Actions 
	//-------

	@Override
	public int inserer( DtoCompte dtoCompte ) throws ExceptionValidation {
		
		try {
			
			managerSecurity.verifierAutorisationAdmin();
			verifierValiditeDonnees( dtoCompte );

			managerTransaction.begin();
			int id = daoCompte.inserer( mapper.map( dtoCompte ) );
			managerTransaction.commit();
			return id;

		} catch ( Exception e ) {
	    	managerTransaction.rollbackSiApplicable();
			throw UtilServices.exceptionValidationOrAnomaly(e);
		}
	}

	@Override
	public void modifier( DtoCompte dtoCompte ) throws ExceptionValidation { 
		try {

			managerSecurity.verifierAutorisationAdmin();
			verifierValiditeDonnees( dtoCompte );

			managerTransaction.begin();
			daoCompte.modifier( mapper.map( dtoCompte ) );
			managerTransaction.commit();

		} catch ( Exception e ) {
	    	managerTransaction.rollbackSiApplicable();
			throw UtilServices.exceptionValidationOrAnomaly(e);
		}
		
	}

	@Override
	public void supprimer( int idCompte ) throws ExceptionValidation  {
		try {
			
			managerSecurity.verifierAutorisationAdmin();
			if ( managerSecurity.getIdCompteActif() == idCompte ) {
				throw new ExceptionValidation("Vous ne pouvez pas supprimer le compte avec lequel vous êtes connecté !");
			}

			managerTransaction.begin();
			daoCompte.supprimer(idCompte);
			managerTransaction.commit();

		} catch ( Exception e ) {
	    	managerTransaction.rollbackSiApplicable();
			throw UtilServices.exceptionValidationOrAnomaly(e);
		}
	}

	
	@Override
	public DtoCompte retrouver( int idCompte ) {
		try {
			
			managerSecurity.verifierAutorisationAdmin();
			return mapper.map( daoCompte.retrouver(idCompte) );

		} catch ( Exception e ) {
			throw UtilServices.exceptionAnomaly(e);
		}
	}

	
	@Override
	public List<DtoCompte> listerTout() {
		try {

			managerSecurity.verifierAutorisationAdmin();
			List<DtoCompte> liste = new ArrayList<>();
			for( Compte compte : daoCompte.listerTout() ) {
				liste.add( mapper.map( compte ) );
			}
			return liste;

		} catch ( Exception e ) {
			throw UtilServices.exceptionAnomaly(e);
		}
	}
	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void verifierValiditeDonnees( DtoCompte dtoCompte ) throws ExceptionValidation {
		
		StringBuilder message = new StringBuilder();
		
		if ( dtoCompte.getPseudo() == null || dtoCompte.getPseudo().isEmpty() ) {
			message.append( "\nLe pseudo est absent." );
		} else 	if ( dtoCompte.getPseudo().length() < 3 ) {
			message.append( "\nLe pseudo est trop court." );
		} else 	if ( dtoCompte.getPseudo().length() > 25 ) {
			message.append( "\nLe pseudo est trop long." );
		} else 	if ( ! daoCompte.verifierUnicitePseudo( dtoCompte.getPseudo(), dtoCompte.getId() ) ) {
			message.append( "\nLe pseudo " + dtoCompte.getPseudo() + " est déjà utilisé." );
		}
		
		if ( dtoCompte.getMotDePasse() == null || dtoCompte.getMotDePasse().isEmpty() ) {
			message.append( "\nLe mot de passe est absent." );
		} else 	if ( dtoCompte.getMotDePasse().length() < 3 ) {
			message.append( "\nLe mot de passe est trop court." );
		} else 	if ( dtoCompte.getMotDePasse().length() > 25 ) {
			message.append( "\nLe mot de passe est trop long." );
		}
		
		if ( dtoCompte.getEmail() == null || dtoCompte.getEmail().isEmpty() ) {
			message.append( "\nL'adresse e-mail est absente." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
	}
	
}
