package com.billywords.config.security;

import com.billywords.security.models.AuthorityEntity;
import com.billywords.user.models.UsersEntity;
import com.billywords.user.repository.UsersEntityRepository;
import com.billywords.user.vo.WordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersEntityRepository usersEntityRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UsersEntity> user = usersEntityRepository.findByEmail(s);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (AuthorityEntity role : user.get().getAuthorityEntityList()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthorityName()));
        }

        return new WordUser(user.get().getId(),
                user.get().getFromLanguage(),
                user.get().getToLanguage(),
                user.get().getEmail(),
                user.get().getPassword(),
                grantedAuthorities);
    }
}
