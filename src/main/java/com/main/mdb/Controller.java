package com.main.mdb;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@org.springframework.stereotype.Controller
public class Controller {

	private final SaveRepository saveRepository;
	private final UserRepository userRepository;
	User loginUser;

	public Controller(SaveRepository saveRepository, UserRepository userRepository) {
		this.saveRepository = saveRepository;
		this.userRepository = userRepository;
	}

	@RequestMapping("/")
	public String root (Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/bookmark", method = RequestMethod.POST)
	public @ResponseBody
	boolean Bookmark(@RequestParam("id") String id) {
		UserSave userSave = new UserSave(loginUser,id);
		saveRepository.save(userSave);
		return true;
	}

	@Transactional
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/bookmarked", method = RequestMethod.POST)
	public @ResponseBody
	boolean UnBookmark(@RequestParam("id") String id) {
		saveRepository.deleteByUserAndMovieId(loginUser,id);
		return true;
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	UserSave Updater(@RequestParam("id") String id) {
		return saveRepository.findByUserAndMovieId(loginUser,id);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/getBookmarks", method = RequestMethod.POST)
	public @ResponseBody
	Iterable<UserSave> getBookmarks() {
		return saveRepository.findAllByUser(loginUser);
	}

	@RequestMapping("home")
	public String HOME() {
		return "home";
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registration";

	}

	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid User User, BindingResult result){

		User existing = userRepository.findByEmail(User.getEmail());
		if (existing != null){
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()){
			return "registration";
		}

		userRepository.save(User);
		return "redirect:/?success";
	}

	@PostMapping("/")
	public String login(@ModelAttribute("user") @Valid User User, BindingResult result){

		loginUser = userRepository.findByEmail(User.getEmail());
		if (loginUser == null){
			result.rejectValue("email", null, "There is no account registered with that email");
		}else{
			if (!loginUser.getPassword().equals(User.getPassword())) {
				result.rejectValue("password", null, "Wrong password, try again.");
			}
		}
		if (result.hasErrors()){
			return "index";
		}else{
			return "home";
		}
	}

	@PostMapping("/logout")
	public String logout(){
		return "redirect:/";
	}
}
