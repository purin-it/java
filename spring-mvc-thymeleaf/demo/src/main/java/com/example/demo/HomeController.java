package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		//現在日時をModelオブジェクトに設定
		String formattedDate = convertLocalDateTimeToStr(LocalDateTime.now(), "uuuu/MM/dd HH:mm:ss");	
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
     * 日時(西暦)を文字列変換した結果を返す
     * @param localDateTime 日時(西暦)
     * @param dateTimeFormat 日時(西暦)のフォーマット
     * @return 変換後の文字列
     */
    private static String convertLocalDateTimeToStr(LocalDateTime localDateTime, String dateTimeFormat){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateTimeFormat).withResolverStyle(ResolverStyle.STRICT);
        return localDateTime.format(df);
    }
	
}
