package contacts.gui.view.categorie;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import contacts.gui.data.Categorie;
import contacts.gui.model.IModelCategorie;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.util.converter.ConverterInteger;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.IManagerGui;
import jfox.javafx.view.Mode;


@Component 
@Scope( "prototype")
public class ViewCategorieCombo extends ControllerAbstract {

	//-------
	// Composants de la vue
	//-------

	@FXML
	private ListView<Categorie>	lsvCategories;
	@FXML
	private Button				btnSupprimer;

	@FXML
	private Label			labId;
	@FXML
	private TextField		txfLibelle;
	@FXML
	private Button			btnValider;

	//-------
	// Autres champs
	//-------

	@Inject
	private IManagerGui		managerGui;
	@Inject
	private IModelCategorie	modelCategorie;

	//-------
	// Initialisation du Controller
	//-------

	@FXML
	private void initialize() {
		
		//-------
		// Partie liste
		//-------

		// ListView
		lsvCategories.setItems( modelCategorie.getList() );
		UtilFX.setCellFactory( lsvCategories, "libelle" );
		bindBidirectional( lsvCategories, modelCategorie.currentProperty(), modelCategorie.flagRefreshingListProperty() );
		
		// Configuraiton des boutons
		lsvCategories.getSelectionModel().selectedItemProperty().addListener(obs -> {
			initDraft();
			configurerBoutons();
		});
		initDraft();
		configurerBoutons();
		
		//-------
		// Partie formulaire
		//-------
		
		var draft = modelCategorie.getDraft();

		// Id
		bind( labId, draft.idProperty(), new ConverterInteger() );

		// Libellé
		bindBidirectional( txfLibelle, draft.libelleProperty()  );
		validator.addRuleNotBlank(txfLibelle);
		validator.addRuleMaxLength(txfLibelle, 25 );

		// Bouton VAlider
		btnValider.disableProperty().bind( validator.invalidProperty() );
	}


	@Override
	public void refresh() {
		modelCategorie.refreshList();
		txfLibelle.requestFocus();
	}

	//-------
	// Actions
	//-------
	
	@FXML
	private void doAjouter() {
		modelCategorie.initDraft( Mode.NEW );
		txfLibelle.requestFocus();
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelCategorie.deleteCurrent();
			refresh();
		}
	}

	@FXML
	private void doAnnuler() {
		if( modelCategorie.getCurrent() == null ) {
			initDraft();
		}
		refresh();
	}

	@FXML
	private void doValider() {
		modelCategorie.saveDraft();
		refresh();
	}
	
	//-------
	// Méthodes auxiliaires
	//-------
	
	private void initDraft() {
		if ( lsvCategories.getSelectionModel().getSelectedItem() == null ) {
			modelCategorie.initDraft( Mode.NEW );
		} else {
			modelCategorie.initDraft( Mode.EDIT );
		}
	}
	
	private void configurerBoutons() {
		var flagDisable = lsvCategories.getSelectionModel().getSelectedItem() == null;
		btnSupprimer.setDisable( flagDisable );
	}

}
