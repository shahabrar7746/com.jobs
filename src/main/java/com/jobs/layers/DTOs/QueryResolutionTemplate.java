package com.jobs.layers.DTOs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResolutionTemplate {

    public int status = HttpStatus.ACCEPTED.value();
}
