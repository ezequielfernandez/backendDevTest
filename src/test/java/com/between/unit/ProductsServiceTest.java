package com.between.unit;

import com.between.dtos.ProductDto;
import com.between.dtos.SimilarProductsDataDto;
import com.between.infra.ProductsClient;
import com.between.services.ProductsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductsClient client;

    @InjectMocks
    private ProductsServiceImpl service = new ProductsServiceImpl();

    @Test
    public void getSimilarProducts_OK() throws Exception {
        ProductDto p = new ProductDto();
        Mockito.when(client.getSimilarProductIds((long) 1))
                .thenReturn(new LinkedList<>() {{
                    add((long) 5);
                }});

        Mockito.when(client.getProduct((long) 5))
                .thenReturn(p);

        SimilarProductsDataDto similarProductsDataDto = service.getSimilarProducts(1);
        List<ProductDto> similar = similarProductsDataDto.getSimilarProducts();

        assertEquals(false, similarProductsDataDto.isPartialContext());
        assertNotNull(similarProductsDataDto.getSimilarProducts());
        assertEquals(1, similar.size());
        assertNotNull(similar.get(0));
    }

    @Test
    public void getSimilarProducts_ErrorGettingLastProduct() throws Exception {
        ProductDto p = new ProductDto();
        Mockito.when(client.getSimilarProductIds((long) 1))
                .thenReturn(new LinkedList() {{
                    add((long) 5);
                    add((long) 6);
                }});

        Mockito.when(client.getProduct((long) 5))
                .thenReturn(p);

        Mockito.when(client.getProduct((long) 6))
                .thenThrow(new Exception("test error"));

        SimilarProductsDataDto similarProductsDataDto = service.getSimilarProducts(1);
        List<ProductDto> similar = similarProductsDataDto.getSimilarProducts();

        assertEquals(true, similarProductsDataDto.isPartialContext());
        assertNotNull(similar);
        assertEquals(1, similar.size());
        assertNotNull(similar.get(0));
    }

    @Test
    public void getSimilarProducts_ErrorGettingSimilars() throws Exception {
        Mockito.when(client.getSimilarProductIds((long) 1))
                .thenThrow(new Exception("test error"));

        Assertions.assertThrows(Exception.class, () -> service.getSimilarProducts(1));
    }
}
