export default interface CreateReservationRequest {
  startsAt: string;
  durationMinutes: number;
  vehicleId: string;
}
