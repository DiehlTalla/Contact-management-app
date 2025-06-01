package contacts.gui.data.mapper;

import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.gui.data.Categorie;
import contacts.gui.data.Compte;
import contacts.gui.data.Personne;
import contacts.gui.data.Telephone;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-17T20:03:46+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240524-2033, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class IMapperGuiImpl implements IMapperGui {

    @Autowired
    private FactoryObsservableList factoryObsservableList;

    @Override
    public Compte map(DtoCompte source) {
        if ( source == null ) {
            return null;
        }

        Compte compte = new Compte();

        compte.setEmail( source.getEmail() );
        compte.setId( source.getId() );
        compte.setMotDePasse( source.getMotDePasse() );
        compte.setPseudo( source.getPseudo() );
        if ( compte.getRoles() != null ) {
            ObservableList<String> observableList = stringListToStringObservableList( source.getRoles() );
            if ( observableList != null ) {
                compte.getRoles().addAll( observableList );
            }
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
        if ( source.getId() != null ) {
            dtoCompte.setId( source.getId() );
        }
        dtoCompte.setMotDePasse( source.getMotDePasse() );
        dtoCompte.setPseudo( source.getPseudo() );
        ObservableList<String> observableList = source.getRoles();
        if ( observableList != null ) {
            dtoCompte.setRoles( new ArrayList<String>( observableList ) );
        }

        return dtoCompte;
    }

    @Override
    public Compte update(Compte target, Compte source) {
        if ( source == null ) {
            return target;
        }

        target.setEmail( source.getEmail() );
        target.setId( source.getId() );
        target.setMotDePasse( source.getMotDePasse() );
        target.setPseudo( source.getPseudo() );
        if ( target.getRoles() != null ) {
            target.getRoles().clear();
            ObservableList<String> observableList = source.getRoles();
            if ( observableList != null ) {
                target.getRoles().addAll( observableList );
            }
        }

        return target;
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

        if ( source.getId() != null ) {
            dtoCategorie.setId( source.getId() );
        }
        dtoCategorie.setLibelle( source.getLibelle() );

        return dtoCategorie;
    }

    @Override
    public Categorie update(Categorie target, Categorie source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setLibelle( source.getLibelle() );

        return target;
    }

    @Override
    public Personne map(DtoPersonne source) {
        if ( source == null ) {
            return null;
        }

        Personne personne = new Personne();

        personne.setCategorie( map( source.getCategorie() ) );
        personne.setId( source.getId() );
        personne.setNom( source.getNom() );
        personne.setPrenom( source.getPrenom() );
        if ( personne.getTelephones() != null ) {
            ObservableList<Telephone> observableList = dtoTelephoneListToTelephoneObservableList( source.getTelephones() );
            if ( observableList != null ) {
                personne.getTelephones().addAll( observableList );
            }
        }

        return personne;
    }

    @Override
    public Personne map(Categorie categorie, DtoPersonne source) {
        if ( categorie == null && source == null ) {
            return null;
        }

        Personne personne = new Personne();

        if ( source != null ) {
            personne.setId( source.getId() );
            personne.setNom( source.getNom() );
            personne.setPrenom( source.getPrenom() );
            if ( personne.getTelephones() != null ) {
                ObservableList<Telephone> observableList = dtoTelephoneListToTelephoneObservableList( source.getTelephones() );
                if ( observableList != null ) {
                    personne.getTelephones().addAll( observableList );
                }
            }
        }
        personne.setCategorie( categorie );

        return personne;
    }

    @Override
    public DtoPersonne map(Personne source) {
        if ( source == null ) {
            return null;
        }

        DtoPersonne dtoPersonne = new DtoPersonne();

        dtoPersonne.setCategorie( map( source.getCategorie() ) );
        if ( source.getId() != null ) {
            dtoPersonne.setId( source.getId() );
        }
        dtoPersonne.setNom( source.getNom() );
        dtoPersonne.setPrenom( source.getPrenom() );
        dtoPersonne.setTelephones( telephoneObservableListToDtoTelephoneList( source.getTelephones() ) );

        return dtoPersonne;
    }

    @Override
    public Personne update(Personne target, Personne source) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setNom( source.getNom() );
        target.setPrenom( source.getPrenom() );
        if ( target.getTelephones() != null ) {
            target.getTelephones().clear();
            ObservableList<Telephone> observableList = duplicate( source.getTelephones() );
            if ( observableList != null ) {
                target.getTelephones().addAll( observableList );
            }
        }

        target.setCategorie( source.getCategorie() );

        return target;
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

        if ( source.getId() != null ) {
            dtoTelephone.setId( source.getId() );
        }
        dtoTelephone.setLibelle( source.getLibelle() );
        dtoTelephone.setNumero( source.getNumero() );

        return dtoTelephone;
    }

    @Override
    public Telephone duplicate(Telephone source) {
        if ( source == null ) {
            return null;
        }

        Telephone telephone = new Telephone();

        if ( source.getId() != null ) {
            telephone.setId( source.getId() );
        }
        telephone.setLibelle( source.getLibelle() );
        telephone.setNumero( source.getNumero() );

        return telephone;
    }

    @Override
    public ObservableList<Telephone> duplicate(ObservableList<Telephone> source) {
        if ( source == null ) {
            return null;
        }

        ObservableList<Telephone> observableList = factoryObsservableList.createObsListFXTelephone();
        for ( Telephone telephone : source ) {
            observableList.add( duplicate( telephone ) );
        }

        return observableList;
    }

    protected ObservableList<String> stringListToStringObservableList(List<String> list) {
        if ( list == null ) {
            return null;
        }

        ObservableList<String> observableList = factoryObsservableList.createObsListString();
        for ( String string : list ) {
            observableList.add( string );
        }

        return observableList;
    }

    protected ObservableList<Telephone> dtoTelephoneListToTelephoneObservableList(List<DtoTelephone> list) {
        if ( list == null ) {
            return null;
        }

        ObservableList<Telephone> observableList = factoryObsservableList.createObsListFXTelephone();
        for ( DtoTelephone dtoTelephone : list ) {
            observableList.add( map( dtoTelephone ) );
        }

        return observableList;
    }

    protected List<DtoTelephone> telephoneObservableListToDtoTelephoneList(ObservableList<Telephone> observableList) {
        if ( observableList == null ) {
            return null;
        }

        List<DtoTelephone> list = new ArrayList<DtoTelephone>( observableList.size() );
        for ( Telephone telephone : observableList ) {
            list.add( map( telephone ) );
        }

        return list;
    }
}
