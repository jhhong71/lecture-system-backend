package com.example.demo; // 본인의 패키지명에 맞게 수정하세요

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*") // [중요] Vue(5173)에서 오는 요청 허용 (CORS 해결)
@Tag(name = "Lectures", description = "강의 관리 API")
public class LectureController {

    // 1. 가짜 데이터베이스 (메모리에 저장)
    private List<LectureDto> dummyDb = new ArrayList<>();

    public LectureController() {
        // 생성자에서 더미 데이터 초기화
        dummyDb.add(new LectureDto(1L, 1, "2025", "1학기", "전공필수", "소프트웨어 공학 개론", 3, "김철수", 45, "2025-02-15"));
        dummyDb.add(new LectureDto(2L, 2, "2025", "1학기", "전공선택", "웹 프론트엔드 실무", 3, "이영희", 120, "2025-02-16"));
        dummyDb.add(new LectureDto(3L, 3, "2025", "2학기", "교양", "디자인 씽킹", 2, "박민수", 80, "2025-08-14"));
    }

    // 2. API 정의
    @Operation(summary = "강의 목록 조회", description = "연도, 학기, 이수구분으로 강의를 검색합니다.")
    @GetMapping("/lectures")
    public List<LectureDto> getLectures(
            @Parameter(description = "개설 연도 (예: 2025)") @RequestParam(required = false) String year,
            @Parameter(description = "학기 (예: 1학기)") @RequestParam(required = false) String semester,
            @Parameter(description = "이수 구분 (예: 전공필수, 전체)") @RequestParam(required = false) String type
    ) {
        // 3. 필터링 로직 (Java Stream API 사용)
        return dummyDb.stream()
                .filter(lecture -> year == null || lecture.getYear().equals(year))
                .filter(lecture -> semester == null || lecture.getSemester().equals(semester))
                .filter(lecture -> type == null || type.equals("전체") || lecture.getType().equals(type))
                .collect(Collectors.toList());
    }

    // 4. DTO 클래스 (데이터 틀 정의) - 보통은 별도 파일로 분리합니다.
    @Data // Lombok: Getter, Setter 자동 생성
    @AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
    static class LectureDto {
        private Long id;
        private int no;
        private String year;
        private String semester;
        private String type;
        private String name;
        private int credits;
        private String professor;
        private int students;
        private String date;
    }
}
