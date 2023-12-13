package onlinebookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import onlinebookstore.dto.category.CategoryDto;
import onlinebookstore.dto.category.CategoryRequestDto;
import onlinebookstore.entity.Category;
import onlinebookstore.mapper.CategoryMapper;
import onlinebookstore.repository.CategoryRepository;
import onlinebookstore.service.CategoryService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryDto) {
        return categoryMapper.toDto(categoryRepository
                .save(categoryMapper.toCategory(categoryDto)));
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find category with id:" + id));
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto categoryDto) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find category with id:" + id));
        categoryMapper.updateCategory(categoryDto, categoryToUpdate);
        return categoryMapper.toDto(categoryRepository.save(categoryToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
