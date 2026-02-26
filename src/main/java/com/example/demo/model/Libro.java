package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(

        String title,

        List<Autor> authors,

        List<String> languages,

        @JsonAlias("download_count")
        Integer downloadCount
) {
}
