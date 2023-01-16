package scanner.model;


import javax.persistence.*;

public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_TOKEN_ID")
    private Long id;

    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
}
