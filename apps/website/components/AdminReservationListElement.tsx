import 'react';
import { Button } from 'react-bootstrap';
import ReservationListElement from './ReservationListElement';

interface ReservationListElementProps {
  id: string;
  startsAt: string;
  endsAt: string;
  vehicleDescription: string;
  isCancelled: boolean;
  onCancel: (id: string) => void;
};

export default function AdminReservationListElement({
  id,
  startsAt,
  endsAt,
  vehicleDescription,
  isCancelled,
  onCancel
}: ReservationListElementProps) {
  const startDate = new Date(startsAt);
  const hours = getHoursCount(startDate.getTime() - new Date().getTime());
  const pastDue = isPastDue(hours);

  return <div className="row">
    <div className="col-3 col-md-2 col-lg-1">
      <Button
        size='sm'
        disabled={pastDue || isCancelled}
        onClick={(e: React.MouseEvent<HTMLButtonElement>) => { e.preventDefault(); onCancel(id); }}
        variant={pastDue || isCancelled ? "secondary" : "danger"}
      >
        Cancel
      </Button>
    </div>
    <div className="col-9 col-md-10 col-lg-11">
      <ReservationListElement startsAt={startsAt} endsAt={endsAt} vehicleDescription={vehicleDescription} isCancelled={isCancelled} />
    </div>
  </div>;
}


const getHoursCount = (milliseconds: number): number => Math.round(milliseconds / 1000 / 3600);

const isPastDue = (hours: number): boolean => hours < 0;

const getAgoPhrase = (hours: number): string => {
  const periodTitle = Math.abs(hours) >= 24 ? "days" : "hours";
  const periodsCount = Math.round(Math.abs(hours) >= 24 ? Math.abs(hours) / 24 : Math.abs(hours));

  if (hours >= 0) {
    return `in ${periodsCount} ${periodTitle}`;
  }

  return `${periodsCount} ${periodTitle} ago`;
};

const getDuration = (startsAt: Date, endsAt: Date): number => {
  return (endsAt.getTime() - startsAt.getTime()) / 1000 / 60;
}