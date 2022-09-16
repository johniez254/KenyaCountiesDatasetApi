package com.johnson.KenyaCountiesDatasetApi.services;

import com.johnson.KenyaCountiesDatasetApi.models.MyUserDetails;
import com.johnson.KenyaCountiesDatasetApi.models.User;
import com.johnson.KenyaCountiesDatasetApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByusername(username);

        user.orElseThrow(()-> new UsernameNotFoundException("Not Found: " + username));

        return user.map(MyUserDetails::new).get();
    }

    public List<User> getUserByUsername(String username){
        return userRepository.findByUsernameContaining(username);
    }


    public Collection<? extends User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                true, "ROLE_USER"));
    }

    public boolean userExists(String username) {
        return userRepository.findByusername(username).isPresent();
    }

    public User updateUser(User user, Long id) {
        User userFetched = userRepository.findById(id).get();
        userFetched.setRoles(user.getRoles());
        userFetched.setActive(user.isActive());
        return userRepository.save(userFetched);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
