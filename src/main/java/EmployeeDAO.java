import java.sql.SQLException;
import java.util.List;
public interface EmployeeDAO {

    List <Employee> getAllEmployees() throws SQLException;
    void getEmployeeById(int id);
    void createEmployee();
    void updateEmployee(int idRequest);
    void deleteEmployee(int id);
}
