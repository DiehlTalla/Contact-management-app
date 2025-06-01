package contacts.emb.dao.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import contacts.emb.dao.IDaoCompte;
import contacts.emb.data.Compte;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(ConfigJpa.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestDaoCompte {

	//-------
	// Champs
	//-------

	@Inject
	private EntityManager em;
	@Inject
	private IDaoCompte dao;

	private Compte 		itemRef1;
	private Compte		itemRef2;
	private long		itemCount;

	//-------
	// Initialisation
	//-------

	@BeforeEach
	public void beforeEach() {

		itemRef1 = new Compte( 2, "chef", "chef", "chef@3il.fr" );
		itemRef1.getRoles().add( "UTILISATEUR" );

		itemRef2 = new Compte( 0, "test", "xxxx", "test@3il.fr" );
		itemRef2.getRoles().add( "ADMINISTRATEUR" );

		itemCount = 3;

		UtilTest.initDb(em);
		em.clear();
	}

	//-------
	// Tests
	//-------

	@Test
	public void testListerTout() {
		var liste = dao.listerTout();
		// Nombre d'items dans la liste
		assertEquals( itemCount, liste.size() );
		// Identifiant du premier item de laliste
		assertEquals( itemRef1.getId(), liste.get(0).getId(), "Ordre de tri incorrect" );
	}

	@Test
	public void testRetrouver_OK() {
		// Récupère l'item dans la base
		var item = dao.retrouver( itemRef1.getId() );
		assertNotNull( item );
		// Vérifie les données de l'item
		assertCompare( itemRef1, item );
	}

	@Test
	public void testRetrouver_Null() {
		// Item non trouvé dans la base
		var item = dao.retrouver( -1 );
		assertNull( item );
	}

	@Test
	public void testInsererSupprimer() {
		var tx = em.getTransaction();
		try {
			tx.begin();
			// Insère un nouvel item
			itemRef2.setId( 0 );
			var id = dao.inserer( itemRef2 );
			// Identifiant > 0 affecté au nouvel item
			assertTrue( id > 0 );
			assertEquals( itemRef2.getId(), id );
			tx.commit();
		} catch (Throwable e) {
			if ( tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		}
		em.detach( itemRef2 );
		try {
			// Récupère l'item dans la base
			var item = dao.retrouver( itemRef2.getId() );
			assertNotNull( item );
			// Vérifie les données de l'item
			assertCompare( itemRef2, item );
			em.detach( item );
			// Supprime l'item de la base
			tx.begin();
			dao.supprimer( itemRef2.getId() );
			tx.commit();
		} catch (Throwable e) {
			if ( tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		}
		// L'item ne peut pas être retrouvé car il est supprimé
		assertNull( dao.retrouver( itemRef2.getId() ) );
	}

	@Test
	public void testModifier() {
		var tx = em.getTransaction();
		// Enregistrement dans la base
		try {
			tx.begin();
			itemRef2.setId( itemRef1.getId() );
			dao.modifier( itemRef2 );
			tx.commit();
		} catch (Throwable e) {
			if ( tx.isActive() ) {
				tx.rollback();
			}
			throw e;
		}
		em.detach( itemRef2 );
		// Récupère l'item dans la base
		var item = dao.retrouver( itemRef2.getId() );
		assertNotNull( item );
		// Vérifie les données de l'item
		assertCompare( itemRef2, item );
	}

	@Test
	public void testVerifierUniciteCompte_OK1() {
		// OK sanss changement du pseudo
		assertTrue(dao.verifierUnicitePseudo( itemRef1.getPseudo(), itemRef1.getId()));
	}

	@Test
	public void testVerifierUniciteCompte_OK2() {
		// OK avec changement du pseudo
		assertTrue(dao.verifierUnicitePseudo("xx", 3));
	}

	@Test
	public void testVerifierUniciteCompte_KO1() {
		// erreur dans un cas de modification
		assertFalse(dao.verifierUnicitePseudo("job", 1));
	}

	@Test
	public void testVerifierUniciteCompte_KO2() {
		// erreur dans un cas d'insertion
		assertFalse(dao.verifierUnicitePseudo("job", -1));
	}

	@Test
	public void testValiderAuthentification_OK() {
		// Authentification OK
		var item = dao.validerAuthentification( itemRef1.getPseudo(), itemRef1.getMotDePasse() );
		assertNotNull( item );
		// Vérifie les données de l'item
		assertCompare(itemRef1, item );
	}

	@Test
	public void testValiderAuthentification_KO1() {
		// Mot de passe invalide
		var item = dao.validerAuthentification( itemRef1.getPseudo(), itemRef1.getMotDePasse() + "x" );
		assertNull( item );
	}

	@Test
	public void testValiderAuthentification_KO2() {
		// Pseudo invalide
		var item = dao.validerAuthentification( itemRef1.getPseudo() + "x", itemRef1.getMotDePasse() );
		assertNull( item );
	}

	//-------
	// Méthode auxilaire
	//-------

	private void assertCompare( Compte item1, Compte item2 ) {
		assertEquals( item1.getId(), item2.getId() );
		assertEquals( item1.getPseudo(), item2.getPseudo() );
		assertEquals( item1.getEmail(), item2.getEmail() );
		assertEquals( item1.getRoles().size(), item2.getRoles().size() );
		for ( String role : item1.getRoles() ) {
			assertTrue( item2.getRoles().contains( role ) );
		}
	}

}
