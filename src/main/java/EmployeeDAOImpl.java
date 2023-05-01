import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> getAllEmployees() throws SQLException {

        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();
        String jpqlQuery = "SELECT s FROM Employee s";
        TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);
        List<Employee> employees = new ArrayList<>(query.getResultList());
        entityManager.getTransaction().commit();

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        return employees;
    }

    public List<Employee> getAllEmployeesFromCity(String cityName) throws SQLException {

        List<Employee> employees = new ArrayList<>();

        EntityManager entityManager = EMF.emfCreate();

        try {
            entityManager.getTransaction().begin();
            String jpqlQuery1 = "SELECT s FROM City s WHERE city_name=" + "'" + cityName + "'";
            TypedQuery<City> query1 = entityManager.createQuery(jpqlQuery1, City.class);
            City city = query1.getSingleResult();
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            String jpqlQuery = "SELECT s FROM Employee s WHERE city_id=" + city.getCity_id();
            TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);
            employees = query.getResultList();
            entityManager.getTransaction().commit();

        } catch (NullPointerException | NoResultException e) {
            entityManager.getTransaction().commit();
            System.out.println("Указанный город не существует!");
        }

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        return employees;
    }

    @Override
    public Employee getEmployeeById(int idRequest) {

        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();
        Employee findEmployee = entityManager.find(Employee.class, idRequest);
        entityManager.getTransaction().commit();

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        return findEmployee;
    }

    @Override
    public Employee createEmployeeWithoutCity() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ИМЯ нового сотрудника: ");
        String firstNameOfEmployee = scanner.useDelimiter("\n").next();
        System.out.println("Введите ФАМИЛИЮ нового сотрудника: ");
        String lastNameOfEmployee = scanner.useDelimiter("\n").next();
        System.out.println("Введите ПОЛ нового сотрудника: " +
                "0 - мужской (значение по умолчанию)\t" +
                "1 - женский");
        String genderOfEmployee = "male";
        int numGenderOfEmployee = scanner.useDelimiter("\n").nextInt();
        switch (numGenderOfEmployee) {
            case 1:
                genderOfEmployee = "female";
            default:
                break;
        }

        System.out.println("Введите ВОЗРАСТ нового сотрудника: ");
        int ageOfEmployee = scanner.useDelimiter("\n").nextInt();

        return new Employee(firstNameOfEmployee, lastNameOfEmployee, genderOfEmployee, ageOfEmployee, 0);
    }

    @Override
    public Employee createEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Employee employee = createEmployeeWithoutCity();

        System.out.println("Введите ГОРОД нового сотрудника: ");
        String cityNewEmployeeName = scanner.useDelimiter("\n").next();

        CityDAO cityDAO = new CityDAOImpl();
        City cityNewEmployee = cityDAO.findCityByName(cityNewEmployeeName);

        EntityManager entityManager = EMF.emfCreate();
        entityManager.getTransaction().begin();
        employee.setCity_id(cityNewEmployee.getCity_id());
        entityManager.persist(employee);
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().commit();

        System.out.println("Создан новый сотрудник: ");
        System.out.println(employee);

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {

        EntityManager entityManager = EMF.emfCreate();

        try {
            entityManager.getTransaction().begin();
            entityManager.find(Employee.class, employee.getId());
            entityManager.getTransaction().commit();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Выберите, какие данные будем изменять: " +
                    "1 - Имя; " +
                    "2 - Фамилия; " +
                    "3 - Возраст; " +
                    "4 - Пол; " +
                    "5 - Город проживания;");
            int numChange = scanner.useDelimiter("\n").nextInt();

            while (numChange != 0) {
                switch (numChange) {
                    case 1:
                        System.out.println("Введите новое ИМЯ: ");
                        employee.setFirst_name(scanner.useDelimiter("\n").next());
                        numChange = 6;
                        break;
                    case 2:
                        System.out.println("Введите новую ФАМИЛИЮ: ");
                        employee.setLast_name(scanner.useDelimiter("\n").next());
                        numChange = 6;
                        break;
                    case 3:
                        System.out.println("Уточните ВОЗРАСТ: ");
                        employee.setAge(scanner.useDelimiter("\n").nextInt());
                        numChange = 6;
                        break;
                    case 4:
                        System.out.println("Уточните ПОЛ: " +
                                "0 - мужской (значение по умолчанию)\t" +
                                "1 - женский");
                        String genderOfEmployee = "male";
                        int numGenderOfEmployee = scanner.useDelimiter("\n").nextInt();
                        switch (numGenderOfEmployee) {
                            case 1:
                                genderOfEmployee = "female";
                            default:
                                break;
                        }
                        employee.setGender(genderOfEmployee);
                        numChange = 6;
                        break;
                    case 5:
                        System.out.println("Введите новый город проживания: ");
                        String newCityName = scanner.useDelimiter("\n").next();

                        CityDAO cityDAO = new CityDAOImpl();
                        employee.setCity_id(cityDAO.findCityByName(newCityName).getCity_id());
                        numChange = 6;
                        break;
                    default:
                        System.out.println("Выберите, какие данные будем изменять: " +
                                "1 - Имя; " +
                                "2 - Фамилия; " +
                                "3 - Возраст; " +
                                "4 - Пол; " +
                                "5 - Город проживания; " +
                                "0 - Завершить редактирование.");
                        numChange = scanner.useDelimiter("\n").nextInt();
                }
            }


            entityManager.getTransaction().begin();
            entityManager.merge(employee);
            entityManager.getTransaction().commit();

            entityManager.getEntityManagerFactory().close();
            entityManager.close();

            System.out.println("Данные сотрудника обновлены успешно!");
        } catch (RuntimeException e) {
            entityManager.getTransaction().commit();
            System.out.println("Сотрудник не найден!");
        }
    }

    @Override
    public void deleteEmployee(Employee employee) {
        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();
        entityManager.find(City.class, employee.getCity_id()).getEmployees().remove(entityManager.find(Employee.class, employee.getId()));
//        entityManager.remove(entityManager.find(Employee.class, employee.getId()));
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().commit();


        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        System.out.println("Сотрудник удален из базы!");

    }

}
