package scanner.model;


import lombok.*;
import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_REFRESH_TOKEN")
public class UserRefreshToken extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_REFRESH_TOKEN_ID")
    private Long id;

    @Getter
    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private User userId;


    @Getter
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Builder
    public UserRefreshToken(User user, String refreshToken){
        this.userId = user;
        this.refreshToken = refreshToken;
    }
}
