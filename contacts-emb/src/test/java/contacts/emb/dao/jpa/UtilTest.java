package contacts.emb.dao.jpa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;

import jakarta.persistence.EntityManager;

public class UtilTest {


	public static void initDb( EntityManager em, File file ) {
		try {
			em.clear();
			em.getTransaction().begin();
			var br = new BufferedReader(new FileReader(file));
			var sb = new StringBuilder();
			String st;
			while ((st = br.readLine()) != null) {
				if ( ! st.trim().startsWith( "--" ) ) {
					sb.append(st);
				}
				if ( st.trim().endsWith(";") ) {
					var query = em.createNativeQuery( sb.toString() );
					query.executeUpdate();
					sb.setLength(0);
				}
			}
			br.close();
			em.getTransaction().commit();
			em.getEntityManagerFactory().getCache().evictAll();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (Exception e) {
			if ( em.getTransaction().isActive() ) {
				em.getTransaction().rollback();
			}
			throw new RuntimeException( e );
		}

	}


	public static void initDb( EntityManager em, String path ) {
		var file = UtilTest.class.getResource( path ).getFile();
		initDb(em, file);
	}


	public static void initDb( EntityManager em ) {
		var file = new File( "../scripts-sql/3-data.sql");
		initDb(em, file);
	}


}
