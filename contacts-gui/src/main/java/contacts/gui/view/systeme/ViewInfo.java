package contacts.gui.view.systeme;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import contacts.gui.model.IModelInfo;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import jfox.javafx.view.ControllerAbstract;


@Component
@Scope( "prototype" )
public class ViewInfo extends ControllerAbstract {
	
	//-------
	// Composants de la vue
	//-------
	
	@FXML
	private Label		labelTitre;
	@FXML
	private Label		labelMessage;

	//-------
	// Autres champs
	//-------
	
	@Inject
	private IModelInfo	modelInfo;
	
	//-------
	// Initialisation
	//-------
	
	@FXML
	private void initialize() {
		
		// Data binding
		labelTitre.textProperty().bind( modelInfo.titreProperty() );
		labelMessage.textProperty().bind( modelInfo.messageProperty() );
		
	}

}
