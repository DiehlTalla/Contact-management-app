package contacts.gui.data;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Categorie  {

	//-------
	// Données observables
	//-------

	private final ObjectProperty<Integer>	id		= new SimpleObjectProperty<>();
	private final StringProperty			libelle	= new SimpleStringProperty();

	//-------
	// Constructeurs
	//-------

	public Categorie() {
	}

	public Categorie( final int id, final String libelle ) {
		setId(id);
		setLibelle(libelle);
	}

	//-------
	// Getters et Setters
	//-------

	public final ObjectProperty<Integer> idProperty() {
		return this.id;
	}

	public final Integer getId() {
		return this.idProperty().getValue();
	}

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}

	public final StringProperty libelleProperty() {
		return this.libelle;
	}

	public final String getLibelle() {
		return this.libelleProperty().get();
	}

	public final void setLibelle(final String libelle) {
		this.libelleProperty().set(libelle);
	}

	//-------
	// hashCode() & equals()
	//-------

	@Override
	public int hashCode() {
		return Objects.hash(id.get() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(id.get(), other.id.get() );
	}

}

