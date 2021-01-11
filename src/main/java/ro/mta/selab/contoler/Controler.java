package ro.mta.selab.contoler;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.util.Calendar;
import java.text.SimpleDateFormat;

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
    private Label info_doi;
    @FXML
    private Label info_pressure;
    @FXML
    private Label info_humidity;
    @FXML
    private Label info_wind;
    @FXML
    private Label info_time;
    @FXML
    private ImageView image;
    @FXML
    private Label info_grade;




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
    if(combo_box_country.getValue() == null)
    {
        combo_box_country.getItems().addAll(aux_country);
    }



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
        String down = IOUtils.toString(reader);

        Object object = new JSONParser().parse(down);
        JSONObject skr = (JSONObject) object;
        String location = (String) skr.get("name");
        info_location.setText(location);


        JSONArray arr = (JSONArray) skr.get("weather");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            String main_id = (String) obj.get("main");
            info_sky.setText(main_id);
            String icon = (String) obj.get("icon");
            String url1="http://openweathermap.org/img/wn/"+icon+"@2x.png";
            Image img = new Image(url1, true);
            image.setImage(img);
            image.setFitWidth(100);
            image.setFitHeight(100);



        }

        JSONObject arr2 = (JSONObject) skr.get("main");
        String pressure = arr2.get("pressure").toString();
        info_pressure.setText("Pressure: " + pressure);
        String humidity = arr2.get("humidity").toString();
        info_humidity.setText("Humidity: " + humidity + "%");

        JSONObject arr3 = (JSONObject) skr.get("wind");
        String wind_speed = arr3.get("speed").toString();
        info_wind.setText("Wind: " + wind_speed + " km/h");

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        String timeStamp2 = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        info_data.setText("Date: "+ timeStamp);
        info_time.setText("Hour: "+ timeStamp2);

        JSONObject arr4 = (JSONObject) skr.get("main");
        String grade = arr4.get("temp").toString();
        info_grade.setText(grade + " °C");



    }

    public void min(MouseEvent mouseEvent) throws IOException, ParseException {

        URL url;
        url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + combo_box_city.getValue() + "," + combo_box_country.getValue() + ",&appid=e804d08047c940bce478d224e7c653be&lang=ro&units=metric");
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String down = IOUtils.toString(reader);

        Object object = new JSONParser().parse(down);
        JSONObject skr = (JSONObject) object;

        JSONObject min_max = (JSONObject) skr.get("main");
        String grade = min_max.get("temp_min").toString();
        info_grade.setText(grade + " °C");

    }

    public void max(MouseEvent mouseEvent) throws IOException, ParseException {
        URL url;
        url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + combo_box_city.getValue() + "," + combo_box_country.getValue() + ",&appid=e804d08047c940bce478d224e7c653be&lang=ro&units=metric");
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String down = IOUtils.toString(reader);

        Object object = new JSONParser().parse(down);
        JSONObject skr = (JSONObject) object;

        JSONObject min_max = (JSONObject) skr.get("main");

        String grade2 = min_max.get("temp_max").toString();
        info_grade.setText(grade2 + " °C");
    }

    public void now(MouseEvent mouseEvent) throws IOException, ParseException {
        URL url;
        url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + combo_box_city.getValue() + "," + combo_box_country.getValue() + ",&appid=e804d08047c940bce478d224e7c653be&lang=ro&units=metric");
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String down = IOUtils.toString(reader);

        Object object = new JSONParser().parse(down);
        JSONObject skr = (JSONObject) object;

        JSONObject now = (JSONObject) skr.get("main");
        String grade = now.get("temp").toString();
        info_grade.setText(grade + " °C");
    }
}


