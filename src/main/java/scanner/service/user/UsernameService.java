package scanner.service.user;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import scanner.common.utils.HeaderParse;
import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserDto;
import scanner.dto.user.UserProfileDto;
import scanner.dto.user.UserSignupDto;
import scanner.exception.ApiException;
import scanner.model.user.User;
import scanner.model.enums.RoleType;
import scanner.repository.UserRepository;
import scanner.common.enums.ResponseCode;
import scanner.security.config.Properties;
import scanner.security.dto.Token;
import scanner.security.provider.JwtTokenProvider;
import scanner.security.provider.UsernamePasswordAuthenticationProvider;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsernameService implements UserService {
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
	private final PasswordEncoder passwordEncoder;
	private final Properties properties;

	@Override
	public Token signup(UserSignupDto dto) {
		userRepository.findByUsername(dto.getUsername()).ifPresent(user -> {
			throw new ApiException(ResponseCode.EXISTED_USER);
		});

		String password = dto.setHash(passwordEncoder, dto.getPassword());
		User user = userRepository.save(User.toEntity(dto, password));
		return jwtTokenProvider.create(user.getUsername(), RoleType.GUEST, properties.getSecret());
	}

	@Transactional
	@Override
	public Token signin(UserAuthenticationDto dto) {

		Authentication authentication = usernamePasswordAuthenticationProvider.authenticate(
			new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		updateLastLogin(dto);

		return jwtTokenProvider.create(dto.getUsername(), ((User)authentication.getPrincipal()).getRoleType(),
			properties.getSecret());
	}

	@Override
	public void updateLastLogin(UserDto dto) {
		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		user.setLastLogin(LocalDateTime.now());

		userRepository.save(user);
	}

	public UserProfileDto getUserProfile(String header, String key) {
		String token = HeaderParse.getAccessToken(header);

		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(token, key))
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		return new UserProfileDto(user.getUsername(), user.getEmail(), user.getContact(), user.getRoleType(),
			user.getLastLogin());
	}

	@Transactional
	public UserProfileDto manageUserProfile(UserProfileDto dto) {
		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		user.setEmail(dto.getEmail());
		user.setContact(dto.getContact());

		userRepository.save(user);

		return dto;
	}

	@Transactional
	public Boolean modifyUserPassword(UserAuthenticationDto dto) {
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
