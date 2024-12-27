package com.test.service;

import com.test.domain.entity.UserData;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserDetailServiceImplements implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        String userName=user.split("!")[0];
       String  email=user.split("!")[1];

        UserData userData = userRepo.findByUserNameAndEmail(userName,email).orElseThrow();
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(userData.getRole()));
        return new User(userData.getUserName(), userData.getPassword(), authorities);
    }
}
