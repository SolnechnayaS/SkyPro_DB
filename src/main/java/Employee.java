import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "first_name", length = 50, nullable = false)
    protected String first_name;

    @Column(name = "last_name", length = 50, nullable = false)
    protected String last_name;

    @Column(name = "gender", length = 6, nullable = false)
    protected String gender;

    @Column(name = "age", nullable = false)
    protected int age;

    @Column(name = "city_id")
    protected int city_id;

    public Employee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public Employee(String first_name, String last_name, String gender, int age, int city_id) {
//        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.age = age;
        this.city_id = city_id;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", имя: " + first_name + '\'' +
                ", фамилия: " + last_name + '\'' +
                ", пол: " + gender + '\'' +
                ", возраст: " + age +
                ", id города: " + city_id +
                '}';
    }
}
