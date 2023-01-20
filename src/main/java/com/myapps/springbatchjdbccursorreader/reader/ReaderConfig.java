package com.myapps.springbatchjdbccursorreader.reader;

import com.myapps.springbatchjdbccursorreader.domain.Customer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class ReaderConfig {

    // If you want to use the CustomerRowMapper implementation (at mapper package), uncomment the code below.
    /*
    private final CustomerRowMapper clientRowMapper;
    public ReaderConfig(CustomerRowMapper clientRowMapper) {
        this.clientRowMapper = clientRowMapper;
    }
    */
    @Bean
    public JdbcCursorItemReader<Customer> jdbcCursorReader(
        // Injects the datasource bean defined in DataSourceConfig
        @Qualifier("batchAppDataSource") DataSource dataSource
    ) {
        // The code below just demonstrates that is possible to create a object and set each property without chaining.
        /*
        JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<Customer>();
        reader.setName("jdbcCursorReader");
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM public.clients");
        reader.setRowMapper(rowMapper());
        return reader;
         */

        return new JdbcCursorItemReaderBuilder<Customer>()
            .name("jdbcCursorReader")
            .dataSource(dataSource)
            .sql("SELECT * FROM public.clients")
            /*
                The mapper below must be used only if the domain class attributes are the same as the result set fields
                or columns from the database, or following standard naming conventions such as field_name or fieldName
                beanRowMapper(domain_class) or
                rowMapper(new BeanPropertyRowMapper<domain_class>(domain_class))
            */
            //.beanRowMapper(Customer.class)
            /*
                The mapper below can be used everytime, that's the recommended method, since it can map any result set
                field or column names from the database to any domain class attribute.
                Just need to implement the RowMapper interface.
                The commented code is the interface implementation one, if you want to use it, just uncomment the
                constructor, and comment any other row mappers.
            */
            //.rowMapper(clientRowMapper)
            .rowMapper(rowMapper())
            .build();
    }

    private RowMapper<Customer> rowMapper() {
        return new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setAge(rs.getString("age"));
                customer.setEmail(rs.getString("email"));
                return customer;
            }
        };
    }
}
