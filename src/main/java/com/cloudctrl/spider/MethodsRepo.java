package com.cloudctrl.spider;

import java.util.List;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MethodsRepo {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public Method findById(long id) {

        return template.queryForObject(
                "select id, selector, source from methods where id = :id",
                ImmutableMap.of("id", id),
                (rs, rowNum) -> new Method(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)));
    }

    public List<Method> findAllLike(String selector, int limit, int offset) {
        if (limit < 1 || limit > 10000) {
            throw new IllegalArgumentException("Invalid limit");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("Invalid offset");
        }
        return template.query(
                "select id, selector, source from methods " +
                        "where selector like :sel " +
                        "order by selector, id " +
                        "limit :limit offset :offset",
                ImmutableMap.of("sel", selector,
                        "limit", limit,
                        "offset", offset),
                (rs, rowNum) -> new Method(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)));
    }
}