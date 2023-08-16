package com.twoday.internshipwarehouse.services;

import com.twoday.internshipmodel.OrderCreateRequest;
import com.twoday.internshipwarehouse.constants.Constants;
import com.twoday.internshipwarehouse.exceptions.InsufficientQuantityException;
import com.twoday.internshipwarehouse.exceptions.InvalidValueException;
import com.twoday.internshipwarehouse.exceptions.ProductNotFoundByIdException;
import com.twoday.internshipwarehouse.models.Product;
import com.twoday.internshipwarehouse.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private final int id = 1;

    @Test
    void givenValidId_whenGetById_thenProductIsReturned() {
        Product expectedResult = new Product(id, "1", "1", new BigDecimal("1.1"), 1);

        when(productRepository.findById(id)).thenReturn(Optional.of(expectedResult));

        Product actualResult = productService.getById(id);

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void givenInvalidId_whenGetById_thenProductNotFoundByIdExceptionIsThrown() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        String actualMessage = catchThrowableOfType(() ->
                productService.getById(id), ProductNotFoundByIdException.class).getMessage();

        String expectedMessage = new ProductNotFoundByIdException(id).getMessage();

        assertThat(actualMessage).isNotNull().isEqualTo(expectedMessage);

        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void whenGetAll_thenProductsAreReturned() {
        List<Product> expectedResult = List.of(
                new Product(1, "Item 1", "Item 1", BigDecimal.valueOf(1.11), 1),
                new Product(2, "Item 2", "Item 2", BigDecimal.valueOf(2.22), 2)
        );

        when(productRepository.findAll()).thenReturn(expectedResult);

        List<Product> actualResult = productService.getAll();

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void givenValidOrderCreateRequest_whenUpdateQuantity_thenProductIsUpdated() {
        BigDecimal unitPrice = new BigDecimal("1.1");
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(id, 1, unitPrice);

        Product mockedProduct = new Product(id, "1", "1", unitPrice, 1);
        when(productRepository.findById(id)).thenReturn(Optional.of(mockedProduct));
        when(productRepository.save(mockedProduct)).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        Product actualResult = productService.updateQuantity(orderCreateRequest);
        Product expectedResult = new Product(id, "1", "1", unitPrice, 0);
        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(productRepository).findById(id);
        verify(productRepository).save(mockedProduct);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void givenInvalidOrderCreateRequest_whenUpdateQuantity_thenInvalidValueExceptionIsThrown() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(id, 0, new BigDecimal("1.1"));

        String actualMessage = catchThrowableOfType(() ->
                productService.updateQuantity(orderCreateRequest), InvalidValueException.class).getMessage();

        String expectedMessage = new InvalidValueException(Constants.QUANTITY).getMessage();

        assertThat(actualMessage).isNotNull().isEqualTo(expectedMessage);
    }

    @Test
    void givenInvalidOrderCreateRequest_whenUpdateQuantity_thenInsufficientQuantityExceptionIsThrown() {
        BigDecimal unitPrice = new BigDecimal("1.1");
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(id, 2, unitPrice);

        Product mockedProduct = new Product(id, "1", "1", unitPrice, 1);
        when(productRepository.findById(id)).thenReturn(Optional.of(mockedProduct));

        String actualMessage = catchThrowableOfType(() ->
                productService.updateQuantity(orderCreateRequest), InsufficientQuantityException.class).getMessage();

        String expectedMessage = new InsufficientQuantityException(id).getMessage();

        assertThat(actualMessage).isNotNull().isEqualTo(expectedMessage);

        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository);
    }
}
