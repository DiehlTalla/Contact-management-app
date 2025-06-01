package contacts.gui.model.standard;

import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.service.IServicePersonne;
import contacts.gui.data.Categorie;
import contacts.gui.data.Personne;
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
	// Autres champs
	//-------
	
	private Mode				mode;
	
	@Inject
	private IMapperGui			mapper;
    @Inject
	private IModelCategorie		modelCategorie;
    @Inject
	private IServicePersonne	servicePersonne;
	
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
		list.clear();
		for( DtoPersonne dto : servicePersonne.listerTout() ) {
			Personne personne = mapper.map( dto );
			list.add( personne );
		}
		flagRefreshingList.set(false);
	}

	
    @Override
	public void initDraft(Mode mode) {
		this.mode = mode;
		modelCategorie.refreshList();
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Personne() );
		} else {
			setCurrent( mapper.map( servicePersonne.retrouver( getCurrent().getId() ) ) );
			mapper.update( draft, getCurrent() );
		}
	}

	
    @Override
	public void saveDraft() {
		
		try {

			// Supprime les téléphones vides
			
			for( int i=draft.getTelephones().size()-1; i >= 0; --i ) {
				var t = draft.getTelephones().get(i);
				if ( t.getId() == null && t.getLibelle() == null && t.getNumero() == null ) {
					draft.getTelephones().remove(i);
				}
			}

			// Enregistre les données dans la base
	
			DtoPersonne dto = mapper.map( draft );
			
			if ( mode == Mode.NEW ) {
				// Insertion
				draft.setId( servicePersonne.inserer( dto ) );
				// Actualise le courant
				setCurrent( mapper.update( new Personne(), draft ) );
			} else {
				// modficiation
				servicePersonne.modifier( dto );
				// Actualise le courant
				mapper.update( getCurrent(), draft );
			}
		} catch ( Exception e) {
			throw UtilFX.runtimeException( e );
		}
	}
	

    @Override
	public void deleteCurrent() {
		try {
			// Effectue la suppression
			servicePersonne.supprimer( getCurrent().getId() );
			// Détermine le nouveau courant
			setCurrent( UtilFX.findNext( list, getCurrent() ) );
		} catch ( Exception e) {
			throw UtilFX.runtimeException( e );
		}
	}
	
}
