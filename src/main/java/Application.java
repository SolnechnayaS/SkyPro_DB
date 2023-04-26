import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.*;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        Scanner scanner = new Scanner(System.in);

        employeeDAO.createEmployee();

        System.out.println(employeeDAO.getAllEmployees());

        System.out.println("Введите id сотрудника, чью запись хотите изменить: ");
        int idRequestChange = scanner.useDelimiter("\n").nextInt();
        employeeDAO.updateEmployee(employeeDAO.getEmployeeById(idRequestChange));

        System.out.println("Введите id сотрудника, чью запись хотите удалить: ");
        int idRequestDel = scanner.useDelimiter("\n").nextInt();
        employeeDAO.deleteEmployee(employeeDAO.getEmployeeById(idRequestDel));

        System.out.println("Введите id сотрудника, чью запись хотите вывести в консоль: ");
        int idRequest = scanner.useDelimiter("\n").nextInt();
        System.out.println(employeeDAO.getEmployeeById(idRequest));

    }

}