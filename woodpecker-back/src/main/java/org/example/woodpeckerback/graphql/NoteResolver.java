package org.example.woodpeckerback.graphql;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.woodpeckerback.dto.DeleteNoteResponse;
import org.example.woodpeckerback.dto.ReadDetailNoteResponse;
import org.example.woodpeckerback.dto.SaveNoteInput;
import org.example.woodpeckerback.dto.SaveNoteResponse;
import org.example.woodpeckerback.service.NoteService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoteResolver {

    private final NoteService noteService;

    @MutationMapping
    public SaveNoteResponse saveNote(@Argument("input") SaveNoteInput input) {
        log.info("saveNote - {}", input.getIsbn());
        noteService.saveNote(2L, input.getIsbn(), input.getContent());
        return new SaveNoteResponse(true, "success!");
    }

    @MutationMapping
    public DeleteNoteResponse deleteNote(@Argument("noteId") Long noteId) {
        log.info("deleteNote - {}", noteId);
        noteService.deleteNote(2L, noteId);

        return new DeleteNoteResponse(true, "success!");
    }

    @MutationMapping
    public ReadDetailNoteResponse getDetailNote(@Argument("noteId") Long noteId) {
        log.info("getDetailNote - {}", noteId);
        String result = noteService.getDetailNote(2L, noteId);

        return new ReadDetailNoteResponse(true, result);
    }
}
