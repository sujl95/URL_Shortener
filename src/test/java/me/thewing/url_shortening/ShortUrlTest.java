package me.thewing.url_shortening;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.thewing.url_shortening.dto.UrlCreateDto;

@DisplayName("Short Url 통합 Test")
@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	@DisplayName("ShortUrl 생성 테스트")
	void createShortUrlTest() throws Exception{
		String originUrl = "https://sujl95.tistory.com/";
		UrlCreateDto urlCreateDto = UrlCreateDto.builder()
				.url(originUrl)
				.build();
		mockMvc.perform(post("/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(urlCreateDto)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("shortUrl").value("http://www.thewing.cf/cXk1OFA4"));
	}

	@Test
	@DisplayName("ShortUrl logs 조회 테스트 - 성공")
	void findByShortUrl() throws Exception {
		String shortUrl = "cXk1OFA4";
		mockMvc.perform(get("/logs/{shortUrl}", shortUrl))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("ShortUrl logs 조회 테스트 - 실패")
	void findByShortUrlFailure() throws Exception {
		String shortUrl = "asdfasdf";
		mockMvc.perform(get("/logs/{shortUrl}", shortUrl))
				.andExpect(status().isNotFound());
	}

}
