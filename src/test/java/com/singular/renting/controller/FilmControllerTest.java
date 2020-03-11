package com.singular.renting.controller;

import com.singular.renting.resource.FilmAssembler;
import com.singular.renting.service.FilmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {

    @Mock
    private FilmService service;
    @Mock
    private FilmAssembler assembler;

    @InjectMocks
    private FilmController controller;

    @Test
    public void itShould() {
    }
}
