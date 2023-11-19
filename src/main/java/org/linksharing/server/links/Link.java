package org.linksharing.server.links;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Link {
    private String id;
    private String title;
    private String url;
}
