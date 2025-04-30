package by.artur.testtask.services;

import by.artur.testtask.entities.EmailLog;
import by.artur.testtask.entities.PhoneLog;
import by.artur.testtask.entities.TransferLog;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElasticsearchService {

    private final ElasticsearchClient elasticsearchClient;
    private final Map<String, String> INDEX_NAMES = Map.of("transfer", "transfer_logs", "phone", "changes_phone", "email", "changes_email");

    public void createIndices() throws IOException {
        for (Map.Entry<String, String> entry : INDEX_NAMES.entrySet()) {
            if (!isIndexExists(entry.getValue())) {
                CreateIndexRequest request = new CreateIndexRequest.Builder().index(entry.getValue()).build();
                elasticsearchClient.indices().create(request);
            }
        }
    }

    public void addTransferLog(TransferLog transferLog) throws IOException {
        IndexRequest<TransferLog> request = new IndexRequest.Builder<TransferLog>().index(INDEX_NAMES.get("transfer"))
                .id(UUID.randomUUID().toString()).document(transferLog).build();
        elasticsearchClient.index(request);
    }

    public void addPhoneLog(PhoneLog phoneLog) throws IOException {
        IndexRequest<PhoneLog> request = new IndexRequest.Builder<PhoneLog>().index(INDEX_NAMES.get("phone"))
                .id(UUID.randomUUID().toString()).document(phoneLog).build();
        elasticsearchClient.index(request);
    }

    public void addEmailLog(EmailLog emailLog) throws IOException {
        IndexRequest<EmailLog> request = new IndexRequest.Builder<EmailLog>().index(INDEX_NAMES.get("email"))
                .id(UUID.randomUUID().toString()).document(emailLog).build();
        elasticsearchClient.index(request);
    }

    public List<TransferLog> getUserTransfersHistory(Long userId) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index(INDEX_NAMES.get("transfer"))
                .query(q -> q.matchPhrase(m -> m.field("userId").query(userId.toString()))).build();
        SearchResponse<TransferLog> searchResponse = elasticsearchClient.search(searchRequest, TransferLog.class);
        List<Hit<TransferLog>> hits = searchResponse.hits().hits();
        return hits.stream().map(Hit::source).collect(Collectors.toList());
    }

    public List<PhoneLog> getUserPhonesHistory(Long userId) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index(INDEX_NAMES.get("phone"))
                .query(q -> q.matchPhrase(m -> m.field("userId").query(userId.toString()))).build();
        SearchResponse<PhoneLog> searchResponse = elasticsearchClient.search(searchRequest, PhoneLog.class);
        List<Hit<PhoneLog>> hits = searchResponse.hits().hits();
        return hits.stream().map(Hit::source).collect(Collectors.toList());
    }

    public List<EmailLog> getUserEmailsHistory(Long userId) throws IOException {
        SearchRequest searchRequest = new SearchRequest.Builder().index(INDEX_NAMES.get("email"))
                .query(q -> q.matchPhrase(m -> m.field("userId").query(userId.toString()))).build();
        SearchResponse<EmailLog> searchResponse = elasticsearchClient.search(searchRequest, EmailLog.class);
        List<Hit<EmailLog>> hits = searchResponse.hits().hits();
        return hits.stream().map(Hit::source).collect(Collectors.toList());
    }

    public boolean isIndexExists(String indexName) throws IOException {
        ExistsRequest request = ExistsRequest.of(e -> e.index(indexName));
        var response = elasticsearchClient.indices().exists(request);
        return response.value();
    }
}
