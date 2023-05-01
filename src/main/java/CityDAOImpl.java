import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CityDAOImpl implements CityDAO {

    @Override
    public City findCityByName(String cityName) {
        City city = null;
        EntityManager entityManager = EMF.emfCreate();
        try {
            entityManager.getTransaction().begin();
            String jpqlQuery = "SELECT s FROM City s WHERE city_name=" + "'" + cityName + "'";
            TypedQuery<City> query1 = entityManager.createQuery(jpqlQuery, City.class);
            city = query1.getSingleResult();
            entityManager.getTransaction().commit();

        } catch (RuntimeException e) {
//            System.out.println("Город с названием " + cityName + " не найден!");
            entityManager.getTransaction().commit();
            city = new City(cityName);

            entityManager.getTransaction().begin();
            entityManager.persist(city);
            entityManager.getTransaction().commit();
        }

        return city;
    }

    @Override
    public List<City> getAllCity() throws SQLException {


        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();
        String jpqlQuery = "SELECT s FROM City s";
        TypedQuery<City> query = entityManager.createQuery(jpqlQuery, City.class);
        List<City> cities = query.getResultList();
        entityManager.getTransaction().commit();

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        return cities;
    }

    @Override
    public City getCityById(int idRequest) {

        EntityManager entityManager = EMF.emfCreate();

        entityManager.getTransaction().begin();
        City findCity = entityManager.find(City.class, idRequest);
        entityManager.getTransaction().commit();

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        return findCity;
    }

    @Override
    public void createCity() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите ГОРОД нового сотрудника: ");
        String cityNewEmployeeName = scanner.useDelimiter("\n").next();

        EntityManager entityManager = EMF.emfCreate();

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = employeeDAO.createEmployeeWithoutCity();

        City cityNewEmployee = findCityByName(cityNewEmployeeName);

        entityManager.getTransaction().begin();
        employee.setCity_id(cityNewEmployee.getCity_id());
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();

        System.out.println("Хотите добавить еще сотрудников из этого же города?" +
                "\n1 - да, добавить еще сотрудников из города: " + cityNewEmployee.getCity_name() +
                "\n0 - нет, больше сотрудников нет");
        int addEmployee = scanner.useDelimiter("\n").nextInt();
        while (addEmployee != 0) {
            switch (addEmployee) {
                case 1:
                    Employee employeeNext = employeeDAO.createEmployeeWithoutCity();
                    entityManager.getTransaction().begin();
                    employeeNext.setCity_id(cityNewEmployee.getCity_id());
                    entityManager.getTransaction().commit();

                    entityManager.getTransaction().begin();
                    entityManager.persist(employeeNext);
                    entityManager.flush();
                    entityManager.clear();
                    entityManager.getTransaction().commit();
                    addEmployee = 2;
                    break;
                default:
                    System.out.println("Хотите добавить еще сотрудников из этого же города?" +
                            "\n1 - да, добавить еще сотрудников из города: " + cityNewEmployee.getCity_name() +
                            "\n0 - нет, больше сотрудников нет");
                    addEmployee = scanner.useDelimiter("\n").nextInt();
                    break;

            }
        }

        entityManager.getEntityManagerFactory().close();
        entityManager.close();

        System.out.println("Создан новый город: ");
        System.out.println(cityNewEmployee);
    }

    @Override
    public void updateCity(City city) {
        EntityManager entityManager = EMF.emfCreate();
//        entityManager.getTransaction().begin();
        String oldCityName = city.getCity_name();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите обновленное название города: ");
        String newCityName = scanner.useDelimiter("\n").next();

        try {
            entityManager.getTransaction().begin();
            String jpqlQuery1 = "SELECT s FROM City s WHERE city_name=" + "'" + newCityName + "'";
            TypedQuery<City> query1 = entityManager.createQuery(jpqlQuery1, City.class);
            City existCity = null;
            try {
                existCity = query1.getResultList().get(0);
            } catch (IndexOutOfBoundsException e) {
                existCity = query1.getSingleResult();
            }

            System.out.println("Город с названием - " + newCityName + " - уже существует, его city_id=" + existCity.getCity_id());
            entityManager.getTransaction().commit();


            System.out.println("Хотите изменить название города - " + oldCityName + " на " + newCityName + " для всех сотрудников из города " + oldCityName + "?\n" +
                    "1 - да, изменить;\n" +
                    "0 - оставить название города " + oldCityName + " без изменений");
            int changeCity = scanner.nextInt();
            switch (changeCity) {
                case 1:
                    entityManager.getTransaction().begin();
                    City finalExistCity = existCity;
                    entityManager.find(City.class, city.getCity_id())
                            .getEmployees()
                            .forEach(employee -> employee.setCity_id(finalExistCity.getCity_id()));
                    System.out.println("Всем сотрудникам из города: " + oldCityName + ", установлен город с названием - " + newCityName + ", его city_id=" + existCity.city_id);
                    entityManager.flush();
                    entityManager.clear();
                    entityManager.getTransaction().commit();

                    entityManager.getTransaction().begin();
                    deleteCity(oldCityName);
                    entityManager.getTransaction().commit();
                    break;
                default:
                    System.out.println("Изменения в название города не внесены!");
            }

        } catch (NullPointerException | NoResultException e) {
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            city.setCity_name(newCityName);
            entityManager.merge(city);
            entityManager.getTransaction().commit();
        }

        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }

    @Override
    public void deleteCity(String cityName) {
        EntityManager entityManager = EMF.emfCreate();

        try {
            entityManager.getTransaction().begin();
            String jpqlQuery = "SELECT s FROM City s WHERE city_name=" + "'" + cityName + "'";
            TypedQuery<City> query1 = entityManager.createQuery(jpqlQuery, City.class);
            City city = query1.getSingleResult();
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(City.class, city.getCity_id()));
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
            System.out.println("Город успешно удален!");
        } catch (NullPointerException | NoResultException e) {
            entityManager.getTransaction().commit();
            System.out.println("Город, который Вы хотите удалить, не существует");
        }

        entityManager.getEntityManagerFactory().close();
        entityManager.close();
    }
}
