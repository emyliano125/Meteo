package ro.mta.selab.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clasa aceasta reprezinta modelul MVC Arhitecture al acestei aplicatii.
 * Aceasta clasa are rolul de a reprezenta o entitate din aplicație.
 *
 * @author Dumitru Emilian-Nicusor
 */




public class Model {
    /**
     * Membrii acestei clase vor fi cei care vor primii valorile noastre de interes.
     * Pentru afisarea informatiilor dorite, despre localitatea dorita, vor fi necesari
     * acesti membrii.
     * Cautarea se va face in functie de ei.
     *
     */


    StringProperty ID_country;
    StringProperty City;
    StringProperty ID_city;





    public String getID_country() {
        return ID_country.get();
    }

    public StringProperty ID_countryProperty() {
        return ID_country;
    }

    public void setID_country(String ID_country) {
        this.ID_country.set(ID_country);
    }

    public String getCity() {
        return City.get();
    }

    public StringProperty cityProperty() {
        return City;
    }

    public void setCity(String city) {
        this.City.set(city);
    }

    public String getID_city() {
        return ID_city.get();
    }

    public StringProperty ID_cityProperty() {
        return ID_city;

    }


    public void setID_city(String ID_city) {
        this.ID_city.set(ID_city);
    }

    /**
     * Crearea constructorului.
     * Constructorul ne va fi util in crearea obiectelor de tip Model.
     *
     */


    public Model(String ID_country, String City, String ID_city) {
        this.ID_country = new SimpleStringProperty(ID_country);
        this.City = new SimpleStringProperty(City);
        this.ID_city = new SimpleStringProperty(ID_city);
    }


}
