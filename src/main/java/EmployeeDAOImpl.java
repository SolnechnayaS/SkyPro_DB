import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class EmployeeDAOImpl implements EmployeeDAO {
    final String user = "postgres";
    final String password = "password";
    final String url = "jdbc:postgresql://localhost:5432/skyprodbhomework1";

    @Override
    public List<Employee> getAllEmployees() throws SQLException {

        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();

        String jpqlQuery = "SELECT s FROM Employee s";

        TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);

        List<Employee> employees = query.getResultList();

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManagerFactory.close();

        return employees;
    }

    @Override
    public Employee getEmployeeById(int idRequest) {

        Employee findEmployee = null;
        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();
        findEmployee = entityManager.find(Employee.class, idRequest);

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManagerFactory.close();

        return findEmployee;
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


        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();

        Employee newEntity = new Employee(firstNameOfEmployee, lastNameOfEmployee, genderOfEmployee, ageOfEmployee, cityIdOfEmployee);

        entityManager.persist(newEntity);

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManagerFactory.close();

    }

    @Override
    public void updateEmployee(Employee employee) {

        Scanner scanner = new Scanner(System.in);
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
                    employee.setFirst_name(scanner.useDelimiter("\n").next());
                    numChange = 5;
                    break;
                case 2:
                    System.out.println("Введите новую ФАМИЛИЮ: ");
                    employee.setLast_name(scanner.useDelimiter("\n").next());
                    numChange = 5;
                    break;
                case 3:
                    System.out.println("Уточните ВОЗРАСТ: ");
                    employee.setAge(scanner.useDelimiter("\n").nextInt());
                    numChange = 5;
                    break;
                case 4:
                    System.out.println("Введите новый ID города проживания: ");
                    employee.setCity_id(scanner.useDelimiter("\n").nextInt());
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

        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();

        entityManager.merge(employee);

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManagerFactory.close();

    }

    @Override
    public void deleteEmployee(Employee employee) {

        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();

        entityManager.remove(entityManager.find(Employee.class, employee.getId()));

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManagerFactory.close();

    }
}
