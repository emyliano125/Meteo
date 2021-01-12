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


public class Controler {


    private ObservableList<Model> options = FXCollections.observableArrayList(); //##### Declararea unei ObservableList in care vom pune datele citite din fisierul de input #####



    //##### Declararea tuturor elementelor prezente in .fxml #####

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



    //##### Implementarea functiei care citeste datele din fisierul de input #####

    private void read_function() throws IOException {


        File file = new File("G:/ANUL 4/ingineria programarii/Meteo/src/main/resources/input");
        if (file.exists()) {

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            st = br.readLine();
            while ((st = br.readLine()) != null) {

                String[] file_data = st.split("\t");
                Model aux = new Model(file_data[4], file_data[1], file_data[0]);
                options.add(aux);   //##### Adaugare in ObservableList a celor 3 variabile care ne intereseaza din fisierul de input si anume ID, Nume_Oras, Cod_tara #####


            }


        }

    }


    @FXML
    private void initialize() throws IOException {

        read_function();//##### Apelul functiei de citire din fisierul de input #####
        Image image = new Image(new FileInputStream("src/main/resources/day-and-night.png"));
        image_view2.setImage(image);
        info_titulu.setText("Emi\nMeteo");
    }

    //##### Implementare functie pentru evenimente asupra ComboBoxului care contine ID-ul tarilor #####
    @FXML
    public void afisare_tari(Event event) {
        ArrayList<String> aux_country = new ArrayList<String>();

        boolean flag = true;
        for (int i = 0; i < options.size(); i++) {

            for (int j = 0; j < aux_country.size(); j++) {
                if (aux_country.get(j).equals(options.get(i).getID_country())) {    //##### Datorita faptului ca in fisierul de input, ID_ul tarilor adica (RO,RU etc..)apar de doua ori, acestea au trebuit retnute doar o singura data in ComboBox #####
                    flag = false;
                    break;                                                          //##### In cazul de fata nu se mai adauga o noua valoare in ComboBox deoarece respectiva valoare se afla deja in ComboBox #####
                }

            }
            if (flag == false) {
                flag = true;
            } else {
                aux_country.add(options.get(i).getID_country());    //##### In cazul de fata se adauga valoarea respectiva in ComboBox #####
            }
        }
    if(combo_box_country.getValue() == null)
    {
        combo_box_country.getItems().addAll(aux_country);   //##### Deoarece evenimentul este unul de tip onMouseClick este nevoie de aceasta instructiune pentru a nu se adauga valori in ComboBox dupa fiecare click #####
    }



    }

    //##### Implementare functie pentru evenimente asupra ComboBoxului care contine Numele Oraselor #####
    //##### Aceasta metoda completeaza ComboBoxul pentru orase in functie de ID_ul tarii la care sunt asignate fiecare #####
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

    //##### Implementare functie pentru evenimente asupra Buttonului Search #####
    @FXML
    public void search(MouseEvent mouseEvent) throws IOException, JSONException, ParseException {

        //##### Accesare fisier Json care contine informatiile necesare #####
        //##### Cautarea se face dupa Nume_Orasului si Id_ul tarilor #####

        URL url;
        url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + combo_box_city.getValue() + "," + combo_box_country.getValue() + ",&appid=e804d08047c940bce478d224e7c653be&lang=ro&units=metric");
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String down = IOUtils.toString(reader);

        //##### De aici se incepe PARSAREA fisierului si adaugare datelor dorite in label-urile specifice acestora #####
        Object object = new JSONParser().parse(down);
        JSONObject skr = (JSONObject) object;
        String location = (String) skr.get("name");//##### get() pentru numele localitate #####
        info_location.setText(location);           //##### setare label pentru numele localitatii cu datele potrivite #####


        JSONArray arr = (JSONArray) skr.get("weather");//##### Aici PARSAREA se face diferit deoarece structura arata in felul urmator weather [{"main:" etc}] #####
        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            String main_id = (String) obj.get("main");
            info_sky.setText(main_id);
            String icon = (String) obj.get("icon");//##### get() pentru iconita specifica vremii #####
            String url1="http://openweathermap.org/img/wn/"+icon+"@2x.png";
            Image img = new Image(url1, true);
            image.setImage(img);    //##### setare ImageView cu iconita potrivita #####
            image.setFitWidth(100);
            image.setFitHeight(100);



        }

        JSONObject arr2 = (JSONObject) skr.get("main");
        String pressure = arr2.get("pressure").toString();
        info_pressure.setText("Pressure: " + pressure);//##### setare label pentru presiune cu datele potrivite #####
        String humidity = arr2.get("humidity").toString();
        info_humidity.setText("Humidity: " + humidity + "%");//##### setare label pentru nivelul de umiditate cu datele potrivite #####

        JSONObject arr3 = (JSONObject) skr.get("wind");
        String wind_speed = arr3.get("speed").toString();
        info_wind.setText("Wind: " + wind_speed + " km/h");//##### setare label pentru viteza vantului cu datele potrivite #####

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        String timeStamp2 = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        info_data.setText("Date: "+ timeStamp);//##### setare label pentru data (dt) #####
        info_time.setText("Hour: "+ timeStamp2);//##### setare label pentru ora (dt) #####

        JSONObject arr4 = (JSONObject) skr.get("main");
        String grade = arr4.get("temp").toString();
        info_grade.setText(grade + " °C");//##### setare label pentru temperatura cu datele potrivite #####



    }

    //#### Umratoarele 3 metode sunt implementarile pentru cele 3 butoane MIN MAX NORMAL. #####
    //Este recomandat sa se verifice functionalitatea acestora pe mai multe localitatii deoarece cateodata temperatura este aceeasi pentru toate 3 valorile si astfel se poate crede ca nu sunt functionale #####

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


