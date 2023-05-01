import java.sql.SQLException;
import java.util.List;
public interface EmployeeDAO {

    List <Employee> getAllEmployees() throws SQLException;
    List<Employee> getAllEmployeesFromCity (String cityName) throws SQLException ;
    Employee getEmployeeById(int id);
    Employee createEmployee() throws SQLException;
    Employee createEmployeeWithoutCity();
    void updateEmployee(Employee employee);
    void deleteEmployee(Employee employee);
}
