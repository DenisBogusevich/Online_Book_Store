package onlinebookstore.service;

import onlinebookstore.dto.category.CategoryDto;
import onlinebookstore.dto.category.CategoryRequestDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto save(CategoryRequestDto categoryDto);
    CategoryDto findById(Long id);
    CategoryDto update(Long id, CategoryRequestDto categoryDto);
    void deleteById(Long id);



}
