package com.eduMap.edumap.GLOBALE.Entity;

import com.eduMap.edumap.GLOBALE.enums.Role;
import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;




    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
