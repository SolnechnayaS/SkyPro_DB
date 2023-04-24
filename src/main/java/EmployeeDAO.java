import java.sql.SQLException;
import java.util.List;
public interface EmployeeDAO {

    List <Employee> getAllEmployees() throws SQLException;
    Employee getEmployeeById(int id);
    void createEmployee();
    void updateEmployee(Employee employee);
    void deleteEmployee(int idDel);
}
