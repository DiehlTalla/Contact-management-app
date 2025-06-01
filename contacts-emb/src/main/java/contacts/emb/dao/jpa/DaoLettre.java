package contacts.emb.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import contacts.emb.dao.IDaoLettre;
import contacts.emb.data.Categorie;
import contacts.emb.data.Lettre;
import contacts.emb.data.Personne;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Component
public class DaoLettre implements IDaoLettre {
	@Inject
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
