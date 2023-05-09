package scanner.user.service;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;
import scanner.security.config.Properties;
import scanner.security.dto.Token;
import scanner.security.provider.JwtTokenProvider;
import scanner.security.provider.UsernamePasswordAuthenticationProvider;
import scanner.user.dto.UserAuthDto;
import scanner.user.dto.UserBaseDto;
import scanner.user.dto.UserDetailsDto;
import scanner.user.entity.User;
import scanner.user.enums.RoleType;
import scanner.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UsernameService implements UserService {
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
	private final PasswordEncoder passwordEncoder;
	private final Properties properties;

	/**
	 * Register User on System.
	 * @return Token
	 */
	@Override
	public Token signup(UserAuthDto.Signup dto) {
		userRepository.findByUsername(dto.getUsername()).ifPresent(user -> {
			throw new ApiException(ResponseCode.EXISTED_USER);
		});

		String password = dto.setHash(passwordEncoder, dto.getPassword());
		User user = userRepository.save(User.addUser(dto, password));
		return jwtTokenProvider.create(user.getUsername(), RoleType.GUEST, properties.getSecret());
	}

	/**
	 * Issue access-token.
	 * @return Token
	 */
	@Transactional
	@Override
	public Token signin(UserAuthDto.Authentication dto) {

		Authentication authentication = usernamePasswordAuthenticationProvider.authenticate(
			new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		updateLastLogin(dto);

		return jwtTokenProvider.create(dto.getUsername(), ((User)authentication.getPrincipal()).getRoleType(),
			properties.getSecret());
	}

	/**
	 *	Update last-login datetime
	 */
	@Override
	public void updateLastLogin(UserBaseDto dto) {
		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));
		user.modifyUserLoginDateTime();

		userRepository.save(user);
	}

	/**
	 *	Return Current-Accessed User
	 * @return User who accessed
	 */
	@Override
	public User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();

		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));
	}

	/**
	 * Get current-accessed User's Profile
	 * @return Profile
	 */
	public UserDetailsDto.Profile getUserProfile() {
		User user = getCurrentUser();

		return new UserDetailsDto.Profile(user);
	}

	/**
	 * Update current-accessed User's Profile
	 * @return updated Profile
	 */
	@Transactional
	public UserDetailsDto.Profile manageUserProfile(UserDetailsDto.Profile dto) {
		User user = getCurrentUser();
		user.modifyUserContactsProfile(dto);
		userRepository.save(user);

		return dto;
	}

	/**
	 * Update User's Password
	 * @return Boolean
	 */
	@Transactional
	public Boolean modifyUserPassword(UserAuthDto.Authentication dto) {
		try {
			User user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

			user.setPassword(dto.setHash(passwordEncoder, dto.getPassword()));

			userRepository.save(user);

			return true;
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_PASSWORD_ERROR);
		}
	}
}
