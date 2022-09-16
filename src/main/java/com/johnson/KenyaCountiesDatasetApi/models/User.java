package com.johnson.KenyaCountiesDatasetApi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table (name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;

    //    @Schema(description = "This is the username to authenticate the user")
    private String username;

    //    @Schema(description = "This is the hashed password to authenticate the user")
    private String password;

    //    @Schema(description = "A boolean value to check if user is active or disabled")
    private boolean active;

    //    @Schema(description = "The roles field stores all the roles assigned to various users")
    private String roles;

    //    constructors
    public User(String username, String password, boolean active, String roles) {
        super();
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
}
