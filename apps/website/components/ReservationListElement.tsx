import 'react';

interface ReservationListElementProps {
  startsAt: string;
  endsAt: string;
  vehicleDescription: string;
};

export default function ReservationListElement({ startsAt, endsAt, vehicleDescription }: ReservationListElementProps) {

  const startDate = new Date(startsAt);
  const endDate = new Date(endsAt);

  const hours = getHoursCount(startDate.getTime() - new Date().getTime());
  const due = isDue(hours);
  const agoPhrase = getAgoPhrase(hours);

  return <li className="list-group-item d-flex justify-content-between align-items-start">
    <div className="ms-2 me-auto">
      <div className="fw-bold">{vehicleDescription}</div>
      <p><em>{startDate.toLocaleDateString("en-US", { weekday: 'long', day: 'numeric', month: 'long' }) + " " + startDate.toLocaleTimeString('en-US', { timeStyle: "short" })}</em></p>
    </div>
    <span className={`badge bg-${due ? 'secondary' : 'primary'} rounded-pill`}>{agoPhrase}</span>
  </li>;
};

const getHoursCount = (milliseconds: number): number => Math.round(milliseconds / 1000 / 3600);

const isDue = (hours: number): boolean => hours < 0;

const getAgoPhrase = (hours: number): string => {
  const periodTitle = Math.abs(hours) >= 24 ? "days" : "hours";
  const periodsCount = Math.round(Math.abs(hours) >= 24 ? Math.abs(hours) / 24 : Math.abs(hours));

  if (hours >= 0) {
    return `in ${periodsCount} ${periodTitle}`;
  }

  return `${periodsCount} ${periodTitle} ago`;
};