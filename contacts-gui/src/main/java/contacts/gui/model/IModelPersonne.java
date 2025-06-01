package contacts.gui.model;

import contacts.gui.data.Categorie;
import contacts.gui.data.Personne;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import jfox.javafx.view.Mode;


public interface IModelPersonne {

	ObservableList<Personne> getList();

	BooleanProperty flagRefreshingListProperty();

	Personne getDraft();

	ObjectProperty<Personne> currentProperty();

	Personne getCurrent();

	void setCurrent(Personne item);

	Mode getMode();

	ObservableList<Categorie> getCategories();

	void refreshList();

	void initDraft(Mode mode);

	void saveDraft();

	void deleteCurrent();

}