package contacts.ejb.dao.jpa;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import contacts.ejb.dao.IDaoPersonne;
import contacts.ejb.data.Personne;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Local
@Stateless
public class DaoPersonne implements IDaoPersonne {
	@PersistenceContext
	private EntityManager em;
	// -------
	// Champs
	// -------

	// -------
	// Actions
	// -------

	@Override
	public int inserer(Personne personne) {
		em.persist(personne);
		em.flush();
		return personne.getId();
	}

	@Override
	public void modifier(Personne personne) {
		em.merge(personne);
	}

	@Override
	public void supprimer(int idPersonne) {
		em.remove(em.getReference(Personne.class, idPersonne));
	}

	@Override
	public Personne retrouver(int idPersonne) {
		return em.find(Personne.class, idPersonne);
	}

	@Override
	public List<Personne> listerTout() {
		em.clear();

		var jpql = "SELECT p FROM Personne  p ORDER BY p.nom";
		var query = em.createQuery(jpql, Personne.class);
		return query.getResultList();
	}

	@Override
	public int compterPourCategorie(int idCategorie) {
		var jpql = "SELECT COUNT(p) FROM Personne p WHERE p.categorie.id = :idCategorie ";
		var query = em.createQuery(jpql, Long.class);
		query.setParameter("idCategorie", idCategorie);
		return query.getSingleResult().intValue();
	}

	@SuppressWarnings("unused")
	private List<Personne> trierParNom(List<Personne> liste) {
		Collections.sort(liste, (Comparator<Personne>) (item1, item2) -> {
			int resultat = item1.getNom().toUpperCase().compareTo(item2.getNom().toUpperCase());
			if (resultat != 0) {
				return resultat;
			} else {
				return (item1.getPrenom().toUpperCase().compareTo(item2.getPrenom().toUpperCase()));
			}
		});
		return liste;
	}

}
