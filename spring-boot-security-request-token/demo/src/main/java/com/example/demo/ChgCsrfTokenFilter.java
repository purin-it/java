package com.example.demo;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChgCsrfTokenFilter extends OncePerRequestFilter {

	//ログ出力のためのクラス
	private static Log log = LogFactory.getLog(ChgCsrfTokenFilter.class);

	/**
	 * CSRFトークンリポジトリ
	 */
	private final HttpSessionCsrfTokenRepository csrfTokenRepository;

	/**
	 * コンストラクタ
	 * @param csrfTokenRepository CSRFトークンリポジトリ
	 */
	public ChgCsrfTokenFilter(HttpSessionCsrfTokenRepository csrfTokenRepository) {
    	this.csrfTokenRepository = csrfTokenRepository;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//CSRFトークンを取得する
		CsrfToken beforeToken = this.csrfTokenRepository.loadToken(request);
		//CSRFトークンが取得できた場合は、CSRFトークンを更新する
		if(beforeToken != null){
			this.csrfTokenRepository.saveToken(null, request, response);
			CsrfToken csrfToken = this.csrfTokenRepository.generateToken(request);
			this.csrfTokenRepository.saveToken(csrfToken, request, response);
			request.setAttribute(CsrfToken.class.getName(), csrfToken);
			request.setAttribute(csrfToken.getParameterName(), csrfToken);
		}
        //次のFilterを呼び出す
        filterChain.doFilter(request, response);
	}

}
