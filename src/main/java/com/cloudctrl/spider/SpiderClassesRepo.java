package com.cloudctrl.spider;

import java.util.List;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpiderClassesRepo {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public SpiderClass findById(long id) {

        return template.queryForObject(
                "select id, type, class_type, name, comment from classes where id = :id",
                ImmutableMap.of("id", id),
                (rs, rowNum) -> new SpiderClass(rs));
    }

    public List<SpiderClass> findAllLike(String name, int limit, int offset) {
        if (limit < 1 || limit > 10000) {
            throw new IllegalArgumentException("Invalid limit");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("Invalid offset");
        }
        String theName = (name == null ? "" : name) + "%";
        return template.query(
                "select id, type, class_type, name, comment from classes " +
                        "where name like :name " +
                        "order by name, id " +
                        "limit :limit offset :offset",
                ImmutableMap.of("name", name,
                        "limit", limit,
                        "offset", offset),
                (rs, rowNum) -> new SpiderClass(rs));
    }
}