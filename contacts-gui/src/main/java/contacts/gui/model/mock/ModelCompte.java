package contacts.gui.model.mock;


import org.springframework.stereotype.Component;

import contacts.gui.data.Compte;
import contacts.gui.data.mapper.IMapperGui;
import contacts.gui.model.IModelCompte;
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
public class ModelCompte implements IModelCompte {
	
	//-------
	// Données pour les vues 
	//-------
	
	private final ObservableList<Compte> list = FXCollections.observableArrayList();
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final Compte				draft			= new Compte();
	
	private final ObjectProperty<Compte>	current 	= new SimpleObjectProperty<>();
	
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
	public ObservableList<Compte> getList() {
		return list;
	}

    @Override
	public BooleanProperty flagRefreshingListProperty() {
		return flagRefreshingList;
	}

	@Override
	public Compte getDraft() {
		return draft;
	}

    @Override
	public ObjectProperty<Compte> currentProperty() {
		return current;
	}

    @Override
	public Compte getCurrent() {
		return current.getValue();
	}

    @Override
	public void setCurrent(Compte item) {
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
		list.setAll( donnees.getMapComptes().values() );
		FXCollections.sort( list, ( item1, item2) -> {
             return ( item1.pseudoProperty().get().toUpperCase().compareTo(item2.pseudoProperty().get().toUpperCase()));
		});
		flagRefreshingList.set(false);
 	}

	
	@Override
	public void initDraft( Mode mode ) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Compte() );
		} else {
			mapper.update( draft, getCurrent() );
		}
	}
	
	
	@Override
	public void saveDraft()   {
		
		// Effectue la mise à jour
		
		if ( mode == Mode.NEW ) {
			draft.setId( donnees.getProchainIdCategorie() + 1 );
			setCurrent( mapper.update( new Compte(), draft ) );
			donnees.getMapComptes().put( getCurrent().getId(), getCurrent() );
		} else {
			mapper.update( getCurrent(), draft );
		}
	}
	
	
	@Override
	public void deleteCurrent()   {
		// Effectue la suppression
		donnees.getMapComptes().remove( getCurrent().getId() );
		// Détermine le nouveau courant
		setCurrent( UtilFX.findNext( list, getCurrent() ) );
	}
    
}
