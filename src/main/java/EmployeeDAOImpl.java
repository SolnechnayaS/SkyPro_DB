import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EmployeeDAOImpl implements EmployeeDAO {
    final String user = "postgres";
    final String password = "password";
    final String url = "jdbc:postgresql://localhost:5432/skyprodbhomework1";

    @Override
    public List<Employee> getAllEmployees() throws SQLException {

        List<Employee> allEmployees = new ArrayList<>();
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee")) {
            System.out.println("Соединение установлено!");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt("id");
                String firstNameOfEmployee = resultSet.getString("first_name");
                String lastNameOfEmployee = resultSet.getString("last_name");
                String genderOfEmployee = resultSet.getString("gender");
                int ageOfEmployee = resultSet.getInt("age");
                int cityIdOfEmployee = resultSet.getInt("city_id");

                Employee tempEmployee = new Employee(firstNameOfEmployee, lastNameOfEmployee, genderOfEmployee, ageOfEmployee, cityIdOfEmployee);
                allEmployees.add(tempEmployee);
            }

        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

        return allEmployees;
    }

    @Override
    public void getEmployeeById(int idRequest) {

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id ="+idRequest)) {

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

    }

    @Override
    public void createEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ИМЯ нового сотрудника: ");
        String firstNameOfEmployee = scanner.useDelimiter("\n").next();
        System.out.println("Введите ФАМИЛИЮ нового сотрудника: ");
        String lastNameOfEmployee = scanner.useDelimiter("\n").next();
        System.out.println("Введите ПОЛ нового сотрудника: " +
                "1 - мужской\t" +
                "2 - женский");
        String genderOfEmployee = "male";
        int numGenderOfEmployee = scanner.useDelimiter("\n").nextInt();
        switch (numGenderOfEmployee) {
            case 1:
                genderOfEmployee = "male";
            case 2:
                genderOfEmployee = "female";
            default:
                break;
        }

        System.out.println("Введите ВОЗРАСТ нового сотрудника: ");
        int ageOfEmployee = scanner.useDelimiter("\n").nextInt();
        System.out.println("Введите id ГОРОДА нового сотрудника: ");
        int cityIdOfEmployee = scanner.useDelimiter("\n").nextInt();

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO employee(first_name, last_name, gender, age, city_id) VALUES ('" + firstNameOfEmployee + "', '" + lastNameOfEmployee + "', '" + genderOfEmployee + "', " + ageOfEmployee + ", " + cityIdOfEmployee + ")")) {
            statement.executeUpdate();
            System.out.println("Сотрудник добавлен");

        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

    }

    @Override
    public void updateEmployee(int idRequest) {
        Scanner scanner = new Scanner(System.in);
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id =" + idRequest)) {
            System.out.println("Соединение установлено!");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Выберите, какие данные будем изменять: " +
                    "1 - Имя; " +
                    "2 - Фамилия; " +
                    "3 - Возраст; " +
                    "4 - id Города проживания;");
            int numChange = scanner.useDelimiter("\n").nextInt();

            while (numChange != 0) {
                switch (numChange) {
                    case 1:
                        System.out.println("Введите новое ИМЯ: ");
                        String firstNameOfEmployee = scanner.useDelimiter("\n").next();
                        PreparedStatement statement1 =
                                connection.prepareStatement("UPDATE employee SET first_name ='" + firstNameOfEmployee + "'  WHERE id =" + idRequest);
                        statement1.executeUpdate();
                        numChange = 5;
                        break;
                    case 2:
                        System.out.println("Введите новую ФАМИЛИЮ: ");
                        String lastNameOfEmployee = scanner.useDelimiter("\n").next();
                        PreparedStatement statement2 =
                                connection.prepareStatement("UPDATE employee SET last_name ='" + lastNameOfEmployee + "' WHERE id =" + idRequest);
                        statement2.executeUpdate();
                        numChange = 5;
                        break;
                    case 3:
                        System.out.println("Уточните ВОЗРАСТ: ");
                        int ageOfEmployee = scanner.useDelimiter("\n").nextInt();
                        PreparedStatement statement3 =
                                connection.prepareStatement("UPDATE employee SET age =" + ageOfEmployee + " WHERE id =" + idRequest);
                        statement3.executeUpdate();
                        numChange = 5;
                        break;
                    case 4:
                        System.out.println("Введите новый ID города проживания: ");
                        int cityIdOfEmployee = scanner.useDelimiter("\n").nextInt();
                        PreparedStatement statement4 =
                                connection.prepareStatement("UPDATE employee SET city_id =" + cityIdOfEmployee + " WHERE id =" + idRequest);
                        statement4.executeUpdate();
                        numChange = 5;
                        break;
                    default:
                        System.out.println("Выберите, какие данные будем изменять: " +
                                "1 - Имя;" +
                                "2 - Фамилия;" +
                                "3 - Возраст;" +
                                "4 - id Города проживания;" +
                                "0 - Завершить редактирование.");
                        numChange = scanner.useDelimiter("\n").nextInt();
                }
            }

        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

    }

    @Override
    public void deleteEmployee(int idRequest) {

        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("DELETE FROM employee WHERE id="+idRequest)) {
            System.out.println("Сотрудник удален");
            statement.executeUpdate();

        } catch (
                SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

    }
}
