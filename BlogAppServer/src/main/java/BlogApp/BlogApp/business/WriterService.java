package BlogApp.BlogApp.business;

import BlogApp.BlogApp.data.entity.Writers;
import BlogApp.BlogApp.data.repository.WriterJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterService {

    @Autowired
    WriterJpaRepository writerJpaRepository;

    public Writers findWriterByUsername(String username) {
        return writerJpaRepository.findByName(username);
    }
}
