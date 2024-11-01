package kh.edu.cstad.business.domain;

import jakarta.persistence.Column;

import java.util.List;

public class Category {
    @Column (unique = true, nullable = false)
    private String alias;
    @Column (unique = true, nullable = false)
    private String title;
    private List<String> parent_aliases;
    private List<String> country_whitelist;
    private List<String> country_blacklist;
}
