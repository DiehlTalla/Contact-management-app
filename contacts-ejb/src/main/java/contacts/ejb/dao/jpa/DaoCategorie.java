package contacts.ejb.dao.jpa;

import java.util.List;
import contacts.ejb.dao.IDaoCategorie;
import contacts.ejb.data.Categorie;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Local
@Stateless
public class DaoCategorie implements IDaoCategorie {

	// -------
	// Champs
	// -------

	@PersistenceContext
	private EntityManager em;
	// -------
	// Actions
	// -------

	public int inserer(Categorie categorie) {

		em.persist(categorie);
		em.flush();
		return categorie.getId();

	}

	public void modifier(Categorie categorie) {
		em.merge(categorie);

	}

	public void supprimer(int idCategorie) {
		em.remove(em.getReference(Categorie.class, idCategorie));

	}

	public Categorie retrouver(int idCategorie) {
		return em.find(Categorie.class, idCategorie);
	}

	@Override
	public List<Categorie> listerTout() {
		em.clear();

		var jpql = "SELECT c FROM Categorie  c ORDER BY c.libelle";
		var query = em.createQuery(jpql, Categorie.class);
		return query.getResultList();

	}

}
