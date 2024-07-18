package com.example.WeCanScapeApi.controler;

import com.example.WeCanScapeApi.modele.ApiResponse;
import com.example.WeCanScapeApi.modele.Hobby;
import com.example.WeCanScapeApi.modele.User;
import com.example.WeCanScapeApi.modele.UserHobby;
import com.example.WeCanScapeApi.service.UserService;
import com.example.WeCanScapeApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{uId}")
	public User getUserById(@PathVariable String uId) {
		return userRepository.findByUId(uId).orElse(null);
	}

	@GetMapping("/{id}/hobbies")
	public ResponseEntity<List<Hobby>> getUserHobbies(@PathVariable Integer id) {
		List<Hobby> hobbies = userService.getHobbiesByUserId(id);
		return ResponseEntity.ok(hobbies);
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PostMapping("/hobbies/{userId}/{hobbyId}")
	public ResponseEntity<ApiResponse<String>> addHobbyToUser(@PathVariable Integer userId,
			@PathVariable Integer hobbyId) {
		// Vérifiez le nombre actuel de hobbies de l'utilisateur
		List<Hobby> currentHobbies = userService.getHobbiesByUserId(userId);
		if (currentHobbies.size() >= 3) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse<>(false, "Vous ne pouvez pas ajouter plus de 3 hobbies.", null));
		}

		// Vérifiez si le hobby est déjà associé à l'utilisateur
		boolean hobbyExists = currentHobbies.stream().anyMatch(uh -> uh.getId().equals(hobbyId));
		if (hobbyExists) {
			return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Vous possèdez déjà ce hobby.", null));
		}

		// Ajoutez le hobby à l'utilisateur
		Optional<UserHobby> userHobby = userService.addHobbyToUser(userId, hobbyId);
		if (userHobby.isPresent()) {
			return ResponseEntity.ok(new ApiResponse<>(true, "Hobby associé avec succès.", null));
		} else {
			return ResponseEntity.badRequest()
					.body(new ApiResponse<>(false, "Échec de l'association du hobby avec l'utilisateur.", null));
		}
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable Integer id, @RequestBody User user) {
		user.setId(id);
		return userRepository.save(user);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Integer id) {
		System.out.println(id);
		userRepository.deleteById(id);
	}

	@DeleteMapping("/hobbies/{userId}/{hobbyId}")
	public ResponseEntity<?> deleteUserHobby(@PathVariable Integer userId,
			@PathVariable Integer hobbyId) {
		System.out.println("Delete request received for userId: " + userId + ", hobbyId: " + hobbyId);
		boolean deleted = userService.deleteUserHobby(userId, hobbyId);
		if (deleted) {
			return ResponseEntity.ok(new ApiResponse<>(true, "Hobby supprimé.", null));
		} else {
			return ResponseEntity.badRequest()
					.body(new ApiResponse<>(false, "Échec de la suppression de la liaison utilisateur-hobby.", null));
		}
	}
}
