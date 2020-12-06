package kr.chis.springbootwebjpaapi.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResponsePageImpl<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePageImpl(@JsonProperty("content") List<T> content,
                            @JsonProperty("number") int number,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") Long totalElements,
                            @JsonProperty("pageable") JsonNode pageable,
                            @JsonProperty("last") boolean last,
                            @JsonProperty("totalPages") int totalPages,
                            @JsonProperty("sort") JsonNode sort,
                            @JsonProperty("first") boolean first,
                            @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public static <U> ResponsePageImpl<U> of(Page<U> page){
        if (page == null) return new ResponsePageImpl<>(Page.empty());
        return new ResponsePageImpl<>(page);
    }

    public ResponsePageImpl(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());


    }

    public ResponsePageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ResponsePageImpl(List<T> content) {
        super(content);
    }

    public ResponsePageImpl(T ... array) {
        super(Stream.of(array).collect(Collectors.toList()));
    }

    public ResponsePageImpl() {
        super(new ArrayList<>());
    }
}