package contacts.ejb.data.mapper;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.ejb.data.Categorie;
import contacts.ejb.data.Compte;
import contacts.ejb.data.Personne;
import contacts.ejb.data.Telephone;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-18T09:15:45+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@ApplicationScoped
public class IMapperImpl implements IMapper {

    @Override
    public Compte map(DtoCompte source) {
        if ( source == null ) {
            return null;
        }

        Compte compte = new Compte();

        compte.setId( source.getId() );
        compte.setPseudo( source.getPseudo() );
        compte.setMotDePasse( source.getMotDePasse() );
        compte.setEmail( source.getEmail() );
        List<String> list = source.getRoles();
        if ( list != null ) {
            compte.setRoles( new ArrayList<String>( list ) );
        }

        return compte;
    }

    @Override
    public DtoCompte map(Compte source) {
        if ( source == null ) {
            return null;
        }

        DtoCompte dtoCompte = new DtoCompte();

        dtoCompte.setEmail( source.getEmail() );
        dtoCompte.setId( source.getId() );
        dtoCompte.setMotDePasse( source.getMotDePasse() );
        dtoCompte.setPseudo( source.getPseudo() );
        List<String> list = source.getRoles();
        if ( list != null ) {
            dtoCompte.setRoles( new ArrayList<String>( list ) );
        }

        return dtoCompte;
    }

    @Override
    public Categorie map(DtoCategorie source) {
        if ( source == null ) {
            return null;
        }

        Categorie categorie = new Categorie();

        categorie.setId( source.getId() );
        categorie.setLibelle( source.getLibelle() );

        return categorie;
    }

    @Override
    public DtoCategorie map(Categorie source) {
        if ( source == null ) {
            return null;
        }

        DtoCategorie dtoCategorie = new DtoCategorie();

        dtoCategorie.setId( source.getId() );
        dtoCategorie.setLibelle( source.getLibelle() );

        return dtoCategorie;
    }

    @Override
    public Personne map(DtoPersonne source) {
        if ( source == null ) {
            return null;
        }

        Personne personne = new Personne();

        personne.setId( source.getId() );
        personne.setNom( source.getNom() );
        personne.setPrenom( source.getPrenom() );
        personne.setCategorie( map( source.getCategorie() ) );
        personne.setTelephones( dtoTelephoneListToTelephoneList( source.getTelephones() ) );

        addBackReference( personne );

        return personne;
    }

    @Override
    public DtoPersonne map(Personne source) {
        if ( source == null ) {
            return null;
        }

        DtoPersonne dtoPersonne = new DtoPersonne();

        dtoPersonne.setCategorie( map( source.getCategorie() ) );
        dtoPersonne.setId( source.getId() );
        dtoPersonne.setNom( source.getNom() );
        dtoPersonne.setPrenom( source.getPrenom() );
        dtoPersonne.setTelephones( telephoneListToDtoTelephoneList( source.getTelephones() ) );

        return dtoPersonne;
    }

    @Override
    public Telephone map(DtoTelephone source) {
        if ( source == null ) {
            return null;
        }

        Telephone telephone = new Telephone();

        telephone.setId( source.getId() );
        telephone.setLibelle( source.getLibelle() );
        telephone.setNumero( source.getNumero() );

        return telephone;
    }

    @Override
    public DtoTelephone map(Telephone source) {
        if ( source == null ) {
            return null;
        }

        DtoTelephone dtoTelephone = new DtoTelephone();

        dtoTelephone.setId( source.getId() );
        dtoTelephone.setLibelle( source.getLibelle() );
        dtoTelephone.setNumero( source.getNumero() );

        return dtoTelephone;
    }

    protected List<Telephone> dtoTelephoneListToTelephoneList(List<DtoTelephone> list) {
        if ( list == null ) {
            return null;
        }

        List<Telephone> list1 = new ArrayList<Telephone>( list.size() );
        for ( DtoTelephone dtoTelephone : list ) {
            list1.add( map( dtoTelephone ) );
        }

        return list1;
    }

    protected List<DtoTelephone> telephoneListToDtoTelephoneList(List<Telephone> list) {
        if ( list == null ) {
            return null;
        }

        List<DtoTelephone> list1 = new ArrayList<DtoTelephone>( list.size() );
        for ( Telephone telephone : list ) {
            list1.add( map( telephone ) );
        }

        return list1;
    }
}
