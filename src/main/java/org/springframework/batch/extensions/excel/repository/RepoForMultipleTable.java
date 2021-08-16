package org.springframework.batch.extensions.excel.repository;

import org.springframework.batch.extensions.excel.dto.MultiTableDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource(value = {"classpath:queries.properties"})
public class RepoForMultipleTable {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //SQL query has been defined in the queries.properties file inside resources folder
    @Value(value = "${myQuery}")
    private String myQuery;

    public RepoForMultipleTable(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<MultiTableDTO> getDataFromMultipleTables(final int authorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", authorId);
        return namedParameterJdbcTemplate.query(myQuery, params, new BeanPropertyRowMapper(MultiTableDTO.class));
    }
}
