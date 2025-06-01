package contacts.ejb.dao;

import java.util.List;

import contacts.ejb.data.Lettre;

public interface IDaoLettre {

	int			inserer( Lettre Lettre );

	void 		modifier( Lettre Lettre );

	void 		supprimer( int idLettre );

	Lettre 	retrouver( int idLettre );

	List<Lettre> listerTout();

}
