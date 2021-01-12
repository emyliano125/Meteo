package ro.mta.selab.model;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ModelTest {
    //##### Efectuaurea testului unitar #####

    String Id_country;
    String City;
    String ID_city;

    private Model TestModel;

    @Before
    public void setUp() throws Exception {
        Id_country = "PL";  //##### Hardcodarea membrilor cu valorile de interes #####
        City = "Katowice";
        ID_city = "3096472";

        ArrayList<String> test = new ArrayList<String>();

        File file = new File("G:/ANUL 4/ingineria programarii/Meteo/src/main/resources/input");
        if (file.exists()) {//##### in interiorul if-ului se citeste fisierul de intrare #####

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            st = br.readLine();
            while ((st = br.readLine()) != null) {

                String[] file_data = st.split("\t");
                TestModel = new Model(file_data[4], file_data[1], file_data[0]);//##### Se pun datele citite din fisierul de input intr un obiect de tip Model pentru a putea fi testate #####

            }


        }
    }

        @Test
        public void getID_country () {
            assertEquals(TestModel.getID_country(), this.Id_country);

        }

        @Test
        public void getCity () {
            assertEquals(TestModel.getCity(), this.City);
        }

        @Test
        public void getID_city () {
            assertEquals(TestModel.getID_city(), this.ID_city);
        }

    }