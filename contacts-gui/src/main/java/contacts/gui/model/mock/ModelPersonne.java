package contacts.gui.model.mock;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import contacts.gui.data.Categorie;
import contacts.gui.data.Personne;
import contacts.gui.data.Telephone;
import contacts.gui.data.mapper.IMapperGui;
import contacts.gui.model.IModelCategorie;
import contacts.gui.model.IModelPersonne;
import jakarta.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Mode;


@Component
public class ModelPersonne implements IModelPersonne {
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Personne> list = FXCollections.observableArrayList(); 
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final Personne				draft = new Personne();
	
	private final ObjectProperty<Personne> 	current 	= new SimpleObjectProperty<>();
	
	//-------
	// Objet sélectionné
	//-------
	
	private Mode				mode;
	
	//-------
	// Autres champs
	//-------
	
    @Inject
	private Donnees				donnees;
    @Inject
	private IMapperGui			mapper;
    @Inject
	private IModelCategorie		modelCategorie;
	
	//-------
	// Getters & Setters
	//-------
	
	@Override
	public ObservableList<Personne> getList() {
		return list;
	}

    @Override
	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}
	
	@Override
	public Personne getDraft() {
		return draft;
	}

    @Override
	public ObjectProperty<Personne> currentProperty() {
		return current;
	}

    @Override
	public Personne getCurrent() {
		return current.getValue();
	}

    @Override
	public void setCurrent(Personne item) {
		current.setValue(item);
	}
	
    @Override
	public Mode getMode() {
		return mode;
	}
	
    @Override
	public ObservableList<Categorie> getCategories() {
		return modelCategorie.getList();
	}
	
	//-------
	// Actions
	//-------
	
	@Override
	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée  
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);

		list.setAll( donnees.getMapPersonnes().values() );
		
		// Trie la liste
		FXCollections.sort( list,
	            (Comparator<Personne>) ( item1, item2) -> {
	                int resultat = item1.getNom().toUpperCase().compareTo(item2.getNom().toUpperCase());
	                return (resultat != 0 ? resultat : item1.getPrenom().toUpperCase().compareTo(item2.getPrenom().toUpperCase()));
			});

		flagRefreshingList.set(false);
	}

	
	@Override
	public void initDraft(Mode mode) {
		this.mode = mode;
		modelCategorie.refreshList();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Personne() );
		} else {
			mapper.update( draft, getCurrent() );
		}
	}

	
	@Override
	public void saveDraft() {
		
		String nom = draft.nomProperty().get();
		String prenom = draft.prenomProperty().get();
		
		StringBuilder message = new StringBuilder();
		if( nom == null || nom.isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( nom.length()> 25 ) {
			message.append( "\nLe nom est trop long." );
		}
		if( prenom == null || prenom.isEmpty() ) {
			message.append( "\nLe prénom ne doit pas être vide." );
		} else if ( prenom.length()> 25 ) {
			message.append( "\nLe prénom est trop long." );
		}

		// Supprime les téléphones vides
		for( int i=draft.getTelephones().size()-1; i >= 0; --i ) {
			var t = draft.getTelephones().get(i);
			if ( t.getId() == null && t.getLibelle() == null && t.getNumero() == null ) {
				draft.getTelephones().remove(i);
			}
		}
		for( var telephoe : draft.getTelephones() ) {
			if ( telephoe.getLibelle() == null || telephoe.getLibelle().isEmpty() ) {
				message.append( "\nLlibellé absent pour le téléphone : " + telephoe.getId() );
			} else 	if ( telephoe.getLibelle().length() > 25 ) {
				message.append( "\nLe libellé du téléphone est trop long : " + telephoe.getLibelle() );
			}
  			
			if ( telephoe.getNumero() == null || telephoe.getNumero().isEmpty() ) {
				message.append( "\nNuméro absent pour le téléphone : " + telephoe.getId() );
			} else 	if ( telephoe.getNumero().length() > 25 ) {
				message.append( "\nLe numéro du téléphone est trop long : " + telephoe.getNumero() );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		// Affecte les identifiants de téléphones
		for( Telephone t : draft.getTelephones() ) {
			if ( t.getId() == 0 ) {
				t.setId( donnees.getProchainIdTelephone() );
			}
		}

		// Effectue la mise à jour
		
		if ( mode == Mode.NEW ) {
			draft.setId( donnees.getProchainIdPersonne() + 1 );
			setCurrent( mapper.update( new Personne(), draft ) );
			donnees.getMapPersonnes().put( getCurrent().getId(), getCurrent() );
		} else {
			mapper.update( getCurrent(), draft );
		}
		
	}
	
	@Override
	public void deleteCurrent()  {
		// Effectue la suppression
		donnees.getMapPersonnes().remove( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}
}
