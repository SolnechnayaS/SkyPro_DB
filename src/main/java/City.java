import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name="city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    protected int city_id;

    @Column(name = "city_name", length = 100, nullable = false)
    protected String city_name;

    @OneToMany(mappedBy = "city", orphanRemoval = true, cascade = CascadeType.ALL)
    protected List<Employee> employees;

    protected int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public City(String city_name) {
        this.city_name = city_name;
    }

    public City() {

    }

    @Override
    public String toString() {
        return "id: " + city_id +
                ", название: " + city_name;
    }
}