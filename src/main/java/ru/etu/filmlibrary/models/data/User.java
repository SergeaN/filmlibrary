package ru.etu.filmlibrary.models.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false)
    private UserType userType;

    private String login;
    private String password;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_film_id")
    private Film favoriteFilm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_figure_id")
    private Figure favoriteFigure;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_genre_id")
    private Genre favoriteGenre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserType> userTypes = new ArrayList<>();
        userTypes.add(getUserType());
        return userTypes;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Film getFavoriteFilm() {
        return favoriteFilm;
    }

    public void setFavoriteFilm(Film favoriteFilm) {
        this.favoriteFilm = favoriteFilm;
    }

    public Figure getFavoriteFigure() {
        return favoriteFigure;
    }

    public void setFavoriteFigure(Figure favoriteFigure) {
        this.favoriteFigure = favoriteFigure;
    }

    public Genre getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(Genre favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(login).append(" ").append(password).append(" ")
                .append(email).append(" ").append(userType.getTitle()).append(" ");
        if (favoriteFilm != null) sb.append(favoriteFilm.getTitle());
        if (favoriteFigure != null) sb.append(favoriteFigure.getFullname());
        if (favoriteGenre != null) sb.append(favoriteGenre.getTitle());
        return sb.toString();
    }
}
