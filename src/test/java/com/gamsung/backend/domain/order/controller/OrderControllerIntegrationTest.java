package com.gamsung.backend.domain.order.controller;

import com.gamsung.backend.global.common.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@DisplayName("OrderController 통합 테스트")
public class OrderControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("숙소 여러개 예약하기")
    @Transactional
    void orderSuccessTest() throws Exception {

        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRfaWQiOiIxIiwiZW1haWwiOiJteXRlc3QxQG5hdmVyLmNvbSIsImlzcyI6ImZjX21pbmkiLCJpYXQiOjE3MDEzMTQ2NTgsImV4cCI6MTcwMTM1MDY1OH0.LUK5_Go1zrhm5AWqojnKIYLvIfZnHzNTj8EOPiUepWKznGmbpq-1Hfnmom0Y6U_gFXUT8BlPmoNZgYsVKOyEig");

        String jsonPayload = "[\n" +
                "  {\n" +
                "    \"accommodation_id\": 2,\n" +
                "    \"people_number\": 2,\n" +
                "    \"start_date\": \"2023-12-03\",\n" +
                "    \"end_date\": \"2023-12-04\",\n" +
                "    \"representative_name\": \"홍길동\",\n" +
                "    \"representative_email\": \"rlfehd@naver.com\",\n" +
                "    \"order_price\": 120000,\n" +
                "    \"cart_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"accommodation_id\": 1,\n" +
                "    \"people_number\": 2,\n" +
                "    \"start_date\": \"2023-12-02\",\n" +
                "    \"end_date\": \"2023-12-03\",\n" +
                "    \"representative_name\": \"홍길동\",\n" +
                "    \"representative_email\": \"rlfehd@naver.com\",\n" +
                "    \"order_price\": 120000,\n" +
                "    \"cart_id\": 0\n" +
                "  }\n" +
                "]";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/order")
                        .headers(headers)
                        .content(jsonPayload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2003))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("결제가 성공하였습니다."))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @DisplayName("숙소 예약 가능여부 확인하기")
    @Transactional
    void checkBookDateIntegrationTest() throws Exception {

        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRfaWQiOiIxIiwiZW1haWwiOiJteXRlc3QxQG5hdmVyLmNvbSIsImlzcyI6ImZjX21pbmkiLCJpYXQiOjE3MDEzMTQ2NTgsImV4cCI6MTcwMTM1MDY1OH0.LUK5_Go1zrhm5AWqojnKIYLvIfZnHzNTj8EOPiUepWKznGmbpq-1Hfnmom0Y6U_gFXUT8BlPmoNZgYsVKOyEig");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/order/check")
                        .param("accommodation_id", "1")
                        .param("start_date", "2023-12-01")
                        .param("end_date", "2023-12-03")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("해당 날짜는 예약 가능합니다."))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @DisplayName("내가 예약한 숙소들 조회하기")
    @Transactional
    void getUserOrderListIntegrationTest() throws Exception {

        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJjbGllbnRfaWQiOiIxIiwiZW1haWwiOiJteXRlc3QxQG5hdmVyLmNvbSIsImlzcyI6ImZjX21pbmkiLCJpYXQiOjE3MDEzMTQ2NTgsImV4cCI6MTcwMTM1MDY1OH0.LUK5_Go1zrhm5AWqojnKIYLvIfZnHzNTj8EOPiUepWKznGmbpq-1Hfnmom0Y6U_gFXUT8BlPmoNZgYsVKOyEig");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/order/me")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.order_list").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.total_page").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.page_number").exists())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
