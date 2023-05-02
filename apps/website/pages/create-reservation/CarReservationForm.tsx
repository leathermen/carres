import { FormEvent, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { ApiError } from '../../utils/client/errors/ApiError';
import { createReservation } from '../../utils/client/apiClient';


interface CarReservationFormProps {
  vehicleId: string;
  onSuccess: () => void;
}

export default function CarReservationForm({ vehicleId, onSuccess }: CarReservationFormProps) {
  const [startDate, setStartDate] = useState<Date>(getStartOfTomorrow());
  const [durationMinutes, setDurationMinutes] = useState<number>(15);

  const [submitted, setSubmitted] = useState<boolean>(false);
  const [submitError, setSubmitError] = useState<string | null>(null);

  const hours = Array.from({ length: 10 }, (_, index) => index + 9);
  const minutes = [0, 15, 30, 45];
  const durations = [15, 30, 45, 60];



  const onSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    createReservation({
      startsAt: startDate.toISOString(),
      durationMinutes,
      vehicleId
    })
      .then(() => {
        setSubmitted(true);
        onSuccess();
      })
      .catch((e: ApiError) => {
        console.info(e.code);
        switch (e.code) {
          case "BAD_RESERVATION_DURATION":
            setSubmitError("Wrong reservation duration given!");
            break;
          case "RESERVATION_OVERLAPS_WITH_EXISTING_ONE":
            setSubmitError("Your reservation overlaps with another one. Please choose another time.");
            break;
          case "CANNOT_RESERVE_FOR_TOO_SOON":
            setSubmitError("The entered reservation time is too soon. Please choose another time.");
            break;
          default:
            setSubmitError("Something went wrong!");
        }
      });
  }

  return (
    <Form onSubmit={(event: FormEvent<HTMLFormElement>) => onSubmit(event)}>
      {submitted && (<>
        <div className="alert alert-success" role="alert">
          Successfully submitted
          <br />
          <sub>You will be redirected soon...</sub>
        </div>
      </>)}
      {!!submitError && (<>
        <div className="alert alert-danger" role="alert">
          <div>{submitError}</div>
          <button type="button" className="btn btn-light mt-3" onClick={(e: FormEvent<HTMLButtonElement>) => setSubmitError(null)}>Try again</button>
        </div>
      </>)}
      {!submitted && !submitError && (
        <>
          <Form.Group className='mt-2' controlId="datepicker">
            <Form.Label>Date</Form.Label>
            <DatePicker
              selected={startDate}
              onChange={(date: Date) => {
                setStartDate(date);
              }}
              dateFormat="MM/dd/yyyy"
              className="form-control"
              minDate={getStartOfTomorrow()}
              filterDate={filterWeekends}
              locale="en-US"
            />
          </Form.Group>
          <Form.Group className='mt-2' controlId="timepicker">
            <Form.Label>Time</Form.Label>
            <div className="d-flex">
              <Form.Select
                className="me-2"
                value={startDate.getHours()}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  const newDate = new Date(startDate);
                  newDate.setHours(Number(e.target.value));
                  setStartDate(newDate);
                }}
              >
                {hours.map(hour => (
                  <option key={hour} value={hour}>{hour.toString().padStart(2, '0')}</option>
                ))}
              </Form.Select>
              <Form.Select
                value={startDate.getMinutes()}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  const newDate = new Date(startDate);
                  newDate.setMinutes(Number(e.target.value));
                  setStartDate(newDate);
                }}
              >
                {minutes.map(minute => (
                  <option key={minute} value={minute}>{minute.toString().padStart(2, '0')}</option>
                ))}
              </Form.Select>
            </div>
          </Form.Group>
          <Form.Group className='mt-3' controlId="timepicker">
            <Form.Label>Duration: {durationMinutes}</Form.Label>
            <div className="d-flex">
              <Form.Select
                value={durationMinutes}
                onChange={(e: React.ChangeEvent<HTMLSelectElement>) => {
                  setDurationMinutes(Number(e.target.value));
                }}
              >
                {durations.map(duration => (
                  <option key={duration} value={duration}>{duration.toString().padStart(2, '0')}</option>
                ))}
              </Form.Select>
            </div>
          </Form.Group>
          <Button className="mt-4" variant="primary" type="submit">
            Submit
          </Button>
        </>
      )}
    </Form >
  );
}

const getStartOfTomorrow = (): Date => {
  const now = new Date();
  const tomorrow = new Date(now);
  tomorrow.setDate(now.getDate() + 1);
  tomorrow.setHours(9, 0, 0, 0);
  return tomorrow;
}

const filterWeekends = (date: Date) => {
  const day = date.getDay();
  return day !== 0 && day !== 6;
};
