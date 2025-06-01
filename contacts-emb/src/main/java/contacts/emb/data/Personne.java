package contacts.emb.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Personne")
public class Personne {

	// -------
	// Champs
	// -------

	@Id
	@Column(name = "IdPersonne")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	@ManyToOne
	@JoinColumn(name = "IdCategorie")
	private Categorie categorie;

	@OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Telephone> telephones = new ArrayList<>();

	// -------
	// Constructeurs
	// -------

	public Personne() {
	}

	public Personne(int id, String nom, String prenom, Categorie categorie) {
		setId(id);
		setNom(nom);
		setPrenom(prenom);
		setCategorie(categorie);
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	// -------
	// Actions
	// -------

	public void ajouterTelephone(Telephone telephone) {
		telephones.add(telephone);
	}

	public void retirerTelephone(Telephone telephone) {
		telephones.remove(telephone);
	}

	// -------
	// hashcode() + equals()
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
		Personne other = (Personne) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
