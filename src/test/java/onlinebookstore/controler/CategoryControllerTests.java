package onlinebookstore.controler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import onlinebookstore.dto.category.CategoryDto;
import onlinebookstore.dto.category.CategoryRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:database/add-categories.sql",
        "classpath:database/add-books.sql"})
@Sql(scripts = "classpath:database/clear-database.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class CategoryControllerTests {
    protected static MockMvc mockMvc;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Create a new category")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void create_ValidCase_ShouldReturnCategoryDto() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "Fantasy", "Fantasy books"
        );

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("id", 6L);
    }

    @Test
    @DisplayName("Get by id")
    @WithMockUser(username = "user", roles = {"USER"})
    void getById_ValidCase_ShouldReturnCategoryDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("name", "education")
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("Update category")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void update_ValidCase_ShouldReturnCategoryDto() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "Fantasy", "Fantasy books"
        );

        MvcResult result = mockMvc.perform(put("/categories/{id}", 1L)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    @DisplayName("Delete category")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void delete_ValidCase_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
