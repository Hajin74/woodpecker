package org.example.woodpeckerback.graphql;

import graphql.GraphQLContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.*;
import org.example.woodpeckerback.service.NoteService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoteResolver {

    private final NoteService noteService;

    @MutationMapping
    public SaveNoteResponse saveNote(@Argument("input") SaveNoteInput input, GraphQLContext context) {
        Long userId = context.get("userId");
        log.info("saveNote - isbn: {}, user: {}", input.getIsbn(), userId);

        noteService.saveNote(userId, input);
        return new SaveNoteResponse(true, "(Isbn: " + input.getIsbn() + ") 에 적은 노트가 성공적으로 작성되었습니다.");
    }

    @MutationMapping
    public DeleteNoteResponse deleteNote(@Argument("noteId") Long noteId, GraphQLContext context) {
        Long userId = context.get("userId");
        log.info("deleteNote - {}", noteId);
        noteService.deleteNote(userId, noteId);

        return new DeleteNoteResponse(true, "(noteId: " +  noteId + ") 가 성공적으로 삭제되었습니다.");
    }

    @MutationMapping
    public NoteDetailResponse getDetailNote(@Argument("noteId") Long noteId, GraphQLContext context) {
        Long userId = context.get("userId");
        log.info("getDetailNote - {}", noteId);

        return noteService.getDetailNote(userId, noteId);
    }

//    @MutationMapping
//    public List<ReadDetailNoteResponse> getNotes(@Argument("isbn") String isbn) {
//        log.info("getNotes book - {}", isbn);
//        List<String> result = noteService.getNotes(2L, isbn);
//
//        return result.stream()
//                .map((r) -> new ReadDetailNoteResponse(true, r))
//                .collect(Collectors.toList());
//    }

}
