package scanner.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import scanner.dto.user.UserSignupDto;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;


@Getter
@Entity
@Table(name = "USER")
@NoArgsConstructor
public class User implements UserDetails {

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

    @Builder
    public User(Long id,
                LocalDateTime modifiedAt,
                LocalDateTime lastLogin,
                String username,
                String password,
                RoleType roleType,
                UserState userState,
                String email,
                String contact){
        this.id = id;
        this.modifiedAt = modifiedAt;
        this.lastLogin = lastLogin;
        this.username = username;
        this.password = password;
        this.roleType = roleType;
        this.userState = userState;
        this.email = email;
        this.contact = contact;
    }

    @Builder
    public User(LocalDateTime modifiedAt,
                String username,
                String password,
                RoleType roleType,
                UserState userState,
                String email,
                String contact){
        this.modifiedAt = modifiedAt;
        this.username = username;
        this.password = password;
        this.roleType = roleType;
        this.userState = userState;
        this.email = email;
        this.contact = contact;
    }

    public static User toEntity(UserSignupDto dto){
        return User.builder()
                .modifiedAt(LocalDateTime.now())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roleType(RoleType.GUEST)
                .userState(UserState.ACTIVATE)
                .email(dto.getEmail())
                .contact(dto.getContact())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
