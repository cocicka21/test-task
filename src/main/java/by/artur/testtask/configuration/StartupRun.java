package by.artur.testtask.configuration;

import by.artur.testtask.services.ElasticsearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRun implements CommandLineRunner {

    private final ElasticsearchService elasticsearchService;

    public StartupRun(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @Override
    public void run(String... args) throws Exception {
        elasticsearchService.createIndices();
    }
}
