package com.singular.renting.service;

import com.singular.renting.repository.FilmRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    FilmService service;

    @Test
    public void is() {
    }
}
