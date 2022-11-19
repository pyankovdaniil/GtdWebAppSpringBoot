package pyankov.springprojects.gtdwebapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "note")
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private int id;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "is_done")
    @Getter
    @Setter
    private boolean isDone;

    @Column(name = "deadline")
    @Getter
    @Setter
    private String deadline;

    @ManyToMany
    @JoinTable(
            name = "note_directory",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "directory_id")
    )
    @Getter
    @Setter
    private List<Directory> directories;
}
