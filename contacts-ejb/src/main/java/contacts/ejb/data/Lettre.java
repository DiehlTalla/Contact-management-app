package contacts.ejb.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Lettre")

public class Lettre {
	@Id
	@Column(name = "IdLettre")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "titre")
	private String titre;

	@ManyToOne
	@JoinColumn(name = "IdCompte")
	private Compte compte;

	@ManyToMany
	@JoinTable(name = "abonner", joinColumns = @JoinColumn(name = "IdLettre"), inverseJoinColumns = @JoinColumn(name = "IdCategorie"))
	private List<Categorie> categories = new ArrayList<Categorie>();

	public Lettre() {
	}

	public Lettre(int id, String titre, Compte compte) {
		super();
		this.id = id;
		this.titre = titre;
		this.compte = compte;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lettre other = (Lettre) obj;
		return id == other.id;
	}

}
