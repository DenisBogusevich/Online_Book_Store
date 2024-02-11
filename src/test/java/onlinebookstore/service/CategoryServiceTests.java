package onlinebookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import onlinebookstore.dto.category.CategoryDto;
import onlinebookstore.dto.category.CategoryRequestDto;
import onlinebookstore.entity.Category;
import onlinebookstore.mapper.CategoryMapper;
import onlinebookstore.mapper.impl.CategoryMapperImpl;
import onlinebookstore.repository.CategoryRepository;
import onlinebookstore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @Test
    @DisplayName("Save new category ")
    void save_validRequestDto_returnsDto() {
        CategoryRequestDto requestDto = new CategoryRequestDto("TEST1", "TTT@TTT");

        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());

        when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto actual = categoryService.save(requestDto);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @DisplayName("Update existing category ")
    void update_validRequestDto_returnsDto() {
        Long id = 1L;

        Category category = new Category();
        category.setName("TEST2");
        category.setId(id);

        CategoryRequestDto requestDto = new CategoryRequestDto("TEST1", "TTT@TTT");

        Category requestCategory = new Category();
        requestCategory.setId(id);
        requestCategory.setName(requestDto.name());
        requestCategory.setDescription(requestDto.description());

        CategoryDto categoryDto = new CategoryDto(1L,
                requestCategory.getName(),
                requestCategory.getDescription());

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(requestCategory);

        CategoryDto actual = categoryService.update(id, requestDto);

        assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Update not existing category")
    void update_notValidRequestDto_throwsException() {
        Long id = 100L;
        CategoryRequestDto requestDto = new CategoryRequestDto("TEST1", "TTT@TTT");

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.update(id, requestDto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Get category by valid id")
    void getById_validId_returnsValidDto() {
        Category category = new Category();
        category.setName("TEST2");
        category.setId(1L);

        CategoryDto categoryDto = new CategoryDto(category.getId(),
                category.getName(),
                category.getDescription());

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryDto actual = categoryService.findById(1L);

        assertThat(actual).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Get category by not existing id")
    void getById_notValidId_throwsException() {
        Long id = 100L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Delete category by valid id")
    void deleteById_validId() {
        Long id = 1L;
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(categoryRepository);
    }
}
