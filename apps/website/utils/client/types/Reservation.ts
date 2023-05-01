export default interface Reservation {
  id: string;
  startsAt: string;
  endsAt: string;
  vehicleDescription: string;
  cancelled: boolean;
}
