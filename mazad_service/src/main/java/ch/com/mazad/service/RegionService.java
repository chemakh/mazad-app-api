package ch.com.mazad.service;

import ch.com.mazad.domain.Region;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.ArticleRepository;
import ch.com.mazad.repository.RegionRepository;
import ch.com.mazad.utils.TokenUtil;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 27/06/2017.
 */
@Service
public class RegionService
{
    private final Logger logger = LoggerFactory.getLogger(RegionService.class);
    @Inject
    private RegionRepository regionRepository;
    @Inject
    private ArticleRepository articleRepository;

    public Region createRegion(Region region)
    {

        region.setReference(TokenUtil.generateReference());
        return regionRepository.save(region);
    }

    public Region updateRegion(Region region, String reference) throws MazadException
    {

        regionRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Region.class, reference));

        return regionRepository.save(region);
    }

    public List<Region> getRegion(String reference) throws MazadException
    {

        if(reference != null)
            return Collections.singletonList(regionRepository.findOneByReference(reference)
                    .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Region.class, reference)));

        else
            return regionRepository.findAll();
    }

    public JSONObject deleteRegion(String reference) throws MazadException
    {

        Region region = regionRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Region.class, reference));

        articleRepository.save(articleRepository.findByRegionReference(reference).stream().map(article ->
        {
            article.setRegion(null);
            return article;
        }).collect(Collectors.toList()));

        regionRepository.delete(region);

        JSONObject result = new JSONObject();
        result.put("result", "delete-success");
        return result;
    }
}
