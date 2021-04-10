package me.thewing.url_shortening.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlShortenerViewController {

	@GetMapping
	public String index(Model model) {

		return "index";
	}
}
