package contacts.gui.view.systeme;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import contacts.gui.data.Compte;
import contacts.gui.model.IModelConnexion;
import contacts.gui.model.IModelInfo;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jfox.javafx.view.ControllerAbstract;
import jfox.javafx.view.IManagerGui;


@Component
@Scope( "prototype" )
public class ViewConnexion extends ControllerAbstract {

	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private TextField		fieldPseudo;
	@FXML
	private PasswordField	fieldMotDePasse;

	//-------
	// Autres champs
	//-------
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private IModelConnexion	modelConnexion;
	@Inject
	private IModelInfo		modelInfo;
	
	//-------
	// Initialisation du Controller
	//-------
	
	@FXML
	private void initialize() {
		
		// Data binding
		Compte courant = modelConnexion.getCourant();
		fieldPseudo.textProperty().bindBidirectional( courant.pseudoProperty() );
		fieldMotDePasse.textProperty().bindBidirectional( courant.motDePasseProperty() );

	}
	
	public void refresh() {
		// Ferem la session si elle est ouverte
		if ( modelConnexion.getCompteActif() != null ) {
			modelConnexion.fermerSessionUtilisateur();
		}
	}
	
	//-------
	// Actions
	//-------
	
	@FXML
	private void doConnexion() {
		managerGui.execTask( () -> {
			modelConnexion.ouvrirSessionUtilisateur();
			Platform.runLater( () -> {
         			modelInfo.titreProperty().setValue( "Bienvenue" );
        			modelInfo.messageProperty().setValue( "Connexion réussie" );
        			managerGui.showView(ViewInfo.class);
            }) ;
		} );
	}
	

}
