package es.upm.spring_practice.domain.models.github;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {
    private Integer number;
    private String title;
    private String body;
    @Singular
    private List<Label> labels;
}
