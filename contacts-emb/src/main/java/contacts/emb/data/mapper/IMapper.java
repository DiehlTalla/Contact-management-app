package contacts.emb.data.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.emb.data.Categorie;
import contacts.emb.data.Compte;
import contacts.emb.data.Personne;
import contacts.emb.data.Telephone;

@Mapper( componentModel = "spring")
public interface IMapper {  

	//-------
	// Compte
	
	Compte map( DtoCompte source );
	
	DtoCompte map( Compte source );
	
	//-------
	// Categorie
	
	Categorie map( DtoCategorie source );
	
	DtoCategorie map( Categorie source );
	
	//-------
	// Personne
	
	Personne map( DtoPersonne source );
	
	DtoPersonne map( Personne source );
	
	//-------
	// Telephone
	
	@Mapping( target="personne", ignore=true )
	Telephone map( DtoTelephone source );
	
	DtoTelephone map( Telephone source );
	
	//-------
	// Méthodes auxiliaires
	//-------
	
    @AfterMapping
    public default void addBackReference(@MappingTarget Personne target) {
        for (Telephone telephone : target.getTelephones() ) {
        	telephone.setPersonne( target );
        }
    }	
	
}
