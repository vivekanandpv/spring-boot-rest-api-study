package dev.vivekanand.springbootrestapistudy.apis;

import dev.vivekanand.springbootrestapistudy.entities.Product;
import dev.vivekanand.springbootrestapistudy.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductApiIntegrationTest {
    private static final String PRODUCTS_URL = "/api/v1/products";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void createsProductAndPersistsIt() throws Exception {
        // Create a valid product through HTTP and verify its response and database state.
        mockMvc.perform(post(PRODUCTS_URL)
                        .contentType(JSON)
                        .content(createProductJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Keyboard")))
                .andExpect(jsonPath("$.description", is("Mechanical keyboard")))
                .andExpect(jsonPath("$.sku", is("ABCDE12345")))
                .andExpect(jsonPath("$.storeInformation", is("Available online and in store")));

        Product saved = productRepository.findAll().stream().findFirst().orElseThrow();
        org.assertj.core.api.Assertions.assertThat(saved.getName()).isEqualTo("Keyboard");
        org.assertj.core.api.Assertions.assertThat(saved.getSku()).isEqualTo("ABCDE12345");
    }

    @Test
    void getsAllPersistedProducts() throws Exception {
        // Persist two products and verify the collection endpoint returns both records in order.
        productRepository.saveAll(java.util.List.of(product("Keyboard", "ABCDE12345"), product("Mouse", "FGHIJ67890")));

        mockMvc.perform(get(PRODUCTS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Keyboard")))
                .andExpect(jsonPath("$[1].name", is("Mouse")));
    }

    @Test
    void getsProductById() throws Exception {
        // Persist a product and verify that the item endpoint returns its complete representation.
        Product saved = productRepository.saveAndFlush(product("Keyboard", "ABCDE12345"));

        mockMvc.perform(get(PRODUCTS_URL + "/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Keyboard")))
                .andExpect(jsonPath("$.sku", is("ABCDE12345")));
    }

    @Test
    void updatesProductAndPersistsChanges() throws Exception {
        // Update an existing product through HTTP and verify the changed fields are persisted.
        Product saved = productRepository.saveAndFlush(product("Keyboard", "ABCDE12345"));

        mockMvc.perform(put(PRODUCTS_URL + "/" + saved.getId())
                        .contentType(JSON)
                        .content("""
                                {
                                  "description": "Updated mechanical keyboard",
                                  "storeInformation": "Available from the updated store"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Keyboard")))
                .andExpect(jsonPath("$.sku", is("ABCDE12345")))
                .andExpect(jsonPath("$.description", is("Updated mechanical keyboard")))
                .andExpect(jsonPath("$.storeInformation", is("Available from the updated store")));

        Product updated = productRepository.findById(saved.getId()).orElseThrow();
        org.assertj.core.api.Assertions.assertThat(updated.getDescription()).isEqualTo("Updated mechanical keyboard");
    }

    @Test
    void deletesProductAndReturnsNoContent() throws Exception {
        // Delete an existing product through HTTP and verify it is no longer persisted.
        Product saved = productRepository.saveAndFlush(product("Keyboard", "ABCDE12345"));

        mockMvc.perform(delete(PRODUCTS_URL + "/" + saved.getId()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        org.assertj.core.api.Assertions.assertThat(productRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void returnsNotFoundForMissingProduct() throws Exception {
        // Request a missing product and verify the API returns its documented not-found response.
        mockMvc.perform(get(PRODUCTS_URL + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(JSON))
                .andExpect(jsonPath("$.error", is("Product not found: 999999")));
    }

    @Test
    void returnsNotFoundWhenUpdatingMissingProduct() throws Exception {
        // Attempt to update a missing product and verify that no record is created.
        mockMvc.perform(put(PRODUCTS_URL + "/999999")
                        .contentType(JSON)
                        .content(updateProductJson()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Product not found: 999999")));

        org.assertj.core.api.Assertions.assertThat(productRepository.count()).isZero();
    }

    @Test
    void returnsNotFoundWhenDeletingMissingProduct() throws Exception {
        // Attempt to delete a missing product and verify that the API returns HTTP 404.
        mockMvc.perform(delete(PRODUCTS_URL + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Product not found: 999999")));
    }

    @Test
    void rejectsInvalidCreatePayload() throws Exception {
        // Submit an invalid create payload and verify bean validation rejects every invalid field.
        mockMvc.perform(post(PRODUCTS_URL)
                        .contentType(JSON)
                        .content("""
                                {
                                  "name": "ab",
                                  "description": "",
                                  "sku": "invalid",
                                  "storeInformation": "short"
                                }
                                """))
                .andExpect(status().isBadRequest());

        org.assertj.core.api.Assertions.assertThat(productRepository.count()).isZero();
    }

    @Test
    void rejectsInvalidUpdatePayload() throws Exception {
        // Submit an invalid update payload and verify validation prevents persistence changes.
        Product saved = productRepository.saveAndFlush(product("Keyboard", "ABCDE12345"));

        mockMvc.perform(put(PRODUCTS_URL + "/" + saved.getId())
                        .contentType(JSON)
                        .content("""
                                {
                                  "description": "",
                                  "storeInformation": "short"
                                }
                                """))
                .andExpect(status().isBadRequest());

        Product unchanged = productRepository.findById(saved.getId()).orElseThrow();
        org.assertj.core.api.Assertions.assertThat(unchanged.getDescription()).isEqualTo("Description");
    }

    @Test
    void allowsConfiguredCorsPreflightRequest() throws Exception {
        // Send a configured CORS preflight request and verify the expected access-control headers.
        mockMvc.perform(options(PRODUCTS_URL)
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "POST")
                        .header("Access-Control-Request-Headers", "Content-Type"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
                .andExpect(header().string("Access-Control-Allow-Methods", org.hamcrest.Matchers.containsString("POST")))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }

    private static Product product(String name, String sku) {
        Product product = new Product();
        product.setName(name);
        product.setDescription("Description");
        product.setSku(sku);
        product.setStoreInformation("Available online and in store");
        return product;
    }

    private static String createProductJson() {
        return """
                {
                  "name": "Keyboard",
                  "description": "Mechanical keyboard",
                  "sku": "ABCDE12345",
                  "storeInformation": "Available online and in store"
                }
                """;
    }

    private static String updateProductJson() {
        return """
                {
                  "description": "Updated description",
                  "storeInformation": "Available online and in store"
                }
                """;
    }
}
