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
import scanner.common.utils.HeaderParse;
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

	@Override
	public Token signup(UserAuthDto.Signup dto) {
		userRepository.findByUsername(dto.getUsername()).ifPresent(user -> {
			throw new ApiException(ResponseCode.EXISTED_USER);
		});

		String password = dto.setHash(passwordEncoder, dto.getPassword());
		User user = userRepository.save(User.addUser(dto, password));
		return jwtTokenProvider.create(user.getUsername(), RoleType.GUEST, properties.getSecret());
	}

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

	@Override
	public void updateLastLogin(UserBaseDto dto) {
		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		user.modifyUserLastLoginNow();

		userRepository.save(user);
	}

	public UserDetailsDto.Profile getUserProfile(String header, String key) {
		String token = HeaderParse.getAccessToken(header);

		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(token, key))
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		return new UserDetailsDto.Profile(user.getUsername(), user.getEmail(), user.getContact(), user.getRoleType(),
			user.getLastLogin());
	}

	@Transactional
	public UserDetailsDto.Profile manageUserProfile(UserDetailsDto.Profile dto) {
		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		user.setEmail(dto.getEmail());
		user.setContact(dto.getContact());

		userRepository.save(user);

		return dto;
	}

	@Transactional
	public Boolean modifyUserPassword(UserAuthDto.Authentication dto) {
		try {
			User user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

			user.modifyUserPassword(dto.setHash(passwordEncoder, dto.getPassword()));

			userRepository.save(user);

			return true;
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_PASSWORD_ERROR);
		}
	}
}
