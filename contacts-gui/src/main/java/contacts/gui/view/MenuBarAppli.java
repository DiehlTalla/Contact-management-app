package contacts.gui.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import contacts.commun.dto.Roles;
import contacts.gui.model.IModelConnexion;
import contacts.gui.view.categorie.ViewCategorieCombo;
import contacts.gui.view.compte.ViewCompteCombo;
import contacts.gui.view.personne.ViewPersonneListe;
import contacts.gui.view.systeme.ViewConnexion;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Menu;
import jfox.javafx.control.MenuBarAbstract;
import jfox.javafx.view.IManagerGui;


@Component 
@Scope( "prototype")
public class MenuBarAppli extends MenuBarAbstract {

	//-------
	// Champs 
	//-------
	
	private final BooleanProperty flagConnexion	= new SimpleBooleanProperty();
	private final BooleanProperty flagRoleUtil	= new SimpleBooleanProperty();
	private final BooleanProperty flagRoleAdmin	= new SimpleBooleanProperty();
	
	@Inject
	private IManagerGui 	managerGui;
	@Inject
	private IModelConnexion	modelConnexion;	
	
	//-------
	// Initialisation
	//-------
	
	@PostConstruct
	public void init() {
		
		//-------
		// Variables de travail

		Menu menu;
		
		//-------
		// Manu Système
		
		menu = addMenu( "Système", null, null );

		addMenuItem( "Se déconnecter", menu, flagConnexion,
				e -> managerGui.showView( ViewConnexion.class ) );

		addMenuItem( "Quitter", menu, null, e -> managerGui.exit() );
		
		//-------
		// Manu Données
		
		menu = addMenu( "Donnees", null,  flagRoleUtil.or(flagRoleAdmin) );
		
		addMenuItem( "Personnes", menu, null,
				e -> managerGui.showView( ViewPersonneListe.class ) );
		
		addMenuItem( "Catégories", menu, flagRoleAdmin, 
				e -> managerGui.showView( ViewCategorieCombo.class ) );
		
		addMenuItem( "Comptes", menu, flagRoleAdmin, 
				e -> managerGui.showView( ViewCompteCombo.class ) );
		
		//-------
		// Gestion des droits d'accès
		
		final var compteActif = modelConnexion.compteActifProperty();
		flagConnexion.bind( compteActif.isNotNull() );
		flagRoleUtil.bind( Bindings.createBooleanBinding( () -> flagConnexion.get() && compteActif.get().isInRole(Roles.UTILISATEUR), compteActif ) );
		flagRoleAdmin.bind( Bindings.createBooleanBinding( () -> flagConnexion.get() && compteActif.get().isInRole(Roles.ADMINISTRATEUR), compteActif ) );
		
	}
}
