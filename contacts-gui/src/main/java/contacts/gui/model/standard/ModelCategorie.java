package contacts.gui.model.standard;

import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.service.IServiceCategorie;
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
	
	private Mode				mode;

	@Inject
	private IMapperGui			mapper;
    @Inject
	private IServiceCategorie	serviceCategorie;
	
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
		list.clear();
		for( DtoCategorie dto : serviceCategorie.listerTout() ) {
			var item = mapper.map( dto );
			list.add( item );
		}
		flagRefreshingList.set(false);
 	}

    @Override
	public void initDraft(Mode mode) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Categorie() );
		} else {
			setCurrent( mapper.map( serviceCategorie.retrouver( getCurrent().getId() ) ) );
			mapper.update( draft, getCurrent() );
		}
	}
	
	
    @Override
	public void saveDraft() {
		
		try {

			// Enregistre les données dans la base
	
			DtoCategorie dto = mapper.map( draft );
			
			if ( mode == Mode.NEW ) {
				// Insertion
				draft.setId( serviceCategorie.inserer( dto ) );
				// Actualise le courant
				setCurrent( mapper.update( new Categorie(), draft ) );
			} else {
				// modficiation
				serviceCategorie.modifier( dto );
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
			serviceCategorie.supprimer( getCurrent().getId() );
			// Détermine le nouveau courant
			setCurrent( UtilFX.findNext( list, getCurrent() ) );
		} catch ( Exception e) {
			throw UtilFX.runtimeException( e );
		}
	}
	
}
