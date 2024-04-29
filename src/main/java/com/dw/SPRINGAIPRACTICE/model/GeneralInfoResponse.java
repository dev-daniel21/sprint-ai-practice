package com.dw.SPRINGAIPRACTICE.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GeneralInfoResponse(@JsonPropertyDescription("director") String director,
                                  @JsonPropertyDescription("budget") String budget,
                                  @JsonPropertyDescription("year of release") String yearOfRelease) {
}
