package BlogApp.BlogApp.data.repository;

import BlogApp.BlogApp.data.entity.Writers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterJpaRepository extends JpaRepository<Writers, Integer> {

    public Writers findByName(String username);

}
