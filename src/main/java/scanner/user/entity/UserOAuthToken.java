package scanner.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.common.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Entity
@Table(name = "USER_OAUTH_TOKEN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOAuthToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_OAUTH_TOKEN_ID")
	private Long id;

	@Column(name = "ACCESS_TOKEN")
	@NotNull
	private String accessToken;

	@Column(name = "REFRESH_TOKEN")
	@NotNull
	private String refreshToken;

	@Column(name = "SCOPE")
	@NotNull
	private String scope;

	@Column(name = "TOKEN_TYPE")
	@NotNull
	@Size(max = 8)
	private String tokenType;

	@Column(name = "EXPIRES_IN")
	@NotNull
	private String expiresIn;

	@Column(name = "REFRESH_TOKEN_EXPIRES_IN")
	@NotNull
	private Long refreshTokenExpiresIn;
}
