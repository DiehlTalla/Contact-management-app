package contacts.emb.dao.jpa;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import contacts.emb.dao.IManagerTransaction;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Component
public class ManagerTransaction implements IManagerTransaction {
	@Inject
	EntityManager em;

	// -------
	// Logger
	private static final Logger logger = Logger.getLogger(ManagerTransaction.class.getName());

	// -------
	// Actions
	// -------

	@Override
	public void begin() {
		em.getTransaction().begin();
		logger.finer("Transaction BEGIN");

	}

	@Override
	public void commit() {
		em.getTransaction().commit();
		logger.finer("Transaction COMMIT");
	}

	@Override
	public void rollback() {
		em.getTransaction().rollback();
		logger.finer("Transaction ROLLBACK");
	}

	@Override
	public void rollbackSiApplicable() {
		
		rollback();
	}

}
