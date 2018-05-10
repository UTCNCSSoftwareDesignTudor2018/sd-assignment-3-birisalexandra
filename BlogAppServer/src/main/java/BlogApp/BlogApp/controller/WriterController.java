package BlogApp.BlogApp.controller;

import BlogApp.BlogApp.business.WriterService;
import BlogApp.BlogApp.data.entity.Writers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WriterController {

    @Autowired
    WriterService writerService;

    public Writers findWriter(String username) {
        return writerService.findWriterByUsername(username);
    }
}
