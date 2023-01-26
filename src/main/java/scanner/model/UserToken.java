package scanner.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
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


    @CreatedDate
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
