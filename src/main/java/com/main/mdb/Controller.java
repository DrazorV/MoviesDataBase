package com.main.mdb;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.stereotype.Controller
public class Controller {

	private final userRepo userRepo;

	public Controller(userRepo userRepo) {
		this.userRepo = userRepo;
	}

	@GetMapping("/create")
	public String greetingForm(Model model) {
		model.addAttribute("user", new user());
		return "create";

	}

	@PostMapping("/create")
	public String greetingSubmit(@ModelAttribute user user) {
		userRepo.save(new user(user.getEmail(),user.getPassword()));
		return "redirect:/";
	}

	@RequestMapping("/")
	public String index (Model model) {
		model.addAttribute("user", new user());
		return "index";
	}
}
