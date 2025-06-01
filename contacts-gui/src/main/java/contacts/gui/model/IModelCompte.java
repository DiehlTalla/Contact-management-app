package contacts.gui.model;

import contacts.gui.data.Compte;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import jfox.javafx.view.Mode;


public interface IModelCompte {

	ObservableList<Compte> getList();

	BooleanProperty flagRefreshingListProperty();

	Compte getDraft();

	ObjectProperty<Compte> currentProperty();

	Compte getCurrent();

	void setCurrent(Compte item);

	Mode getMode();

	void refreshList();

	void initDraft(Mode mode);

	void saveDraft();

	void deleteCurrent();

}