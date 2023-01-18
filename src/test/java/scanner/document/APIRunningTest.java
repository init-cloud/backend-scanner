package scanner.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@Import(RestDocConfiguration.class)
@AutoConfigureRestDocs
@SpringBootTest
class APIRunningTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("API RUNNING Test")
    void apiRunningTest() throws Exception {

        this.mockMvc.perform(get("/"))
            .andDo(print())
            .andDo(document("API Running-GET",
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("is Success"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("error"),
                    fieldWithPath("data.scan").type(JsonFieldType.STRING).description("Scan"),
                    fieldWithPath("data.checklist").type(JsonFieldType.STRING).description("Checklist"),
                    fieldWithPath("data.history").type(JsonFieldType.STRING).description("History"),
                    fieldWithPath("data.report").type(JsonFieldType.STRING).description("Report")
                ))
            )
            .andExpect(jsonPath("success").isBoolean())
            .andExpect(jsonPath("error").isEmpty())
            .andExpect(jsonPath("data.scan").isString())
            .andExpect(jsonPath("data.checklist").isString())
            .andExpect(jsonPath("data.history").isString())
            .andExpect(jsonPath("data.report").isString());

        this.mockMvc.perform(get("/api/v1"))
            .andDo(print())
            .andDo(document("API Running-GET",
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("is Success"),
                    fieldWithPath("error").type(JsonFieldType.NULL).description("error"),
                    fieldWithPath("data.scan").type(JsonFieldType.STRING).description("Scan"),
                    fieldWithPath("data.checklist").type(JsonFieldType.STRING).description("Checklist"),
                    fieldWithPath("data.history").type(JsonFieldType.STRING).description("History"),
                    fieldWithPath("data.report").type(JsonFieldType.STRING).description("Report")
                ))
            )
        .andExpect(jsonPath("success").isBoolean())
        .andExpect(jsonPath("error").isEmpty())
        .andExpect(jsonPath("data.scan").isString())
        .andExpect(jsonPath("data.checklist").isString())
        .andExpect(jsonPath("data.history").isString())
        .andExpect(jsonPath("data.report").isString());
    }
}
