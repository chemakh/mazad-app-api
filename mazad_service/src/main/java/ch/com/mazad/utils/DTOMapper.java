package ch.com.mazad.utils;

import ch.com.mazad.domain.*;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.*;
import ch.com.mazad.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import javax.inject.Inject;
import java.math.BigInteger;

/**
 * Created by Chemakh on 20/06/2017.
 */
@Mapper(componentModel = "spring")
public abstract class DTOMapper {

    public static BigInteger ZERO = BigInteger.ZERO;

    @Inject
    ArticleRepository articleRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    BidRepository bidRepository;

    @Inject
    PhotoRepository photoRepository;

    @Inject
    SaleRepository saleRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    AddressRepository addressRepository;


    @Mappings({
            @Mapping(target = "createdByUserReference", source = "article.createdBy.reference"),
            @Mapping(target = "category", source = "article.category.name")})
    public abstract ArticleDTO fromArticleToDTO(Article article);

    @Mappings({
            @Mapping(target = "createdBy", expression = "java(userRepository.findOneByReference(dto.getCreatedByUserReference()).orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, dto.getCreatedByUserReference())))"),
            @Mapping(target = "category", expression = "java(categoryRepository.findOneByName(dto.getCategory()).orElse(null))"),
            @Mapping(target = "id", expression = "java(articleRepository.findIdByReference(dto.getReference()).orElse(ZERO).longValue())")
    })
    public abstract Article fromDTOToArticle(ArticleDTO dto) throws MazadException;

    public abstract UserDTO fromUserToDTO(User user);

    @Mappings({
            @Mapping(target = "id", expression = "java(userRepository.findIdByReference(dto.getReference()).orElse(ZERO).longValue())")
    })
    public abstract User fromDTOToUser(UserDTO dto);

    @Mappings({
            @Mapping(target = "articleReference", source = "article.reference"),
            @Mapping(target = "userReference", source = "user.reference")
    })
    public abstract BidDTO fromBidToDTO(Bid bid);

    @Mappings({
            @Mapping(target = "article", expression = "java(articleRepository.findOneByReference(dto.getArticleReference()).orElse(null))"),
            @Mapping(target = "user", expression = "java(userRepository.findOneByReference(dto.getUserReference()).orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, dto.getUserReference())))"),
            @Mapping(target = "id", expression = "java(bidRepository.findIdByReference(dto.getReference()).orElse(ZERO).longValue())")
    })
    public abstract Bid fromDTOToBid(BidDTO dto) throws MazadException;

    @Mappings({
            @Mapping(target = "articleReference", source = "article.reference")
    })
    public abstract PhotoDTO fromPhotoToDTO(Photo photo);

    @Mappings({
            @Mapping(target = "id", expression = "java(photoRepository.findIdByReference(dto.getReference()).orElse(ZERO).longValue())"),
            @Mapping(target = "article", expression = "java(articleRepository.findOneByReference(dto.getArticleReference()).orElse(null))")
    })
    public abstract Photo fromDTOToPhoto(PhotoDTO dto);

    public abstract SaleDTO fromSaleToDTO(Sale sale);

    @Mappings({
            @Mapping(target = "id", expression = "java(saleRepository.findIdByReference(dto.getReference()).orElse(ZERO).longValue())")
    })
    public abstract Sale fromDTOToSale(SaleDTO dto);

    public abstract AddressDTO fromAddressToDTO(Address address);

    @Mappings({
            @Mapping(target = "id", expression = "java(addressRepository.findIdByReference(dto.getReference()).orElse(ZERO).longValue())")
    })
    public abstract Address fromDTOToAddress(AddressDTO dto);

    @Mappings({
            @Mapping(target = "reference", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "email", ignore = true)
    })
    public abstract void updateUserFromDto(UserDTO dto, @MappingTarget User user);

    @Mappings({
            @Mapping(target = "reference", ignore = true),
    })
    public abstract void updateBidFromDto(BidDTO dto, @MappingTarget Bid bid);

    @Mappings({
            @Mapping(target = "reference", ignore = true),
    })
    public abstract void updatePhotoFromDto(PhotoDTO dto, @MappingTarget Photo photo);

    @Mappings({
            @Mapping(target = "reference", ignore = true),
    })
    public abstract void updateSaleFromDto(SaleDTO dto, @MappingTarget Sale sale);

    @Mappings({
            @Mapping(target = "reference", ignore = true),
            @Mapping(target = "avatar", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "photos", ignore = true),
            @Mapping(target = "category", expression = "java(categoryRepository.findOneByName(dto.getCategory()).orElse(null))")
    })
    public abstract void updateArticleFromDto(ArticleDTO dto, @MappingTarget Article article);

    @Mappings({
            @Mapping(target = "reference", ignore = true)})
    public abstract void updateAddressFromDto(AddressDTO dto, @MappingTarget Address address);


}
