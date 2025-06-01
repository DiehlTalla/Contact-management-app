package contacts.gui.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.gui.data.Categorie;
import contacts.gui.data.Compte;
import contacts.gui.data.Personne;
import contacts.gui.data.Telephone;
import jakarta.inject.Named;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
   

@Mapper( uses=IMapperGui.FactoryObsservableList.class, componentModel = "spring"  )
public interface IMapperGui { 
	
	
	// Compte
	
	Compte map( DtoCompte source );
	
	DtoCompte map( Compte source );
	
	Compte update( @MappingTarget Compte target,  Compte source );
	
	
	// Categorie
	
	Categorie map( DtoCategorie source );
	
	DtoCategorie map( Categorie source );
	
	Categorie update( @MappingTarget Categorie target, Categorie source );
	
	
	// Personne
	
    Personne map( DtoPersonne source );
	
	@Mapping( source="categorie", target="categorie" )
	@Mapping( source="source.id", target="id" )
	Personne map( Categorie categorie, DtoPersonne source );
	
	DtoPersonne map( Personne source );
	
	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Personne update( @MappingTarget Personne target, Personne source );
	
	
	// Telephone
	
    Telephone map( DtoTelephone source );
	
    DtoTelephone map( Telephone source );

    // Méthodes nécessaire pour update( FXPersonne )
    Telephone duplicate( Telephone source );
    ObservableList<Telephone> duplicate( ObservableList<Telephone> source );
    
	
	
    // Classe auxiliaire

    @Component
    @Named
    public static class FactoryObsservableList {

    	ObservableList<Telephone> createObsListFXTelephone() {
    		return FXCollections.observableArrayList();
    	}

    	ObservableList<String> createObsListString() {
    		return FXCollections.observableArrayList();
    	}

    }
    
}
