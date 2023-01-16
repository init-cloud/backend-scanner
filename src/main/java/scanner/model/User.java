package scanner.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.dto.user.UserSignupDto;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    public static User toEntity(UserSignupDto dto){
        return User.builder()
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roleType(RoleType.GUEST)
                .userState(UserState.ACTIVATE)
                .email(dto.getEmail())
                .contact(dto.getContact())
                .build();
    }
}
