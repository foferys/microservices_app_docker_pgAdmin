package com.embarkx.companyms.company.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.embarkx.companyms.company.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;

    @Test
    void deleteCompanyById_shouldReturnFalse_whenCompanyDoesNotExist() {
        Long id = 99L;

        when(companyRepository.existsById(id)).thenReturn(false);

        boolean result = companyServiceImpl.deleteCompanyById(id);
        assertFalse(result);
        verify(companyRepository, never()).deleteById(id);
    }
}