package contacts.gui.model.mock;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import contacts.gui.data.Categorie;
import contacts.gui.data.mapper.IMapperGui;
import contacts.gui.model.IModelCategorie;
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
public class ModelCategorie implements IModelCategorie  {
	
	//-------
	// Données observables 
	//-------
	
	private final ObservableList<Categorie> list 	= FXCollections.observableArrayList(); 
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final Categorie				draft 		= new Categorie();
	
	private final ObjectProperty<Categorie> 	current 	= new SimpleObjectProperty<>();
	
	//-------
	// Autres champs
	//-------
	
	private Mode			mode;

	@Inject
	private Donnees				donnees;
	@Inject
	private IMapperGui			mapper;
	
	//-------
	// Getters & Setters
	//-------
	
    @Override
	public ObservableList<Categorie> getList() {
		return list;
	}

    @Override
	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}
	
    @Override
	public Categorie getDraft() {
		return draft;
	}

    @Override
	public ObjectProperty<Categorie> currentProperty() {
		return current;
	}

    @Override
	public Categorie getCurrent() {
		return current.getValue();
	}

    @Override
	public void setCurrent(Categorie item) {
		current.setValue(item);
	}
	
    @Override
	public Mode getMode() {
		return mode;
	}

	//-------
	// Actions
	//-------
	
	@Override
	public void refreshList() {
		// flagRefreshingList vaut true pendant la durée  
		// du traitement de mise à jour de la liste
		flagRefreshingList.set(true);

		list.setAll( donnees.getMapCategories().values() );

		// Trie la liste
		FXCollections.sort( list,
	            (Comparator<Categorie>) ( item1, item2) -> {
	                return ( item1.getLibelle().toUpperCase().compareTo( item2.getLibelle().toUpperCase() ) );
			});

		flagRefreshingList.set(false);
 	}

	
	@Override
	public void initDraft(Mode mode) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Categorie() );
		} else {
			mapper.update( draft, getCurrent() );
		}
	}
	
	
	@Override
	public void saveDraft() {
		
		// Effectue la mise à jour
		
		if ( mode == Mode.NEW ) {
			draft.setId( donnees.getProchainIdCategorie() + 1 );
			setCurrent( mapper.update( new Categorie(), draft ) );
			donnees.getMapCategories().put( getCurrent().getId(), getCurrent() );
		} else {
			mapper.update( getCurrent(), draft );
		}
		
	}
	
	
	@Override
	public void deleteCurrent() {
		// Effectue la suppression
		donnees.getMapCategories().remove( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}
	
}
