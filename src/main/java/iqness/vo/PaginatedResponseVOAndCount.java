package iqness.vo;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@ToString
@AllArgsConstructor
public class PaginatedResponseVOAndCount<T> {

    private long totalCount;
    private List<T> data;

}
