package contacts.emb.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Categorie")
public class Categorie {
    
	//-------
    // Champs
	//-------
    @Id
    @Column(name="IdCategorie")
    @GeneratedValue( strategy=GenerationType.IDENTITY)
    private int         	id;
    
    @Column(name="libelle")
    private String      	libelle;

	//-------
	// Constructeurs
	//-------

	public Categorie() {
		
	}
    
    public Categorie(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
    
	//-------
    // Getters & setters
	//-------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    
	//-------
    // soString()
	//-------

    @Override
    public String toString() {
    	return libelle;
    }

	//-------
	// hashcode() & equals()
	//-------

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
		Categorie other = (Categorie) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
}
