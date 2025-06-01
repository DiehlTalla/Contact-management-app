package contacts.emb.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Compte")
public class Compte {

	// -------
	// Champs
	// -------
	@Id
	@Column(name = "IdCompte")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "pseudo")
	private String pseudo;
	@Column(name = "motDePasse")
	private String motDePasse;

	@Column(name = "email")
	private String email;
	@ElementCollection
	@CollectionTable(name = "role", joinColumns = @JoinColumn(name = "IdCompte"))
	@Column(name = "Role")
	private List<String> roles = new ArrayList<>();

	// -------
	// Constructeurs
	// -------

	public Compte() {
	}

	public Compte(int id, String pseudo, String motDePasse, String email) {
		this.id = id;
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
		this.email = email;
	}

	// -------
	// Getters & setters
	// -------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	// -------
	// equals() et hashcode()
	// -------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compte other = (Compte) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
