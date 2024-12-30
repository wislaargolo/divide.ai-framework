package com.ufrn.imd.divide.ai.framework.service;

import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.model.UserDetailsImpl;
import com.ufrn.imd.divide.ai.framework.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCaseAndActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        return new UserDetailsImpl(user);
    }
}
