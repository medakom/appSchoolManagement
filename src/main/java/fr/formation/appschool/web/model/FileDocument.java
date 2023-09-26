package fr.formation.appschool.web.model;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class FileDocument {
    Long id;
    String name;
    String description;
    String type;
    @Lob
    byte[] content;

    Course course;

}
