import java.sql.*;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        CityDAO cityDAO = new CityDAOImpl();

        employeeDAO.createEmployee();
        cityDAO.createCity();

        System.out.println(employeeDAO.getAllEmployees());
        System.out.println(cityDAO.getAllCity());

        System.out.println("Введите название города для просмотра сотрудников: ");
        String requestCity = scanner.useDelimiter("\n").next();
        System.out.println(employeeDAO.getAllEmployeesFromCity(requestCity));

        System.out.println("Введите название города, который хотите удалить: ");
        String requestCityDel = scanner.useDelimiter("\n").next();
        cityDAO.deleteCity(requestCityDel);

        System.out.println("Введите название города чью запись, хотите изменить: ");
        String requestCityChange = scanner.useDelimiter("\n").next();
        cityDAO.updateCity(cityDAO.findCityByName(requestCityChange));

        System.out.println("Введите id сотрудника, чью запись хотите изменить: ");
        int idRequestChange = scanner.useDelimiter("\n").nextInt();
        employeeDAO.updateEmployee(employeeDAO.getEmployeeById(idRequestChange));

        System.out.println("Введите id сотрудника, чью запись хотите удалить: ");
        int idRequestDel = scanner.useDelimiter("\n").nextInt();
        employeeDAO.deleteEmployee(employeeDAO.getEmployeeById(idRequestDel));

        System.out.println("Введите id сотрудника, чью запись хотите вывести в консоль: ");
        int idRequest = scanner.useDelimiter("\n").nextInt();
        System.out.println(employeeDAO.getEmployeeById(idRequest));

        System.out.println(employeeDAO.getAllEmployees());
        System.out.println(cityDAO.getAllCity());

    }

}