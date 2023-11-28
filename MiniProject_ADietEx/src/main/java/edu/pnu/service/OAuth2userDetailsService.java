package edu.pnu.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class OAuth2userDetailsService extends DefaultOAuth2UserService {
	
	@Autowired
	private MemberRepository memRepo;
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	OAuth2User ouser = super.loadUser(userRequest);
	
	// 자동 회원가입
	String name = ouser.getAttribute("name");
	Optional<Member> member = memRepo.findById(name);
	if (!member.isPresent()) {
		memRepo.save(Member.builder()
				.username(name)
				.role(Role.ROLE_MEMBER)
				.email(ouser.getAttribute("email").toString())
				.build());
	}
	
	// JWT 토큰 발행
	String token = JWT.create()
			.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10*100))
			.withClaim("username", name)
			.sign(Algorithm.HMAC256("jwt_edu_temp"));
	
//	response.setHeader("Authorization", "Bearer" + token);
//	response.setHeader("Access-Control-Expose-Headers", "Authorization");
	asdf(HttpServletRequest request, http)
	
	System.out.println("JWT토큰: " + token);
	
	System.out.println("엑세스토큰:" + userRequest.getAccessToken().getTokenValue());
	
	System.out.println("OAuth2User:" + ouser.getAttributes());
	
	return ouser;
	}
	
	public String asdf() {
		
	}
}
