package org.linksharing.server.auth;

import org.linksharing.server.user.User;
import org.linksharing.server.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

//    @Autowired
    public DefaultUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(username + "User not found");
        return new UserPrincipal(user);
    }
}
