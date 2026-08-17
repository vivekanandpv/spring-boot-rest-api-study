package dev.vivekanand.springbootrestapistudy.config;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.filter.CorsFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ApplicationCorsConfigurationTest {

    @Test
    void createsCorsFilterWithConfiguredPolicy() throws Exception {
        // Verify that the configured origin and method are accepted by the generated CORS filter.
        ApplicationCorsConfiguration configuration = new ApplicationCorsConfiguration();
        ReflectionTestUtils.setField(configuration, "allowedOrigins", new String[]{"https://example.test"});
        ReflectionTestUtils.setField(configuration, "allowedMethods", new String[]{"GET", "POST"});
        ReflectionTestUtils.setField(configuration, "allowedHeaders", new String[]{"Content-Type"});
        CorsFilter filter = configuration.corsFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/products");
        request.addHeader("Origin", "https://example.test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        assertThat(response.getHeader("Access-Control-Allow-Origin")).isEqualTo("https://example.test");
        assertThat(response.getHeader("Access-Control-Allow-Credentials")).isEqualTo("true");
        verify(chain).doFilter(request, response);
    }
}
