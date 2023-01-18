package scanner.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Table(name = "USER_TOKEN")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_TOKEN_ID")
    private Long id;

    @Getter
    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Getter
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Builder
    public UserToken(User user, String refreshToken){
        this.userId = user;
        this.refreshToken = refreshToken;
    }
}
