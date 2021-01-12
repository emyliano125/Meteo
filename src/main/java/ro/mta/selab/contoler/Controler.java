package ro.mta.selab.contoler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ro.mta.selab.model.Model;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Clasa Controler
 * Acesta va controla elementele din interfata si va implementa functionalitatea propriu-zisa a aplicatiei.
 *
 *
 * @author Dumitru Emilian-Nicusor
 */


public class Controler {


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
    @FXML
    private ImageView image_view2;
    @FXML
    private Label info_titulu;
    @FXML
    private ImageView image_localitate;
    @FXML
    private ImageView image_ski;
    @FXML
    private ImageView image_data;
    @FXML
    private ImageView image_timp;
    @FXML
    private ImageView image_pressure;
    @FXML
    private ImageView image_humidity;
    @FXML
    private ImageView image_wind;


    /**
     * Crearea metodei de citire din fisierul de input.
     * Aceasta metoda are rolul de a citi elementele din fisierul de input
     * si totodata adauga valorile de interes intr-o ObservableList
     *
     */

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
        Image image = new Image(new FileInputStream("src/main/resources/photo/day-and-night.png"));
        image_view2.setImage(image);
        info_titulu.setText("Emi\nMeteo");
    }

    /**
     * Metoda afisare_tari() are rolul de a umple ComboBoxul_country
     * cu valorile corespunzatoare citite din fisierul de input
     *
     */


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

    /**
     * Metoda afisare_tari() are rolul de a umple ComboBoxul_city
     * cu valorile corespunzatoare citite din fisierul de input
     *
     */

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


    /**
     * Metoda search() are rolul de afisare a informatiilor dorite
     * Aceasta este metoda care ii spune buttonului cum sa se comporte in cazul evenimentului onMouseClick
     * Tot aici se face si o parte din Parsarea fisierului JSON
     *
     */

    @FXML
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





            Image image = new Image(new FileInputStream("src/main/resources/photo/buildings.png"));
            image_localitate.setImage(image);
            Image image1 = new Image(new FileInputStream("src/main/resources/photo/calendar(1).png"));
            image_data.setImage(image1);
            Image image2 = new Image(new FileInputStream("src/main/resources/photo/clock.png"));
            image_timp.setImage(image2);
            Image image3 = new Image(new FileInputStream("src/main/resources/photo/clouds-and-sun.png"));
            image_ski.setImage(image3);

            Image image4 = new Image(new FileInputStream("src/main/resources/photo/gauge.png"));
            image_pressure.setImage(image4);
            Image image5 = new Image(new FileInputStream("src/main/resources/photo/humidity.png"));
            image_humidity.setImage(image5);
            Image image6 = new Image(new FileInputStream("src/main/resources/photo/weather-vane.png"));
            image_wind.setImage(image6);




        }

        JSONObject arr2 = (JSONObject) skr.get("main");
        String pressure = arr2.get("pressure").toString();
        info_pressure.setText("Pressure: " + pressure + "hpa");
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

    /**
     * Metodele min(), max(), normal() au rolul de a afisa temperatura
     * MINIMA, MAXIMA, SI CURENTA a unei localitati dorite.
     *
     */

    @FXML
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

    @FXML
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

    @FXML
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


