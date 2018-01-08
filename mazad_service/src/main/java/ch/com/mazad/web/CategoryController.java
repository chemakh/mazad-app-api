package ch.com.mazad.web;

import ch.com.mazad.domain.Category;
import ch.com.mazad.exception.FieldErrorDTO;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Chemakh on 28/06/2017.
 */

@RestController
@RequestMapping("/ws/categories")
public class CategoryController {

    @Inject
    private CategoryService categoryService;

    @Inject
    private ObjectMapper objectMapper;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create Category Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Category.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public Category createCategory(@Valid @RequestPart("category") String category, @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws MazadException, IOException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Category object = objectMapper.readValue(category, Category.class);
        final Set<ConstraintViolation<Category>> violations = validator.validate(object);

        if (violations.size() > 0) {
            violations.forEach(u -> System.out.println("  \"" + u.getPropertyPath().toString() + "\"" + " " + u.getMessage()));

            ConstraintViolation<Category> violation = violations.stream().findFirst().get();

            throw MazadException.validationErrorBuilder(new FieldErrorDTO("Category", violation.getPropertyPath().toString(), violation.getMessage()));


        }

        return categoryService.createCategory(object, avatar);
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create Category Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Category.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public Category updateCategory(@Valid @RequestBody Category category, @RequestParam(value = "reference") String reference) throws MazadException {

        return categoryService.updateCategory(category, reference);
    }

    @PutMapping(value = "avatar",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Category Avatar Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Category.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 404, message = "Object with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")

    })
    public Category updateUserAvatar(@RequestParam("category_ref") String categoryRef, @RequestPart(value = "photo") MultipartFile photo) throws MazadException, IOException {

        return categoryService.updateCategoryAvatar(categoryRef, photo);
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Category Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Category.class),
            @ApiResponse(code = 404, message = "Category with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public List<Category> getCategory(@RequestParam(value = "reference", required = false) String reference) throws MazadException {

        return categoryService.getCategory(reference);
    }

    @RequestMapping(value = "",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Category Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Category.class),
            @ApiResponse(code = 404, message = "Category with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public JSONObject deleteCategory(@RequestParam("reference") String reference) throws MazadException {

        return categoryService.deleteCategory(reference);
    }
}
