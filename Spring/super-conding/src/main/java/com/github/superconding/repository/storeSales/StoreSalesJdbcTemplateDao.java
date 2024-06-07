package com.github.superconding.repository.storeSales;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreSalesJdbcTemplateDao implements StoreSalesRepository {

    private JdbcTemplate jdbcTemplate;

    public StoreSalesJdbcTemplateDao(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static RowMapper<StoreSales> storeSalesRowMapper = (((rs, rowNum) ->
                new StoreSales.StoreSalesBuilder()
                        .id(rs.getInt("id"))
                        .storeName(rs.getNString("store_name"))
                        .amount(rs.getInt("amount"))
                        .build()));

    @Override
    public StoreSales findStoreSalesById(Integer storeId) {
        return jdbcTemplate.queryForObject(" SELECT * from store sales WHERE id = ?", storeSalesRowMapper, storeId);
    }

    @Override
    public void updateSalesAmount(Integer storeId, Integer amount) {
        jdbcTemplate.update(" UPDATE store_sales " +
                                " SET amount = ? " +
                                " WHERE id = ? ", amount, storeId);
    }
}
