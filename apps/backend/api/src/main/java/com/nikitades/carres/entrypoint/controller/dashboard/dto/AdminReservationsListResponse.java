package com.nikitades.carres.entrypoint.controller.dashboard.dto;

import java.util.List;

public record AdminReservationsListResponse(List<ReservationDto> items, int totalPages) {}
