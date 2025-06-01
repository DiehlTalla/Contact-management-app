package contacts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Lazy(false)
@ComponentScan(
		basePackages = {
				"contacts.gui.data.mapper",
				"contacts.gui.view",
				"contacts.gui.model.standard",
				"contacts.emb.data.mapper",
				"contacts.emb.service.util",
				"contacts.emb.service.standard",
				"contacts.emb.dao.jpa",
		},
		lazyInit = true	)
public class Config3DaoJpa {
	
	
	@Bean
	public EntityManagerFactory emf() {
	return Persistence.createEntityManagerFactory( "contacts" );
	}
	@Bean
	public EntityManager em( EntityManagerFactory emf ) {
	return emf.createEntityManager();
	}

	
}
