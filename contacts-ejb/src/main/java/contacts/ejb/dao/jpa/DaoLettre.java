package contacts.ejb.dao.jpa;

import java.util.List;
import contacts.ejb.dao.IDaoLettre;
import contacts.ejb.data.Categorie;
import contacts.ejb.data.Lettre;
import contacts.ejb.data.Personne;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Local
@Stateless
public class DaoLettre implements IDaoLettre {
	@PersistenceContext
	private EntityManager em;

	@Override
	public int inserer(Lettre lettre) {
		em.persist(lettre);
		em.flush();
		return lettre.getId();
	}

	@Override
	public void modifier(Lettre lettre) {
		em.merge(lettre);

	}

	@Override
	public void supprimer(int idLettre) {
		em.remove(em.getReference(Lettre.class, idLettre));

	}

	@Override
	public Lettre retrouver(int idLettre) {
		return em.find(Lettre.class, idLettre);
	}

	@Override
	public List<Lettre> listerTout() {
		em.clear();

		var jpql = "SELECT l FROM Lettre  l ORDER BY l.titre";
		var query = em.createQuery(jpql, Lettre.class);
		return query.getResultList();
	}

}
