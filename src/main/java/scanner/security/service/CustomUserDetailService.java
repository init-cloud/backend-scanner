package scanner.security.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import scanner.user.dto.UserDetailsDto;
import scanner.common.exception.ApiException;
import scanner.auth.entity.User;
import scanner.user.repository.UserRepository;
import scanner.common.enums.ResponseCode;

import javax.persistence.*;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));
	}

	/* for the future */
	@Transactional
	public UserDetailsDto.Managing modifyUserRole(UserDetailsDto.Managing dto) {

		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		User modifiedUser = User.toEntityByModifying(user, user.getLastLogin(), user.getPassword(),
			user.getAuthoritiesToString(), dto.getRole(), user.getUserState(), user.getEmail(), user.getContact());

		userRepository.save(modifiedUser);

		return dto;
	}

	/* for the future */
	@Transactional
	public UserDetailsDto.Managing modifyUserAuthority(UserDetailsDto.Managing dto) {

		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		User modifiedUser = User.toEntityByModifying(user, user.getLastLogin(), user.getPassword(),
			User.getAuthorities(dto.getAuthorities()), user.getRoleType(), user.getUserState(), user.getEmail(),
			user.getContact());

		userRepository.save(modifiedUser);

		return dto;
	}

	@Transactional
	public UserDetailsDto.Managing modifyUserDetails(UserDetailsDto.Managing dto) {

		EntityManager manager = emf.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		try {
			User user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

			User modifiedUser = User.toEntityByModifying(user, user.getLastLogin(), user.getPassword(),
				User.getAuthorities(dto.getAuthorities()), dto.getRole(), user.getUserState(), user.getEmail(),
				user.getContact());

			manager.persist(modifiedUser);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new ApiException(e, ResponseCode.INVALID_USER);
		} finally {
			manager.close();
		}

		return dto;
	}

	@Transactional
	public List<UserDetailsDto.Retrieve> getUserList() {

		List<User> user = userRepository.findAll();

		return user.stream().map(UserDetailsDto.Retrieve::new).collect(Collectors.toList());
	}
}
