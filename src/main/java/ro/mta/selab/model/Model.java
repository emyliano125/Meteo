package ro.mta.selab.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

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

    public Model(String ID_country, String City, String ID_city) {
        this.ID_country = new SimpleStringProperty(ID_country);
        this.City = new SimpleStringProperty(City);
        this.ID_city = new SimpleStringProperty(ID_city);
    }




}
