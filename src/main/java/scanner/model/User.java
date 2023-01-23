package scanner.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import scanner.dto.user.UserSignupDto;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


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
    @Setter
    private LocalDateTime modifiedAt;

    @Column(name = "LAST_LOGIN")
    @Setter
    private LocalDateTime lastLogin;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column
    @Setter
    private String authorities;

    @Column(name = "ROLE_TYPE")
    @Enumerated(EnumType.STRING)
    @Setter
    private RoleType roleType;

    @Column(name = "USER_STATE")
    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Setter
    @Column(name = "EMAIL")
    private String email;

    @Setter
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

    @Builder
    public User(LocalDateTime modifiedAt,
                String username,
                String authorities,
                RoleType roleType) {
        this.modifiedAt = modifiedAt;
        this.username = username;
        this.authorities = authorities;
        this.roleType = roleType;
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
        Collection<GrantedAuthority> authorityList = new ArrayList<>();

        for(String authority : this.authorities.split(","))
            authorityList.add(new SimpleGrantedAuthority(authority));

        return authorityList;
    }

    public static String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        StringBuilder sb = new StringBuilder();

        for(GrantedAuthority authority : authorities)
            sb.append(authority.getAuthority());

        return sb.toString();
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
        return this.userState.equals(UserState.ACTIVATE);
    }
}
