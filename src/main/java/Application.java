import java.sql.*;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {


        EmployeeDAO employeeDAO = new EmployeeDAOImpl();


        final String user = "postgres";
        final String password = "password";
        final String url = "jdbc:postgresql://localhost:5432/skyprodbhomework1";

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee")) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt("id");
                System.out.println("ID сотрудника: " + idOfEmployee);

                String firstNameOfEmployee = resultSet.getString("first_name");
                String lastNameOfEmployee = resultSet.getString("last_name");
                String genderOfEmployee = resultSet.getString("gender");
                int ageOfEmployee = resultSet.getInt("age");
                int cityIdOfEmployee = resultSet.getInt("city_id");

                System.out.println("Имя: " + firstNameOfEmployee);
                System.out.println("Фамилия: " + lastNameOfEmployee);
                System.out.println("Пол: " + genderOfEmployee);
                System.out.println("Возраст: " + ageOfEmployee);
                System.out.println("Город (id): " + cityIdOfEmployee + "\n");

            }

        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        employeeDAO.createEmployee();

        System.out.println("Введите id сотрудника, чью запись хотите изменить: ");
        int idRequestChange = scanner.useDelimiter("\n").nextInt();
        employeeDAO.updateEmployee(idRequestChange);

        System.out.println("Введите id сотрудника, чью запись хотите удалить: ");
        int idRequestDel = scanner.useDelimiter("\n").nextInt();
        employeeDAO.deleteEmployee(idRequestDel);

        System.out.println("Введите id сотрудника, чью запись хотите вывести в консоль: ");
        int idRequest = scanner.useDelimiter("\n").nextInt();
        employeeDAO.getEmployeeById(idRequest);

    }


}