package contacts;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;


@Lazy
@ComponentScan(
		basePackages = {
				"contacts.gui.data.mapper",
				"contacts.gui.view",
				"contacts.gui.model.standard",
				"contacts.emb.data.mapper",
				"contacts.emb.service.util",
				"contacts.emb.service.standard",
				"contacts.emb.dao.mock",
		},
		lazyInit = true	)
public class Config1DaoMock {
	
}
