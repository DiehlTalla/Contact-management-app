package contacts;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import jfox.javafx.util.UtilFX;
import jfox.jdbc.DataSourceSingleConnection;

@Lazy
@ComponentScan(
		basePackages = {
				"contacts.gui.data.mapper",
				"contacts.gui.view",
				"contacts.gui.model.standard",
				"contacts.emb.data.mapper",
				"contacts.emb.service.util",
				"contacts.emb.service.standard",
				"contacts.emb.dao.jdbc",
		},
		lazyInit = true	)
public class Config2DaoJdbc {
	
	
	@Bean
	public DataSource dataSource() {
	return new DataSourceSingleConnection( UtilFX.getInputStram(
	"classpath:/META-INF/jdbc.properties" ) );
	}
	
}
