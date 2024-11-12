package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "role_id")
    private Integer id;

    private String authority;

    public Role(){
        super();
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Integer id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
