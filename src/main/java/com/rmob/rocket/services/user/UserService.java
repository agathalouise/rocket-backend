package com.rmob.rocket.services.user;

import com.rmob.rocket.entities.address.ViaCepResponse;
import com.rmob.rocket.entities.user.User;
import com.rmob.rocket.entities.user.model.UserRequest;
import com.rmob.rocket.entities.user.model.UserResponse;
import com.rmob.rocket.entities.user.model.UserUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
	Long createUser(UserRequest request, ViaCepResponse viaCepResponse);

	UserResponse findById(Long id);

	List<UserResponse> findAll();

	UserResponse updateUser(UserUpdate request, Long id);

	void delete(Long id);

	UserResponse findByEmail(String email);

	 User getUser(String email);

	void uploadDocs(List<MultipartFile> photos, Long id) throws Exception;
}
