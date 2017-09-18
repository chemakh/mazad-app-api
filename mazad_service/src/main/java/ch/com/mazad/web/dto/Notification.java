package ch.com.mazad.web.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chemakh on 05/09/2017.
 */
public class Notification
{
    private String operation;
    private UserDTO createdByUser;
    private LocalDateTime creationDate;
    private List<UserDTO> consernedUsers = new ArrayList<>();
    private ArticleDTO article;

    public Notification()
    {

    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public ArticleDTO getArticle()
    {
        return article;
    }

    public void setArticle(ArticleDTO article)
    {
        this.article = article;
    }

    public List<UserDTO> getConsernedUsers()
    {
        return consernedUsers;
    }

    public void setConsernedUsers(List<UserDTO> consernedUsers)
    {
        this.consernedUsers = consernedUsers;
    }

    public UserDTO getCreatedByUser()
    {
        return createdByUser;
    }

    public void setCreatedByUser(UserDTO createdByUser)
    {
        this.createdByUser = createdByUser;
    }

    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate)
    {
        this.creationDate = creationDate;
    }
}
