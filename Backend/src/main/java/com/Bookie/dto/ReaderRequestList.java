package com.Bookie.dto;

import com.Bookie.entities.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReaderRequestList(@NotNull
                                Long id,
                                @NotNull
                                UserReaderRequest user,
                                @NotNull
                                HistoryEntity history,
                                @NotBlank
                                Boolean complete,
                                List<ChapterEntity> readerChapters) {
    public ReaderRequestList(ReaderEntity r,List<ChapterEntity> chapters){
        this(r.getId(),new UserReaderRequest(r.getUserId()) , r.getHistoryId(), r.getComplete(),chapters);
    }
}
