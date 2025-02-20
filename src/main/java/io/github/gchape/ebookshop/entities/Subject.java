package io.github.gchape.ebookshop.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject_name", nullable = false, unique = true)
    private String subjectName;

    @OneToMany(mappedBy = "subject")
    private Set<Book> books = new HashSet<>();

    public Subject(String name) {
        this.subjectName = name;
    }

    public Subject() {

    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String name) {
        this.subjectName = name;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
