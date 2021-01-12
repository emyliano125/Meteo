package ro.mta.selab.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelTestMockito {
    String Id_country;
    String City;
    String ID_city;

    private Model MockitoTestModel;
    @Before
    public void setUp() throws Exception {

        BufferedReader buffer = mock(BufferedReader.class);

        when(buffer.readLine()).thenReturn("PL").thenReturn("Katowice").thenReturn("3096472").thenReturn("PL").thenReturn("Katowice").thenReturn("3096472");

        Id_country = buffer.readLine();
        City = buffer.readLine();
        ID_city = buffer.readLine();

        ArrayList<String> test = new ArrayList<String>();

        File file = new File("G:/ANUL 4/ingineria programarii/Meteo/src/main/resources/input");
        if (file.exists()) {

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            st = br.readLine();
            while ((st = br.readLine()) != null) {

                String[] file_data = st.split("\t");
                MockitoTestModel = new Model(file_data[4], file_data[1], file_data[0]);

            }


        }
    }


    @Test
    public void getID_country () {
        assertEquals(MockitoTestModel.getID_country(), this.Id_country);

    }

    @Test
    public void getCity () {
        assertEquals(MockitoTestModel.getCity(), this.City);
    }

    @Test
    public void getID_city () {
        assertEquals(MockitoTestModel.getID_city(), this.ID_city);
    }
}