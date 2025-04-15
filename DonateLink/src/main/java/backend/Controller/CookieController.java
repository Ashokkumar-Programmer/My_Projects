package backend.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieController {
	void setCookie(HttpServletResponse response, String username, String password) {
		Cookie username_cookie = new Cookie("donatelink_username", username);
		username_cookie.setMaxAge(2592000);
		Cookie password_cookie = new Cookie("donatelink_password", password);
		password_cookie.setMaxAge(2592000);
		response.addCookie(username_cookie);
		response.addCookie(password_cookie);
	}
	
	String getCookieUsername(String username, String password) {
		return username+"  "+password;
	}
	
	void deleteCookie(HttpServletResponse response) {
		Cookie username_cookie = new Cookie("donatelink_username", "");
		username_cookie.setMaxAge(0);
		Cookie password_cookie = new Cookie("donatelink_password", "");
		password_cookie.setMaxAge(0);
		response.addCookie(username_cookie);
		response.addCookie(password_cookie);
	}
}
