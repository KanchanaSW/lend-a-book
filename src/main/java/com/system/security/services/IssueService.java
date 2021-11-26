package com.system.security.services;

import com.system.models.Issue;
import com.system.models.User;
import com.system.repository.IssueRepository;
import com.system.repository.IssuedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IssueService {

    private IssueRepository issueRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private IssuedBookService issuedBookService;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

    public Issue get(Long issueId) {
        return issueRepository.findById(issueId).get();
    }

    public List<Issue> getAllUnreturned() {
        return issueRepository.findByReturned(0);
    }

    public Issue addNew(Issue issue) {
        issue.setIssueDate(new Date());
        issue.setReturned(0);
        return issueRepository.save(issue);
    }

    public Issue save(Issue issue) {
        return issueRepository.save(issue);
    }

    public Long getCountByUser(User user) {
        return issueRepository.countByUserAndReturned(user, 0);
    }

    public List<Issue> findBynotReturUser(User user) {
        return issueRepository.findByReturnedAndUser(0, user);
    }

}










