package org.SpeechForm.FormService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String filename;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
