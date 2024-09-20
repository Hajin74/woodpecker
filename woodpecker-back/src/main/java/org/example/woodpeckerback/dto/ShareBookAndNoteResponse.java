package org.example.woodpeckerback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Node;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShareBookAndNoteResponse {

    private BookItemResponse bookItemResponse;
    private List<NoteDetailResponse> noteDetailResponses;

}
