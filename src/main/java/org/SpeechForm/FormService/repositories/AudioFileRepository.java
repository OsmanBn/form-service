package org.SpeechForm.FormService.repositories;

import org.SpeechForm.FormService.entities.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileRepository extends JpaRepository<AudioFile, Integer> {
}
