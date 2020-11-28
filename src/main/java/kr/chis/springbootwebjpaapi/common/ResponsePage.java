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

public class ResponsePage<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePage(@JsonProperty("content") List<T> content,
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

    public static <U> ResponsePage of(Page<U> page){
        if (page == null) return new ResponsePage<U>(Page.empty());
        return new ResponsePage<U>(page);
    }

    public ResponsePage(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());


    }

    public ResponsePage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ResponsePage(List<T> content) {
        super(content);
    }

    public ResponsePage(T ... array) {
        super(Stream.of(array).collect(Collectors.toList()));
    }

    public ResponsePage() {
        super(new ArrayList<>());
    }
}