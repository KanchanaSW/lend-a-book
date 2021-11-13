package com.system.security.services;

import com.system.models.User;
import com.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

		return UserDetailsImpl.build(user);
	}

	public User getByEmail(String email){
		Optional<User> user=userRepository.findByEmail(email);
		User u=null;
		if (user.isPresent()){
			u= user.get();
		}
		return u;
	}
	public User getById(Long id){return userRepository.findById(id).get();}
	public Optional<User> findByUsername(String username){
		return userRepository.findByEmail(username);
	}
}
