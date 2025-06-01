package contacts.emb.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import contacts.emb.dao.IDaoCompte;
import contacts.emb.data.Compte;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@Component
public class DaoCompte implements IDaoCompte {
	@Inject
	private EntityManager em;

	// -------
	// Champs
	// -------

	// -------
	// Actions
	// -------

	@Override
	public int inserer(Compte compte) {
		em.persist(compte);
		em.flush();
		return compte.getId();
	}

	@Override
	public void modifier(Compte compte) {
		em.merge(compte);
	}

	@Override
	public void supprimer(int idCompte) {
		em.remove(em.getReference(Compte.class, idCompte));
	}

	@Override
	public Compte retrouver(int idCompte) {
		return em.find(Compte.class, idCompte);
	}

	@Override
	public List<Compte> listerTout() {
		em.clear();

		var jpql = "SELECT c FROM Compte  c ORDER BY c.pseudo";
		var query = em.createQuery(jpql, Compte.class);
		return query.getResultList();
	}

	@Override
	public Compte validerAuthentification(String pseudo, String motDePasse) {
		var jpql = "SELECT c FROM Compte c WHERE c.pseudo = :pseudo AND c.motDePasse = :motDePasse";
		var query = em.createQuery(jpql, Compte.class);
		query.setParameter("pseudo", pseudo);
		query.setParameter("motDePasse", motDePasse);

		try {

			return query.getSingleResult();
		} catch (NoResultException e) {

			return null;
		}
	}

	@Override
	public boolean verifierUnicitePseudo(String pseudo, int idCompte) {
		var jpql = "SELECT COUNT(c) FROM Compte c WHERE c.pseudo = :pseudo AND c.id <> :idCompte";
		var query = em.createQuery(jpql, Long.class);
		query.setParameter("pseudo", pseudo);
		query.setParameter("idCompte", idCompte);
		Long count = query.getSingleResult();
		return count == 0;
	}

}
