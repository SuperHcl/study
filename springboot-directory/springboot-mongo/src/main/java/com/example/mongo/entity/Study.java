package com.example.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/10/14 16:19
 * @description:
 */
@Document(collection = "hcl")
@Data
public class Study {
    @Id
    private String id;

    private String title;

    private String description;

    private String by;

    private String url;

    private List<String> tags;

    private Integer likes;
}
