package scanner.prototype.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@Import(RestDocConfiguration.class)
@AutoConfigureRestDocs
@SpringBootTest
class CheckListTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("API RUNNING Test")
    void apiRunningTest() throws Exception {

        this.mockMvc.perform(get("/"))
        .andDo(print())
        .andDo(document("API Running-GET",
            responseFields( 
                fieldWithPath("scan").type(JsonFieldType.STRING).description("Scan"),
                fieldWithPath("checklist").type(JsonFieldType.STRING).description("Checklist"),
                fieldWithPath("history").type(JsonFieldType.STRING).description("History"),
                fieldWithPath("report").type(JsonFieldType.STRING).description("Report")
            ))
        )
        .andExpect(jsonPath("scan").isString())
        .andExpect(jsonPath("checklist").isString())
        .andExpect(jsonPath("history").isString())
        .andExpect(jsonPath("report").isString());

        this.mockMvc.perform(get("/api/v1"))
            .andDo(print())
            .andDo(document("API Running-GET",
                responseFields( 
                    fieldWithPath("scan").type(JsonFieldType.STRING).description("Scan"),
                    fieldWithPath("checklist").type(JsonFieldType.STRING).description("Checklist"),
                    fieldWithPath("history").type(JsonFieldType.STRING).description("History"),
                    fieldWithPath("report").type(JsonFieldType.STRING).description("Report")
                ))
            )
        .andExpect(jsonPath("scan").isString())
        .andExpect(jsonPath("checklist").isString())
        .andExpect(jsonPath("history").isString())
        .andExpect(jsonPath("report").isString());
    }
}
