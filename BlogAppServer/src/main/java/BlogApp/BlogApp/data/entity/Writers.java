package BlogApp.BlogApp.data.entity;

import javax.persistence.*;

@Entity
@Table(name="writer")
public class Writers {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "writer_id")
    private Integer writerId;

    @Column
    private String name;

    @Column
    private String password;

    public Writers() {}

    public Writers(Integer writerId, String name, String password) {
        this.writerId = writerId;
        this.name = name;
        this.password = password;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Writers{" +
                "writerId=" + writerId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}