package contacts.emb.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import contacts.emb.dao.IDaoLettre;
import contacts.emb.data.Categorie;
import contacts.emb.data.Compte;
import contacts.emb.data.Lettre;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(ConfigJpa.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestDaoLettre {
	// -------
	// Champs
	// -------

	@Inject
	private EntityManager em;

	@Inject
	private IDaoLettre dao;

	private Lettre itemRef1;
	private Lettre itemRef2;
	private long itemCount;

	// -------
	// Initialisation
	// -------

	@BeforeEach
	public void beforeEach() {
		itemRef1 = new Lettre(2, "Actus du mois", new Compte(3, null, null, null));
		itemRef2 = new Lettre(0, "Test", new Compte(2, null, null, null));
		itemRef1.getCategories().add( new Categorie( 1, null) );
		itemRef1.getCategories().add( new Categorie( 2, null) );
		itemRef2.getCategories().add( new Categorie( 2, null) );
		itemRef2.getCategories().add( new Categorie( 3, null) );

		itemCount = 2;
		UtilTest.initDb(em);
		em.clear();
	}

	// -------
	// Tests
	// -------

	@Test
	public void testListerTout() {
		var liste = dao.listerTout();
		// Nombre d'items dans la liste
		assertEquals(itemCount, liste.size());
		// Identifiant du premier item de laliste
		assertEquals(itemRef1.getId(), liste.get(0).getId(), "Ordre de tri incorrect");
	}

	@Test
	public void testRetrouver_OK() {
		// Récupère l'item dans la base
		var item = dao.retrouver(itemRef1.getId());
		assertNotNull(item);
		// Vérifie les données de l'item
		assertCompare(itemRef1, item);
	}

	@Test
	public void testRetrouver_Null() {
		// Item non trouvé dans la base
		var item = dao.retrouver(-1);
		assertNull(item);
	}

	@Test
	public void testInsererSupprimer() {
		var tx = em.getTransaction();
		try {
			tx.begin();
			// Insère un nouvel item
			itemRef2.setId(0);
			var id = dao.inserer(itemRef2);
			// Identifiant > 0 affecté au nouvel item
			assertTrue(id > 0);
			assertEquals(itemRef2.getId(), id);
			tx.commit();
		} catch (Throwable e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		em.detach(itemRef2);
		try {
			// Récupère l'item dans la base
			var item = dao.retrouver(itemRef2.getId());
			assertNotNull(item);
			// Vérifie les données de l'item
			assertCompare(itemRef2, item);
			em.detach(item);
			// Supprime l'item de la base
			tx.begin();
			dao.supprimer(itemRef2.getId());
			tx.commit();
		} catch (Throwable e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		// L'item ne peut pas être retrouvé car il est supprimé
		assertNull(dao.retrouver(itemRef2.getId()));
	}

	@Test
	public void testModifier() {
		var tx = em.getTransaction();
		// Enregistrement dans la base
		try {
			tx.begin();
			itemRef2.setId(itemRef1.getId());
			dao.modifier(itemRef2);
			tx.commit();
		} catch (Throwable e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		em.detach(itemRef2);
		// Récupère l'item dans la base
		var item = dao.retrouver(itemRef2.getId());
		assertNotNull(item);
		// Vérifie les données de l'item
		assertCompare(itemRef2, item);
	}

	// -------
	// Méthode auxilaire
	// -------

	private void assertCompare(Lettre item1, Lettre item2) {
		assertEquals(item1.getId(), item2.getId());
		assertEquals(item1.getTitre(), item2.getTitre());
		assertEquals(item1.getCompte(), item2.getCompte());
		assertEquals(item1.getCategories().size(), item2.getCategories().size());
		for (var c : item1.getCategories()) {
			assertTrue(item2.getCategories().contains(c));
		}

	}
}
