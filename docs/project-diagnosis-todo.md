# 프로젝트 미완성/불일치 TODO

- [ ] 컨트롤러에서 `POST /memos` 구현 및 테스트 정합성 맞추기
- [ ] 컨트롤러 경로 정리(`/memo` vs `/memos`) 및 API 스펙 확정
- [ ] `MemoServiceInDto`/`MemoServiceOutDto` 정의 또는 불필요 시 제거
- [ ] `MemoCreateRequestMapper`를 실제 요청 흐름에 적용
- [ ] `Memo` 엔티티에 기본 생성자/Getter 추가 (JPA 요구사항 충족)
- [ ] DTO 입력 검증 어노테이션(`@NotBlank` 등) 추가
- [ ] `application.yaml`에 DB 설정 추가, 필요 시 profile 분리
- [ ] 감사 필드(`createdBy`/`updatedBy`)의 실제 인증 연동 여부 검토
