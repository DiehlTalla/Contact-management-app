package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.commun.exception.ExceptionValidation;
import contacts.commun.service.IServicePersonne;
import contacts.emb.dao.IDaoPersonne;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.data.Personne;
import contacts.emb.data.mapper.IMapper;
import contacts.emb.service.util.IManagerSecurity;
import contacts.emb.service.util.UtilServices;
import jakarta.inject.Inject;


@Component
public class ServicePersonne implements IServicePersonne {
	
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
	private IDaoPersonne		daoPersonne;

	//-------
	// Actions 
	//-------

	@Override
	public int inserer( DtoPersonne dtoPersonne ) throws ExceptionValidation {
		try {
			
			managerSecurity.verifierAutorisationUtilisateurOuAdmin();
			verifierValiditeDonnees( dtoPersonne );
	
			managerTransaction.begin();
			int id = daoPersonne.inserer( mapper.map( dtoPersonne ) );
			managerTransaction.commit();
			return id;

		} catch ( Exception e ) {
	    	managerTransaction.rollbackSiApplicable();
			throw UtilServices.exceptionValidationOrAnomaly(e);
		}
	}

	@Override
	public void modifier( DtoPersonne dtoPersonne ) throws ExceptionValidation {
		try  {
		
			managerSecurity.verifierAutorisationUtilisateurOuAdmin();
			verifierValiditeDonnees( dtoPersonne );
	
			managerTransaction.begin();
			daoPersonne.modifier( mapper.map( dtoPersonne ) );
			managerTransaction.commit();

		} catch ( Exception e ) {
	    	managerTransaction.rollbackSiApplicable();
			throw UtilServices.exceptionValidationOrAnomaly(e);
		}
	}

	@Override
	public void supprimer( int idPersonne ) throws ExceptionValidation {
		try {
			
			managerSecurity.verifierAutorisationUtilisateurOuAdmin();

			managerTransaction.begin();
			daoPersonne.supprimer(idPersonne);
			managerTransaction.commit();

		} catch ( Exception e ) {
	    	managerTransaction.rollbackSiApplicable();
			throw UtilServices.exceptionValidationOrAnomaly(e);
		}
	}

	@Override
	public DtoPersonne retrouver( int idPersonne ) {
		try {
			
			managerSecurity.verifierAutorisationUtilisateurOuAdmin();
            Personne personne = daoPersonne.retrouver(idPersonne);
            return  mapper.map( personne );


		} catch ( Exception e ) {
			throw UtilServices.exceptionAnomaly(e);
		}
	}

	@Override
	public List<DtoPersonne> listerTout() {
		try {

			managerSecurity.verifierAutorisationUtilisateurOuAdmin();

			List<DtoPersonne> liste = new ArrayList<>();
			for ( Personne personne : daoPersonne.listerTout()) {
				liste.add( mapper.map(personne) );
			}
			return liste;

		} catch ( Exception e ) {
			throw UtilServices.exceptionAnomaly(e);
		}
	}
	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void verifierValiditeDonnees( DtoPersonne dtoPersonne ) throws ExceptionValidation {
		
		StringBuilder message = new StringBuilder();
		
		if ( dtoPersonne.getNom() == null || dtoPersonne.getNom().isEmpty() ) {
			message.append( "\nLe nom est absent." );
		} else 	if ( dtoPersonne.getNom().length() > 25 ) {
			message.append( "\nLe nom est trop long." );
		}

		if ( dtoPersonne.getPrenom() == null || dtoPersonne.getPrenom().isEmpty() ) {
			message.append( "\nLe prénom est absent." );
		} else 	if ( dtoPersonne.getPrenom().length() > 25 ) {
			message.append( "\nLe prénom est trop long." );
		}
		
		for( DtoTelephone telephoe : dtoPersonne.getTelephones() ) {
			if ( telephoe.getLibelle() == null || telephoe.getLibelle().isEmpty() ) {
				message.append( "\nLlibellé absent pour le téléphone : " + telephoe.getNumero() );
			} else 	if ( telephoe.getLibelle().length() > 25 ) {
				message.append( "\nLe libellé du téléphone est trop long : " + telephoe.getLibelle() );
			}
  			
			if ( telephoe.getNumero() == null || telephoe.getNumero().isEmpty() ) {
				message.append( "\nNuméro absent pour le téléphone : " + telephoe.getLibelle() );
			} else 	if ( telephoe.getNumero().length() > 25 ) {
				message.append( "\nLe numéro du téléphone est trop long : " + telephoe.getNumero() );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
	} 

}
