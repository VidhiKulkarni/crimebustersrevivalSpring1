package com.nighthawk.spring_portfolio.mvc.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@RestController
<<<<<<< HEAD
@CrossOrigin(origins = "https://lwu1822.github.io/crimebustersrevival/login")
=======
//@CrossOrigin(origins={"https://lwu1822.github.io"})
@CrossOrigin(origins="http://localhost:4002")
>>>>>>> 71f84bbb788df497f09e464cd166cbe34df03c5e
public class JwtApiController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@PostMapping(value="/login/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Person authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		final ResponseCookie tokenCookie = ResponseCookie.from("jwt", token)
			.httpOnly(true)
			.secure(true)
			.path("/login/authenticate")
			.maxAge(3600)
			.sameSite("None; Secure")
			// .domain("example.com") // Set to backend domain
			.build();
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.set(HttpHeaders.SET_COOKIE, tokenCookie.toString());
		// return new ResponseEntity<>(null, headers, HttpStatus.OK);

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).build();
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}