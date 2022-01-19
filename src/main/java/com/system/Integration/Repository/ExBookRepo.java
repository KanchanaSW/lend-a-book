package com.system.Integration.Repository;

import com.system.Integration.Models.ExBook;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;

@Repository
public interface ExBookRepo extends JpaRepository<ExBook,Integer> {

    @Query("FROM ExBook eb where eb.title like %:title%")
    List<ExBook> searchByTitle(String title);
}
