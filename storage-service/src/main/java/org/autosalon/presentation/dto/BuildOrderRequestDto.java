package org.autosalon.presentation.dto;

import java.util.UUID;

public record BuildOrderRequestDto(
        UUID sourceOrderId
) {
}