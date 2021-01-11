package ro.mta.selab.contoler;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


import com.sun.javafx.collections.MappingChange;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import ro.mta.selab.model.Model;
import org.json.simple.JSONObject;


import java.util.Iterator;
import java.util.Map;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class Controler {

    public String file_name;
    public String city_name;


    //private ObservableList<Model> modelData;

    private ObservableList<Model> options = FXCollections.observableArrayList();


    @FXML
    private ComboBox combo_box_country;
    @FXML
    private ComboBox combo_box_city;
    @FXML
    private Label info_location;
    @FXML
    private Label info_data;
    @FXML
    private Label info_sky;
    @FXML
    private Label info_precipitation;
    @FXML
    private Label info_humidity;
    @FXML
    private Label info_wind;

    private void read_function() throws IOException {


        File file = new File("G:/ANUL 4/ingineria programarii/Meteo/src/main/resources/input");
        if (file.exists()) {

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            st = br.readLine();
            while ((st = br.readLine()) != null) {

                String[] file_data = st.split("\t");
                Model aux = new Model(file_data[4], file_data[1], file_data[0]);
                options.add(aux);


            }


        }

    }


    @FXML
    private void initialize() throws IOException {

        read_function();


    }

    @FXML
    public void afisare_tari(Event event) {
        ArrayList<String> aux_country = new ArrayList<String>();

        boolean flag = true;
        for (int i = 0; i < options.size(); i++) {

            for (int j = 0; j < aux_country.size(); j++) {
                if (aux_country.get(j).equals(options.get(i).getID_country())) {
                    flag = false;
                    break;
                }

            }
            if (flag == false) {
                flag = true;
            } else {
                aux_country.add(options.get(i).getID_country());
            }
        }
        combo_box_country.getItems().addAll(aux_country);

    }

    @FXML
    public void afisare_orase(Event event) throws IOException, ParseException {

        ObservableList<String> options2 = FXCollections.observableArrayList();
        if (this.combo_box_country.getValue() != null) {
            for (Model city_loc : options)
                if (this.combo_box_country.getValue().equals(city_loc.getID_country())) {
                    options2.add(city_loc.getCity());

                }
        }
        combo_box_city.getItems().clear();
        combo_box_city.getItems().addAll(options2);


    }


    public void search(MouseEvent mouseEvent) throws IOException, JSONException, ParseException {
        URL url;
        url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + combo_box_city.getValue() + "," + combo_box_country.getValue() + ",&appid=e804d08047c940bce478d224e7c653be&lang=ro&units=metric");
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String down = org.apache.commons.io.IOUtils.toString(reader);

        Object object = new JSONParser().parse(down);
        JSONObject skr = (JSONObject) object;
        String location = (String) skr.get("name");
        info_location.setText(location);
        JSONArray msg = (JSONArray) skr.get("weather");
        Iterator<String> iterator = msg.iterator();
       // while (iterator.hasNext()) {

       // }


    }
}


