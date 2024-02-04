package org.linksharing.server.links;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Link {
    private String title;
    private String url;
}
