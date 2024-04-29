package com.dw.SPRINGAIPRACTICE.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GeneralResponse(@JsonPropertyDescription("Director property:") String response) {
}
