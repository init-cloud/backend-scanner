package scanner.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.model.BaseEntity;

@Getter
@Entity
@Table(name = "USER_OAUTH_TOKEN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOAuthToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_OAUTH_TOKEN_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "ACCESS_TOKEN")
	@NotNull
	private String accessToken;

	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;

	@Column(name = "SCOPE")
	private String scope;

	@Column(name = "TOKEN_TYPE", length = 8)
	private String tokenType;

	@Column(name = "EXPIRES_IN")
	private Long expiresIn;

	@Column(name = "REFRESH_TOKEN_EXPIRES_IN")
	private Long refreshTokenExpiresIn;

	@Builder
	public UserOAuthToken(LocalDateTime createdAt, LocalDateTime modifiedAt, User user, String accessToken,
		String refreshToken, String scope, String tokenType, Long expiresIn, Long refreshTokenExpiresIn) {
		super(createdAt, modifiedAt);
		this.user = user;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.scope = scope;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}
}
