package scanner.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import scanner.dto.user.UserSignupDto;
import scanner.model.enums.OAuthProvider;
import scanner.model.enums.RoleType;
import scanner.model.enums.UserAuthority;
import scanner.model.enums.UserState;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "IS_OAUTHED")
    @Size(max = 1)
    private Character isOAuthed;

    @Column(name = "OAUTH_PROVIDER")
    @Size(max = 16)
    private OAuthProvider oAuthProvider;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "LAST_LOGIN")
    @Setter
    private LocalDateTime lastLogin;

    @Column(name = "USERNAME")
    @Size(max = 32)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AUTHORITIES")
    @Setter
    private String authorities;

    @Column(name = "ROLE_TYPE")
    @Enumerated(EnumType.STRING)
    @Setter
    @Size(max = 8)
    private RoleType roleType;

    @Column(name = "USER_STATE")
    @Enumerated(EnumType.STRING)
    @Size(max = 8)
    private UserState userState;

    @Setter
    @Column(name = "EMAIL")
    @Size(max = 128)
    private String email;

    @Setter
    @Column(name = "contact")
    @Size(max = 16)
    private String contact;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UsedRule> usedRules = new ArrayList<>();

    @Builder
    public User(LocalDateTime lastLogin,
                String username,
                String password,
                Character isOAuthed,
                OAuthProvider oAuthProvider,
                RoleType roleType,
                String authorities,
                UserState userState,
                String email,
                String contact,
                List<UsedRule> usedRules
    ){
        this.lastLogin = lastLogin;
        this.isOAuthed = isOAuthed;
        this.oAuthProvider = oAuthProvider;
        this.username = username;
        this.password = password;
        this.roleType = roleType;
        this.authorities = authorities;
        this.userState = userState;
        this.email = email;
        this.contact = contact;
        this.usedRules = usedRules;
    }

    public static User toEntity(UserSignupDto dto){
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .isOAuthed('n')
                .oAuthProvider(OAuthProvider.NONE)
                .roleType(RoleType.GUEST)
                .authorities(UserAuthority.GUEST.toString())
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
