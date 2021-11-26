package com.system.repository;

import com.system.models.Issue;
import com.system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
    List<Issue> findByReturned(Integer returned);
    Long countByUserAndReturned(User user,Integer returned);
    List<Issue> findByReturnedAndUser(Integer returned,User user);
   // Issue findByIssue(Issue issue);
}
