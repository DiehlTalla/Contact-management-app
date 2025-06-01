package contacts.gui.model.standard;


import org.springframework.stereotype.Component;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceCompte;
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
	// Données observables 
	//-------
	
	private final ObservableList<Compte> list = FXCollections.observableArrayList(); 
	
	private final BooleanProperty 		flagRefreshingList = new SimpleBooleanProperty();
	
	private final Compte				draft 		= new Compte();
	
	private final ObjectProperty<Compte> current 	= new SimpleObjectProperty<>();
	
	//-------
	// Autres champs
	//-------
	
	private Mode			mode;

    @Inject
	private IMapperGui		mapper;
    @Inject
	private IServiceCompte	serviceCompte;
	
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
		list.clear();
		for( DtoCompte dto : serviceCompte.listerTout() ) {
			var item = mapper.map( dto );
			list.add( item );
		}
		flagRefreshingList.set(false);
 	}

	
    @Override
	public void initDraft(Mode mode) {
		this.mode = mode;
		if( mode == Mode.NEW ) {
			mapper.update( draft, new Compte() );
		} else {
			setCurrent( mapper.map( serviceCompte.retrouver( getCurrent().getId() ) ) );
			mapper.update( draft, getCurrent() );
		}
	}
	
	
    @Override
	public void saveDraft() {
		
		try {
			
			// Enregistre les données dans la base
			
			DtoCompte dto = mapper.map( draft );
			
			if ( mode == Mode.NEW ) {
				// Insertion
				draft.setId( serviceCompte.inserer( dto ) );
				// Actualise le courant
				setCurrent( mapper.update( new Compte(), draft ) );
			} else {
				// modficiation
				serviceCompte.modifier( dto );
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
			serviceCompte.supprimer( getCurrent().getId() );
			// Détermine le nouveau courant
			setCurrent( UtilFX.findNext( list, getCurrent() ) );
		} catch ( Exception e) {
			throw UtilFX.runtimeException( e );
		}
	}

}
