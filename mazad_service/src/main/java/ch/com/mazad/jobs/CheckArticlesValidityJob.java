package ch.com.mazad.jobs;

import ch.com.mazad.domain.Article;
import ch.com.mazad.restclients.RestCLientCallback;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@EnableBatchProcessing
public class CheckArticlesValidityJob
{
    private static String REQUEST_VALID_ARTICLES = "select *  from article a where a.validity_duration > 0";
    @Inject
    private JobBuilderFactory jobBuilders;
    @Inject
    private StepBuilderFactory stepBuilders;
    @Inject
    private DataSource dataSource;
    @Inject
    private RestCLientCallback restCLientCallback;

    @Bean
    public Job smsBefore24hNotificationJob()
    {
        return jobBuilders.get("checkArticlesValidityJobLauncher")
                .incrementer(jobParametersIncrementer())
                .listener(protocolListener())
                .flow(checkArticlesValidityStep())
                .end()
                .build();
    }

    @Bean
    public Step checkArticlesValidityStep()
    {
        return stepBuilders.get("checkArticlesValidityStep")
                .<Article, Article>chunk(1)
                .reader(checkArticlesValidityReader())
                .writer(items -> items.stream().filter(article -> article.getCreationDate() == null ||
                        ((Long)Duration.between(article.getCreationDate(), LocalDateTime.now()).toMinutes()).compareTo(article.getValidityDuration().longValue()) > 0)
                        .forEach(article -> restCLientCallback.validateArticle(article.getReference())))
                .faultTolerant()
                .skipLimit(10)
                .skip(MySQLIntegrityConstraintViolationException.class)
                .build();
    }

    @Bean
    public ItemReader<Article> checkArticlesValidityReader()
    {

        JdbcCursorItemReader<Article> reader = new JdbcCursorItemReader<>();
        reader.setSql(REQUEST_VALID_ARTICLES);
        reader.setDataSource(dataSource);
        reader.setRowMapper((rs, i) -> {
            Article article = new Article();
            article.setReference(rs.getString("reference"));
            article.setCreationDate(rs.getTimestamp("creation_date") != null ? rs.getTimestamp("creation_date").toLocalDateTime() : null);
            article.setValidityDuration(rs.getInt("validity_duration"));
            return article;
        });
        return reader;
    }

    @Bean
    public ProtocolListener protocolListener()
    {
        return new ProtocolListener();
    }

    @Bean
    public JobParametersIncrementer jobParametersIncrementer()
    {
        return new SampleIncrementer();
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository() throws Exception
    {
        return new MapJobRepositoryFactoryBean(transactionManager()).getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository)
    {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }

    public class SampleIncrementer implements JobParametersIncrementer
    {
        public JobParameters getNext(JobParameters parameters)
        {
            if(parameters == null || parameters.isEmpty())
            {
                return new JobParametersBuilder().addLong("run.id", System.currentTimeMillis()).toJobParameters();
            }
            long id = parameters.getLong("run.id", 1L) + System.currentTimeMillis();
            return new JobParametersBuilder().addLong("run.id", id).toJobParameters();
        }
    }
}
