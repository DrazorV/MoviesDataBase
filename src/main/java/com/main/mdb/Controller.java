package com.main.mdb;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.stereotype.Controller
public class Controller {

	private final UserRepo userRepo;

	public Controller(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@GetMapping("/create")
	public String greetingForm(Model model) {
		model.addAttribute("user", new User());
		return "create";

	}

	@PostMapping("/create")
	public String greetingSubmit(@ModelAttribute User user) {
		userRepo.save(new User(user.getEmail(),user.getPassword()));
		return "redirect:/";
	}

	@RequestMapping("/")
	public String index (Model model) {
		model.addAttribute("user", new User());
		return "index";
	}
}
