package ru.etu.filmlibrary.models.data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "figure")
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private FigureType typeId;

    private String fullname;
    private String sex;
    private Date birthday;

    @ManyToMany(mappedBy = "figures")
    private Set<Film> films = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FigureType getTypeId() {
        return typeId;
    }

    public void setTypeId(FigureType typeId) {
        this.typeId = typeId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    @Override
    public String toString() {
        return id + " " + typeId.getTitle() + " " + fullname + " " + sex + " " +
                new SimpleDateFormat("dd/MM/yyyy").format(birthday);
    }
}
