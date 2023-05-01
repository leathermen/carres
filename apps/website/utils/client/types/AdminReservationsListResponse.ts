import Reservation from "./Reservation";

export default interface AdminReservationsListResponse {
  items: Reservation[];
  totalPages: number;
}
