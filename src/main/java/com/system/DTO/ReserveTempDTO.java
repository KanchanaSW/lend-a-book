package com.system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveTempDTO {
    private Long reserveId;
    private Integer bookId;
    private Integer movieId;
    private Long userId;
}
