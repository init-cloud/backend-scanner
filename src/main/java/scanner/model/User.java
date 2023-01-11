package scanner.model;


import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE_TYPE")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "USER_STATE")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "contact")
    private String contact;
}
