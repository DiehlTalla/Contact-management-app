package contacts;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;


public class AppliContacts2Ejb extends Application {
	
	//-------
	// Champs
	//-------

	private final String TITRE = "Contacts EJB";
	
	private static final Logger logger = getLogger();
	
	private AnnotationConfigApplicationContext	context;
	
	//-------
	// Actions
	//-------
	
	@Override
	public final void start(Stage stage) {
		
		try {
			
			// Context
			context = new AnnotationConfigApplicationContext();
			//context.register( Config3DaoJpa.class );
			context.refresh();

			// ManagerGui
	    	var managerGui = context.getBean( IManagerGui.class );
	    	managerGui.setFactoryController( context::getBean );
			managerGui.setStage( stage );
			managerGui.configureStage();

	    	
	    	
			
			// Affiche le stage
			stage.setTitle( TITRE );
			stage.show();
			
		} catch(Exception e) {
			UtilFX.unwrapException(e).printStackTrace();
			logger.log( Level.SEVERE, "Echec du démarrage", e );
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setHeaderText( "Impossible de démarrer l'application." );
	        alert.showAndWait();
	        Platform.exit();
		}

	}
	
	@Override
	public final void stop() throws Exception {
		
		// Message de fermeture
		logger.config( "\n    Fermeture de l'application" );

		if (context != null ) {
			context.close();
		}
	}
	

	//-------
	// Méthodes auxiliaires
	//-------
	
	private static Logger getLogger() {

		try {
			InputStream in = 
				Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/logging.properties");
			LogManager.getLogManager().readConfiguration( in );
			in.close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}		

		return Logger.getLogger( AppliContacts2Ejb.class.getName() );
	}
	
	//-------
	// main()
	//-------
	
	public static void main(String[] args) {
		launch( args);
	}

}
