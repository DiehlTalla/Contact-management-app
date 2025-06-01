package contacts.emb.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import contacts.emb.dao.IDaoCategorie;
import contacts.emb.data.Categorie;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Component
public class DaoCategorie implements IDaoCategorie {

	// -------
	// Champs
	// -------

	@Inject
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
