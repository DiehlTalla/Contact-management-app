package contacts.emb.dao.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


@Lazy
@ComponentScan(
		basePackages = {
				"contacts.emb.dao.jpa",
		},
		lazyInit = true	)
public class ConfigJpa {

	@Bean
	public EntityManagerFactory emf() {
		return Persistence.createEntityManagerFactory( "contacts" );
	}

	@Bean
	public EntityManager em( EntityManagerFactory emf ) {
		return  emf.createEntityManager();
	}
}
