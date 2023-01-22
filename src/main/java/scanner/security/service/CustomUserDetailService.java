package scanner.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import scanner.dto.user.UserRoleAuthorityDto;
import scanner.exception.ApiException;
import scanner.model.User;
import scanner.repository.UserRepository;
import scanner.response.enums.ResponseCode;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));
    }

    @Transactional
    public UserRoleAuthorityDto updateRole(UserRoleAuthorityDto dto){

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));

        user.setRoleType(dto.getRole());
        user.setModifiedAt(LocalDateTime.now());

        userRepository.save(user);

        return dto;
    }


    @Transactional
    public UserRoleAuthorityDto updateAuthority(UserRoleAuthorityDto dto){

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ApiException(ResponseCode.STATUS_4008));

        user.setAuthorities(User.getAuthorities(dto.getAuthorities()));
        user.setModifiedAt(LocalDateTime.now());

        userRepository.save(user);

        return dto;
    }
}
