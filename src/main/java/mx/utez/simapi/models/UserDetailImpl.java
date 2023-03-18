package mx.utez.simapi.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailImpl implements UserDetails {

    private String correo;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getCorreo() {
        return correo;
    }

    public UserDetailImpl() {
    }

    public UserDetailImpl(String correo, String password, Collection<? extends GrantedAuthority> authorities) {
        this.correo = correo;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailImpl build(Usuarios usuario) {
        List<String> roles = new ArrayList<>();
        roles.add("SA");
        roles.add("E");
        roles.add("A");

        Collection<GrantedAuthority> authority = roles.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new UserDetailImpl(usuario.getCorreo(), usuario.getPassword(), authority);
    }
}
