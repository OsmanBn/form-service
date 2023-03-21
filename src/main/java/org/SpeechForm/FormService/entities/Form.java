package org.SpeechForm.FormService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    private long id;
    private String name;
    private String description;
    //private AudioFile audioFile;
}
