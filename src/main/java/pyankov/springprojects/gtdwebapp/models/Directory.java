package pyankov.springprojects.gtdwebapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "directory")
public class Directory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Column(name = "name")
    @Getter
    private String name;

    @ManyToMany(mappedBy = "directories")
    @Getter
    private List<Note> notes;
}
