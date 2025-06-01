package contacts;

import javax.naming.InitialContext;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceCompte;

public class TestEjb {

	public static void main(String[] args) throws Exception {
		InitialContext ic = new InitialContext();
		IServiceCompte serviceCompte = (IServiceCompte) ic.lookup("contacts-ear-1.0.0-SNAPSHOT/contacts-ejb-1.0.0-SNAPSHOT/ServiceCompte!contacts.commun.service.IServiceCompte");
		for (DtoCompte compte : serviceCompte.listerTout()) {
			System.out.println(compte.getMotDePasse());
		}
		ic.close();
	}

}
