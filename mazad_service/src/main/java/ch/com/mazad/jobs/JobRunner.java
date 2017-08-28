package ch.com.mazad.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.util.Date;

@EnableScheduling
@Configuration
public class JobRunner
{
    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);
    @Inject
    private SimpleJobLauncher launcher;
    @Inject
    private Job checkArticlesValidityJobLauncher;

    public JobParameters jobParameter()
    {
        return new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters();
    }

    @Scheduled(cron = "${mazad.check_validity.cron}")
    public void launchSmsNotification()
    {
        try
        {
          /*  JobExecution execution = */
            launcher.run(checkArticlesValidityJobLauncher, jobParameter());
        }
        catch (Exception e)
        {
            logger.error("Unable execution job for  sending sms", e);
        }
    }
}
