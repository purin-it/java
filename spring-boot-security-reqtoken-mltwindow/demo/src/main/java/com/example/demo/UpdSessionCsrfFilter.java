package com.example.demo;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdSessionCsrfFilter extends OncePerRequestFilter {

	/**
	 * CSRFトークンキーのデフォルト値
	 */
	private static final String DEFAULT_NAME
			= HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");

	/**
	 * CSRFトークンリポジトリ
	 */
	private final HttpSessionCsrfTokenRepository csrfTokenRepository;

	/**
	 * コンストラクタ
	 * @param csrfTokenRepository CSRFトークンリポジトリ
	 */
	public UpdSessionCsrfFilter(HttpSessionCsrfTokenRepository csrfTokenRepository) {
    	this.csrfTokenRepository = csrfTokenRepository;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//ウィンドウ名を取得する
		String windowName = request.getParameter("windowName");
		//ウィンドウ名の値に応じたセッションキーを設定する
		if(windowName != null) {
			this.csrfTokenRepository.setSessionAttributeName(DEFAULT_NAME + "." + windowName);
		}else {
			this.csrfTokenRepository.setSessionAttributeName(DEFAULT_NAME);
		}
        //次のFilterを呼び出す
        filterChain.doFilter(request, response);
	}

}
