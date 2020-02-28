package org.belova.registration.service;

import org.belova.registration.models.User;
import org.belova.registration.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        LOGGER.info("Запрос на вход в систему пользователя {}", email);
        List<User> users = this.userRepository.findUserByEmail(email);

        if(users.isEmpty()) {
            LOGGER.info("Пользователь {} не найден", email);
            throw new UsernameNotFoundException("Пользователь %s не найден");
        }

        LOGGER.info("Пользователь существует");
        Set<GrantedAuthority> roles = new HashSet();

        LOGGER.info("Определяем роль");

        if(users.get(0).getUserRole().equals("USER")) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if(users.get(0).getUserRole().equals("ADMIN")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(users.get(0), users.get(0).getPassword(), roles));

        return new org.springframework.security.core.userdetails.User(email,
                users.get(0).getPassword(),
                true,
                true,
                true,
                true,
                roles);
    }

}
