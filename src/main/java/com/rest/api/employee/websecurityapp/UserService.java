package com.rest.api.employee.websecurityapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	   @Autowired
	   OAuthDao oauthDao;

	   @Override
	   public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
	      UserEntity userEntity = null;
	      try {
	         userEntity = oauthDao.getUserDetails(username);
	         CustomUser customUser = new CustomUser(userEntity);
	         return customUser;
	      } catch (Exception e) {
	         e.printStackTrace();
	         throw new UsernameNotFoundException("User " + username + " was not found in the database");
	      }
	   }
	}