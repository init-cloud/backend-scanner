package scanner.security.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import scanner.dto.user.UserManagingDto;
import scanner.dto.user.UserRetrieveDto;
import scanner.exception.ApiException;
import scanner.model.user.User;
import scanner.repository.UserRepository;
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
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));
	}

	@Transactional
	public UserManagingDto modifyUserRole(UserManagingDto dto) {

		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));

		user.setRoleType(dto.getRole());

		userRepository.save(user);

		return dto;
	}

	@Transactional
	public UserManagingDto modifyUserAuthority(UserManagingDto dto) {

		User user = userRepository.findByUsername(dto.getUsername())
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));

		user.setAuthorities(User.getAuthorities(dto.getAuthorities()));

		userRepository.save(user);

		return dto;
	}

	@Transactional
	public UserManagingDto modifyUserDetails(UserManagingDto dto) {

		EntityManager manager = emf.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		try {
			User user = userRepository.findByUsername(dto.getUsername())
				.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));

			user.setRoleType(dto.getRole());
			user.setAuthorities(User.getAuthorities(dto.getAuthorities()));

			manager.persist(user);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new ApiException(e, ResponseCode.STATUS_5008);
		} finally {
			manager.close();
		}

		return dto;
	}

	@Transactional
	public List<UserRetrieveDto> getUserList() {

		List<User> user = userRepository.findAll();

		return user.stream()
			.map(UserRetrieveDto::new)
			.collect(Collectors.toList());
	}
}
