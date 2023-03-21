package org.SpeechForm.FormService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryForm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private AudioFile audioFile;
}
