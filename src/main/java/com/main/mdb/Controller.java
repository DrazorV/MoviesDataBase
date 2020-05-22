package com.main.mdb;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@org.springframework.stereotype.Controller
public class Controller {

	private final UserRepository userRepository;
	User LoginUser;

	public Controller(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping("/")
	public String root (Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

	@RequestMapping("home")
	public String HOME() {
		Long a = LoginUser.getId();
		return "home";
	}


	@ModelAttribute("/user")
	public User user() {
		return new User();
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registration";

	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid User user, BindingResult result){

		User existing = userRepository.findByEmail(user.getEmail());
		if (existing != null){
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()){
			return "registration";
		}

		userRepository.save(user);
		return "redirect:/?success";
	}

	@PostMapping("/")
	public String login(@ModelAttribute("user") @Valid User user, BindingResult result){

		LoginUser = userRepository.findByEmail(user.getEmail());
		if (LoginUser == null){
			result.rejectValue("email", null, "There is no account registered with that email");
		}else{
			if (!LoginUser.getPassword().equals(user.getPassword())) {
				result.rejectValue("password", null, "Wrong password, try again.");
			}
		}
		if (result.hasErrors()){
			return "index";
		}else{
			return "home";
		}
	}
}
