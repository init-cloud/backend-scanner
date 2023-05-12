package scanner.oauth.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import scanner.checklist.entity.CustomRule;
import scanner.checklist.entity.UsedRule;
import scanner.checklist.repository.CheckListRepository;
import scanner.checklist.repository.UsedCheckListRepository;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiAuthException;
import scanner.oauth.dto.OAuthDto;
import scanner.oauth.middleware.OAuthRequestFacade;
import scanner.security.dto.Token;
import scanner.user.entity.User;
import scanner.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final CheckListRepository checkListRepository;
	private final UsedCheckListRepository usedCheckListRepository;
	private final OAuthRequestFacade oauthRequestFacade;

	/**
	 * If user registered, just return token.
	 * Else, register user and return token.
	 * @return user Token
	 */
	public Token getUserAccessToken(@NotNull String code) {
		String tokenResponse = oauthRequestFacade.requestGithubOAuthToken(code);
		OAuthDto.GithubUserDetail userDetail = oauthRequestFacade.requestGithubUserDetail(tokenResponse);

		User user = getUserIfExist(userDetail);

		return oauthRequestFacade.createSocialUserToken(user.getUsername());
	}

	/**
	 * @return registered User
	 */
	public User getUserIfExist(OAuthDto.GithubUserDetail userDetail) {
		Optional<User> user = userRepository.findByUsername(userDetail.getLogin());

		if (user.isPresent())
			return user.get();

		User socialUser = userRepository.save(User.addIndividualSocialUser(userDetail));

		initializeUserRules(socialUser);

		return socialUser;
	}

	/**
	 * Initialize UsedRule from CustomRule for new User.
	 */
	private void initializeUserRules(User user) {
		List<CustomRule> originRules = checkListRepository.findAll();
		List<UsedRule> usedRules = new ArrayList<>();

		for (CustomRule originRule : originRules)
			usedRules.add(new UsedRule(originRule, user));

		usedCheckListRepository.saveAll(usedRules);
	}

	/**
	 * Redirect to Github login page
	 */
	public void redirectGithub(HttpServletResponse response, String redirect) {
		try {
			String url = oauthRequestFacade.getRedirectAuthUrl(redirect);
			response.sendRedirect(url);
		} catch (IOException e) {
			throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
		}
	}
}
