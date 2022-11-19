package pyankov.springprojects.gtdwebapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pyankov.springprojects.gtdwebapp.models.Directory;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Integer> {
    Directory findDirectoryByName(String name);
}
