package com.example;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class home {

	@RequestMapping("/")
	String homeIndex() throws IOException {
		return "home";
	}
}
