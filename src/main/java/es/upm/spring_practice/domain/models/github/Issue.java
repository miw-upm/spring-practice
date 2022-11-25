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
    //@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Label> labels;
}
