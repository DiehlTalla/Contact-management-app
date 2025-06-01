package contacts.gui.model;

import contacts.gui.data.Categorie;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import jfox.javafx.view.Mode;


public interface IModelCategorie {

	ObservableList<Categorie> getList();

	BooleanProperty flagRefreshingListProperty();

	Categorie getDraft();

	ObjectProperty<Categorie> currentProperty();

	Categorie getCurrent();

	void setCurrent(Categorie item);

	Mode getMode();

	void refreshList();

	void initDraft(Mode mode);

	void saveDraft();

	void deleteCurrent();

}