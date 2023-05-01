import java.sql.SQLException;
import java.util.List;

public interface CityDAO {

    City findCityByName (String cityName);
    List<City> getAllCity() throws SQLException;
    City getCityById(int id);
    void createCity();
    void updateCity(City city);
    void deleteCity(String city);
}
