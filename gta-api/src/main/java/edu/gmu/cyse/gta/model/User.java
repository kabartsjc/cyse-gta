package edu.gmu.cyse.gta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String gmuID;
    
    private boolean hasApplication=false;
    
    private String role;

    public User(String username, String password, String name, String email, String gmuID, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gmuID=gmuID;
        this.email = email;
        this.role = role;
    }
    
    
    
   
}
