package scanner.model;


import lombok.*;
import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_TOKEN")
public class UserToken extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_TOKEN_ID")
    private Long id;

    @Getter
    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private User userId;


    @Getter
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Builder
    public UserToken(User user, String refreshToken){
        this.userId = user;
        this.refreshToken = refreshToken;
    }
}
