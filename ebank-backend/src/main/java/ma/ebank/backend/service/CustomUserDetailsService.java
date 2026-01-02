package ma.ebank.backend.service;

import ma.ebank.backend.model.User;
import ma.ebank.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        // Retourne directement User (qui implémente UserDetails maintenant)
        return userRepository.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Login ou mot de passe erronés"));
    }
}