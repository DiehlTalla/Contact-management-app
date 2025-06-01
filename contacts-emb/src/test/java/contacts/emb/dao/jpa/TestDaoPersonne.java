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

import contacts.emb.dao.IDaoPersonne;
import contacts.emb.data.Categorie;
import contacts.emb.data.Personne;
import contacts.emb.data.Telephone;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ExtendWith( SpringExtension.class )
@SpringJUnitConfig( ConfigJpa.class )
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestDaoPersonne {

	//-------
	// Champs
	//-------

	@Inject
	private EntityManager	em;
	@Inject
	private IDaoPersonne	dao;

	private Personne 		itemRef1;
	private Personne		itemRef2;
	private long			itemCount;

	//-------
	// Initialisation
	//-------

	@BeforeEach
	public void beforeEach() {
		var categorie1 = new Categorie( 1, null);
		itemRef1 = new Personne( 3, "AMBLARD", "Emmanuel", categorie1 );
		var t1 = itemRef1.getTelephones();
		t1.add( new Telephone( 33, itemRef1, "Bureau", "05 55 33 33 33" ) );
		t1.add( new Telephone( 32, itemRef1, "Fax", "05 55 99 33 33" ) );
		t1.add( new Telephone( 31, itemRef1, "Portable", "06 33 33 33 33" ) );

		var categorie2 = new Categorie( 2, null);
		itemRef2 = new Personne( 3, "XXXXX", "Xxxxxx", categorie2 );
		var t2 = itemRef2.getTelephones();
		t2.add( new Telephone( 31, itemRef2, "aaaaa", "bbbb" ) );
		t2.add( new Telephone( 0, itemRef2, "xxxx", "yyyyy" ) );

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
			for ( Telephone t : itemRef2.getTelephones() ) {
				t.setId(0);
			}
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
	public void testCompterPourCategorie1() {
		// Comptage OK pour catégorie non vide
		var nb = dao.compterPourCategorie( 1 );
		assertEquals( 3, nb );
	}

	@Test
	public void testCompterPourCategorie2() {
		// Comptage OK pour catégorie vide
		var nb = dao.compterPourCategorie( 5 );
		assertEquals( 0, nb );
	}

	//-------
	// Méthode auxilaire
	//-------

	private void assertCompare( Personne item1, Personne item2 ) {
		assertEquals( item1.getId(), item2.getId() );
		assertEquals( item1.getCategorie(), item2.getCategorie() );
		assertEquals( item1.getNom(), item2.getNom() );
		assertEquals( item1.getPrenom(), item2.getPrenom() );
		assertEquals( item1.getTelephones().size(), item2.getTelephones().size() );
		for ( int i=0; i < item1.getTelephones().size(); ++i ) {
			assertEquals( item1.getTelephones().get(i).getNumero(), item2.getTelephones().get(i).getNumero() );
			assertEquals( item1.getTelephones().get(i).getLibelle(), item2.getTelephones().get(i).getLibelle() );
	 	}
	}

}
