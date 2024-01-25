package onlinebookstore.controler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import onlinebookstore.dto.book.BookDto;
import onlinebookstore.dto.book.CreateBookRequestDto;
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
                "classpath:database/add-books.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/clear-database.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class BookControllerTests {
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
    @DisplayName("Create a new book")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void create_ValidCase_ShouldReturnBookDto() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Harry Potter",
                "J. K. Rowling",
                "1456789101",
                BigDecimal.valueOf(30),
                null,
                null,
                List.of(1L)
        );

        MvcResult result = mockMvc.perform(post("/books")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actual).isNotNull()
                .hasFieldOrProperty("id").isNotNull()
                .hasFieldOrPropertyWithValue("title", "Harry Potter")
                .hasFieldOrPropertyWithValue("author", "J. K. Rowling")
                .hasFieldOrPropertyWithValue("isbn", "1456789101")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(30))
                .hasFieldOrPropertyWithValue("categoryIds", Collections.singletonList(1L));
    }

    @Test
    @DisplayName("Get book by id")
    @WithMockUser(username = "user", roles = "USER")
    void getById_ValidCase_ShouldReturnBookDto() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "The Godfather")
                .hasFieldOrPropertyWithValue("author", "Mario Puzo")
                .hasFieldOrPropertyWithValue("isbn", "222333666")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(350.55));

    }

    @Test
    @DisplayName("Update book")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void update_ValidCase_ShouldReturnBookDto() throws Exception {
        Long id = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "Harry Potter",
                "J. K. Rowling",
                "1456789101",
                BigDecimal.valueOf(30),
                null,
                null,
                List.of(1L)
        );

        MvcResult result = mockMvc.perform(put("/books/{id}", id)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actual).isNotNull()
                .hasFieldOrProperty("id").isNotNull()
                .hasFieldOrPropertyWithValue("title", "Harry Potter")
                .hasFieldOrPropertyWithValue("author", "J. K. Rowling")
                .hasFieldOrPropertyWithValue("isbn", "1456789101")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(30))
                .hasFieldOrPropertyWithValue("categoryIds", Collections.singletonList(1L));
    }

    @Test
    @DisplayName("Delete book by existent id")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void deleteCategory_WithExistentId_Success() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/books/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
